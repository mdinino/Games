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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted.Companion.Lazily
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.flow.transform

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
        showBackIcon = showBackIcon,
        showMenuIcon = showMenuIcon
    ) { innerPadding, backSelectedEvent, menuSelectedEvent ->
        ObserveOneTimeEventEffect(oneTimeEvents = backSelectedEvent) { event ->
            when(showBackIcon) {
                false -> {}
                true -> {
                    if (showBackIcon) {
                        navHostController.popBackStack()
                    }
                }
            }
        }
        content( innerPadding, menuSelectedEvent)
    }
}

@Composable
private fun ContentWithActionBar(
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
        val splitFlows by remember {
            mutableStateOf(actionBarEvent.split())
        }

        content(
            innerPadding,
            splitFlows.backSelected,
            splitFlows.menuSelected
        )
    }
)

@Composable
private fun ContentWithActionBar(
    modifier: Modifier = Modifier,
    localizedTitle: String? = null,
    showBackIcon: Boolean = true,
    showMenuIcon: Boolean = true,
    content: @Composable (
        innerPadding: PaddingValues,
        actionBarOneTimeEvent: Flow<ActionBarOneTimeEvent>
    ) -> Unit
) {
    val actionBarOneTimeEvents by
        remember { mutableStateOf(Channel<ActionBarOneTimeEvent>()) }
    val actionBarOneTimeEventsFlow by
        remember { mutableStateOf(actionBarOneTimeEvents.receiveAsFlow()) }

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
            content(innerPadding, actionBarOneTimeEventsFlow)
        }
    )
}

private data class SplitFlows(
    val backSelected: Flow<ActionBarOneTimeEvent.BackSelected>,
    val menuSelected: Flow<ActionBarOneTimeEvent.MenuSelected>
)

private fun Flow<ActionBarOneTimeEvent>.split(
    coroutineScope: CoroutineScope =
        CoroutineScope(Dispatchers.Default)
) = shareIn(scope = coroutineScope, started = Lazily)
        .run {
            SplitFlows(
                backSelected = transformBackSelected(),
                menuSelected = transformMenuSelected()
            )
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
