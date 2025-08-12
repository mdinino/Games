package dinino.marc.games.userflow.common.ui.layout

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import dinino.marc.games.userflow.common.ui.ObserveOneTimeEventEffect
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.transform

@Composable
fun ContentWithActionBar(
    modifier: Modifier = Modifier,
    localizedTitle: String? = null,
    showBackIcon: Boolean = true,
    showMenuIcon: Boolean = true,
    content: @Composable (
        innerPadding: PaddingValues,
        actionBarOneTimeEvent: Flow<ActionBarOneTimeEvent>
    ) -> Unit
) {
    val actionBarOneTimeEvents by remember { mutableStateOf(Channel<ActionBarOneTimeEvent>()) }
    val actionBarEventsFlow by remember { mutableStateOf(actionBarOneTimeEvents.receiveAsFlow()) }

    Scaffold(
        modifier = modifier,
        topBar = {
            ActionBar(
                modifier = modifier,
                localizedTitle = localizedTitle,
                showBackIcon = showBackIcon,
                showMenuIcon = showMenuIcon,
                actionBarOneTimeEvents = actionBarOneTimeEvents
            )
        },
        content = { innerPadding ->
            content(innerPadding, actionBarEventsFlow)
        }
    )
}

@Composable
fun ContentWithActionBar(
    modifier: Modifier = Modifier,
    localizedTitle: String? = null,
    showBackIcon: Boolean = true,
    showMenuIcon: Boolean = true,
    content: @Composable (
        innerPadding: PaddingValues,
        backSelectedEvent: Flow<ActionBarOneTimeEvent.BackSelected>,
        menuSelectedEvent: Flow<ActionBarOneTimeEvent.MenuSelected>,
    ) -> Unit
) = ContentWithActionBar(
        modifier = modifier,
        localizedTitle = localizedTitle,
        showBackIcon = showBackIcon,
        showMenuIcon = showMenuIcon,
        content = { innerPadding, actionBarEvent ->
            content(
                innerPadding,
                actionBarEvent.transformBackSelected(),
                actionBarEvent.transformMenuSelected()
            )
        }
    )

@Composable
fun ContentWithActionBar(
    modifier: Modifier = Modifier,
    localizedTitle: String? = null,
    showMenuIcon: Boolean = true,
    navHostController: NavHostController,
    content: @Composable (
        innerPadding: PaddingValues,
        menuSelectedEvent: Flow<ActionBarOneTimeEvent.MenuSelected>,
    ) -> Unit
) {
    val showBackIcon = navHostController.previousBackStackEntry != null

    ContentWithActionBar(
        modifier = modifier,
        localizedTitle = localizedTitle,
        showBackIcon = navHostController.previousBackStackEntry != null,
        showMenuIcon = showMenuIcon
    ) { innerPadding, backSelectedEvent, menuSelectedEvent ->
        ObserveOneTimeEventEffect(oneTimeEvents = backSelectedEvent) { event ->
            when(showBackIcon) {
                false -> {}
                true -> {
                    if (navHostController.previousBackStackEntry != null) {
                        navHostController.popBackStack()
                    }
                }
            }
        }
        content( innerPadding, menuSelectedEvent)
    }
}

private fun Flow<ActionBarOneTimeEvent>.transformBackSelected(): Flow<ActionBarOneTimeEvent.BackSelected> =
    transform { event ->
        if (event is ActionBarOneTimeEvent.BackSelected)
            emit(event)
    }

private fun Flow<ActionBarOneTimeEvent>.transformMenuSelected(): Flow<ActionBarOneTimeEvent.MenuSelected> =
    transform { event ->
        if (event is ActionBarOneTimeEvent.MenuSelected)
            emit(event)
    }