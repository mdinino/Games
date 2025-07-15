package dinino.marc.games.userflow.common.ui.screen.selectneworresumegame

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import dinino.marc.games.userflow.common.ui.ObserveOneTimeEventEffect
import dinino.marc.games.userflow.common.ui.layout.SelectNewOrResumeGameLayout
import dinino.marc.games.userflow.common.ui.route.SerializableUserFlowRoute.Companion.navigateToRoute
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

@Composable
fun <GAME: Any,
     STATE: SelectNewOrResumeGameState,
     ONE_TIME_EVENT: SelectNewOrResumeGameOneTimeEvent,
     VM: AbstractSelectNewOrResumeGameViewModel<GAME, STATE, ONE_TIME_EVENT>
> SelectNewOrResumeGameScreen(
    modifier: Modifier = Modifier,
    navHostController: NavHostController,
    vm: VM
) {
    val state = vm.selectNewOrResumeGameState.collectAsState()

    val onNewGame: (()->Unit)? =
        when(state.value.isSelectNewGameAvailable) {
            true -> { { vm.selectNewGame() } }
            else -> null
        }

    val onResumeGame: (()->Unit)? =
        when(state.value.isSelectResumeGameAvailable) {
            true -> { { vm.selectResumeGame() } }
            else -> null
        }

    SelectNewOrResumeGameScreen(
        modifier = modifier,
        navHostController = navHostController,
        oneTimeEvents = vm.oneTimeEvents,
        onNewGameOrNullIfDisabled = onNewGame,
        onResumeGameOrNullIfDisabled = onResumeGame
    )
}

@Composable
fun <ONE_TIME_EVENT: SelectNewOrResumeGameOneTimeEvent>
        SelectNewOrResumeGameScreen(
            modifier: Modifier = Modifier,
            navHostController: NavHostController,
            oneTimeEvents: Flow<ONE_TIME_EVENT> = emptyFlow(),
            onNewGameOrNullIfDisabled : (()->Unit)? = {},
            onResumeGameOrNullIfDisabled : (()->Unit)? = null
) {
    oneTimeEvents.ObserveEffect(
        navHostController = navHostController
    )

    SelectNewOrResumeGameLayout(
        modifier = modifier,
        navHostController = navHostController,
        onNewGameOrNullIfDisabled = onNewGameOrNullIfDisabled,
        onResumeGameOrNullIfDisabled = onResumeGameOrNullIfDisabled
    )
}

@Composable
private fun <ONE_TIME_EVENT: SelectNewOrResumeGameOneTimeEvent>
        Flow<ONE_TIME_EVENT>.ObserveEffect(
            navHostController: NavHostController
) {
    ObserveOneTimeEventEffect(oneTimeEvents = this) { oneTimeEvent ->
        when(oneTimeEvent) {
            is SelectNewOrResumeGameOneTimeEvent.Navigate -> {
                navHostController.navigateToRoute(oneTimeEvent.route)
            }
        }
    }
}
