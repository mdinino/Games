package dinino.marc.games.userflow.common.ui.layout

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
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
    showMenuIcon: MutableState<ShowIconState> =
        remember { mutableStateOf(ShownEnabled) },
    navHostController: NavHostController,
    content: @Composable (
        innerPadding: PaddingValues,
        menuSelectedEvent: Flow<MenuSelected>,
    ) -> Unit
) {
    val showBackIcon = remember {
        mutableStateOf( value =
            when(navHostController.previousBackStackEntry != null) {
                true -> ShownEnabled
                false -> NotShown
            }
        )
    }

    ContentWithActionBar(
        modifier = modifier,
        localizedTitle = localizedTitle,
        showBackIcon = showBackIcon,
        showMenuIcon = showMenuIcon
    ) { innerPadding, backSelectedEvent, menuSelectedEvent ->
        ObserveOneTimeEventEffect(oneTimeEvents = backSelectedEvent) { event ->
            when(showBackIcon.value is Shown) {
                false -> {}
                true -> {
                    if (showBackIcon.value is ShownEnabled)
                        navHostController.popBackStack()
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
    showBackIcon: MutableState<ShowIconState> =
        remember { mutableStateOf(ShownEnabled) },
    showMenuIcon: MutableState<ShowIconState> =
        remember { mutableStateOf(ShownEnabled) },
    content: @Composable (
        innerPadding: PaddingValues,
        backSelectedEvent: Flow<BackSelected>,
        menuSelectedEvent: Flow<MenuSelected>,
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
    showBackIcon: MutableState<ShowIconState> =
        remember { mutableStateOf(ShownEnabled) },
    showMenuIcon: MutableState<ShowIconState> =
        remember { mutableStateOf(ShownEnabled) },
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
    val backSelected: Flow<BackSelected>,
    val menuSelected: Flow<MenuSelected>
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

private fun Flow<ActionBarOneTimeEvent>.transformBackSelected(): Flow<BackSelected> =
    transform { event ->
        if (event is BackSelected)
            emit(event)
    }

private fun Flow<ActionBarOneTimeEvent>.transformMenuSelected(): Flow<MenuSelected> =
    transform { event ->
        if (event is MenuSelected)
            emit(event)
    }
