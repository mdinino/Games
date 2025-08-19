package dinino.marc.games.userflow.common.ui.screen.game

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import dinino.marc.games.userflow.common.ui.ObserveOneTimeEventEffect
import dinino.marc.games.userflow.common.ui.layout.ActionBarOneTimeEvent
import dinino.marc.games.userflow.common.ui.route.GameUserFlowNavGraphRoute
import dinino.marc.games.userflow.common.ui.route.navigateForwardTo
import games.composeapp.generated.resources.Res
import games.composeapp.generated.resources.game_hidden_while_paused
import games.composeapp.generated.resources.game_over
import games.composeapp.generated.resources.ok
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.getString

@Composable
fun <GAME_OVER_STATE_DETAILS: Any, BOARD_STATE: Any>
        GameScreen(
    vm: GameViewModel<*, *, GAME_OVER_STATE_DETAILS, BOARD_STATE>,
    modifier: Modifier = Modifier,
    navHostController: NavHostController,
    menuSelectedOneTimeEvent: Flow<ActionBarOneTimeEvent.MenuSelected>,
    gameOverRoute: (gameOverDetails: GAME_OVER_STATE_DETAILS?) -> GameUserFlowNavGraphRoute.GameOverRoute,
    localizedGameOverMessage: suspend (gameOverDetails: GAME_OVER_STATE_DETAILS?) -> String
        = { getString(Res.string.game_over) },
    content: @Composable (innerPadding: PaddingValues, board: BOARD_STATE) -> Unit = { _, _ -> }
) = GameScreen(
        vm = vm,
        modifier = modifier,
        menuSelectedOneTimeEvent = menuSelectedOneTimeEvent,
        onViewModelOneTimeEvent = { event ->
            when(event) {
                is GameOneTimeEvent.NavigateToGameOverScreen<GAME_OVER_STATE_DETAILS> ->
                    navHostController.navigateForwardTo(
                        route = gameOverRoute(event.gameOverDetails)
                    )
            }
        },
        localizedGameOverMessage = localizedGameOverMessage,
        content = content
)

@Composable
private fun <GAME_OVER_STATE_DETAILS: Any, BOARD_STATE: Any>
        GameScreen(
    vm: GameViewModel<*, *, GAME_OVER_STATE_DETAILS, BOARD_STATE>,
    modifier: Modifier = Modifier,
    menuSelectedOneTimeEvent: Flow<ActionBarOneTimeEvent.MenuSelected> = emptyFlow(),
    onViewModelOneTimeEvent: (event: GameOneTimeEvent<GAME_OVER_STATE_DETAILS>) -> Unit = {},
    localizedGameOverMessage: suspend (gameOverDetails: GAME_OVER_STATE_DETAILS?) -> String
        = { getString(Res.string.game_over) },
    content: @Composable (innerPadding: PaddingValues, board: BOARD_STATE) -> Unit = { _, _ -> }
) = GameScreen(
        state = vm.gameState,
        coroutineScope = rememberCoroutineScope(),
        modifier = modifier,
        menuSelectedOneTimeEvent = menuSelectedOneTimeEvent,
        onMenuSelectedOneTimeEvent = {vm.togglePause() },
        viewModelOneTimeEvent = vm.oneTimeEvent,
        onViewModelOneTimeEvent = onViewModelOneTimeEvent,
        onPausePopupHidden = {vm.unPause() },
        localizedGameOverMessage = localizedGameOverMessage,
        onGameOverAccepted = { vm.navigateToGameOverScreen(clearGame = true) },
        content = content
)

@Composable
private fun <GAME_OVER_STATE_DETAILS: Any, BOARD_STATE: Any>
        GameScreen(
    state: StateFlow<GameState<GAME_OVER_STATE_DETAILS, BOARD_STATE>>,
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    modifier: Modifier = Modifier,
    menuSelectedOneTimeEvent: Flow<ActionBarOneTimeEvent.MenuSelected> = emptyFlow(),
    onMenuSelectedOneTimeEvent: (event: ActionBarOneTimeEvent.MenuSelected) -> Unit = {},
    viewModelOneTimeEvent: Flow<GameOneTimeEvent<GAME_OVER_STATE_DETAILS>> = emptyFlow(),
    onViewModelOneTimeEvent: (event: GameOneTimeEvent<GAME_OVER_STATE_DETAILS>) -> Unit = {},
    onPausePopupHidden: ()->Unit = {},
    localizedGameOverMessage: suspend (gameOverDetails: GAME_OVER_STATE_DETAILS?) -> String =
        { getString(Res.string.game_over) },
    onGameOverAccepted: (gameOVerDetails: GAME_OVER_STATE_DETAILS?)->Unit = {},
    content: @Composable (innerPadding: PaddingValues, board: BOARD_STATE) -> Unit = { _, _ -> }
) {
    val currentState = state.collectAsStateWithLifecycle()
    GameScreen(
        state = currentState.value,
        coroutineScope = coroutineScope,
        modifier = modifier,
        menuSelectedOneTimeEvent = menuSelectedOneTimeEvent,
        onMenuSelectedOneTimeEvent = onMenuSelectedOneTimeEvent,
        viewModelOneTimeEvent = viewModelOneTimeEvent,
        onViewModelOneTimeEvent = onViewModelOneTimeEvent,
        onPausePopupHidden = onPausePopupHidden,
        localizedGameOverMessage = localizedGameOverMessage,
        onGameOverAccepted = onGameOverAccepted,
        content = content
    )
}

@Composable
private fun <GAME_OVER_STATE_DETAILS: Any, BOARD_STATE: Any>
        GameScreen(
    state: GameState<GAME_OVER_STATE_DETAILS, BOARD_STATE>,
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    modifier: Modifier = Modifier,
    menuSelectedOneTimeEvent: Flow<ActionBarOneTimeEvent.MenuSelected> = emptyFlow(),
    onMenuSelectedOneTimeEvent: (event: ActionBarOneTimeEvent.MenuSelected) -> Unit = {},
    viewModelOneTimeEvent: Flow<GameOneTimeEvent<GAME_OVER_STATE_DETAILS>> = emptyFlow(),
    onViewModelOneTimeEvent: (event: GameOneTimeEvent<GAME_OVER_STATE_DETAILS>) -> Unit = {},
    onPausePopupHidden: ()->Unit = {},
    localizedGameOverMessage: suspend (gameOverDetails: GAME_OVER_STATE_DETAILS?) -> String =
        { getString(Res.string.game_over) },
    onGameOverAccepted: (gameOverDetails: GAME_OVER_STATE_DETAILS?)->Unit = {},
    content: @Composable (innerPadding: PaddingValues, board: BOARD_STATE) -> Unit = { _, _ -> }
) {
    val isPausePopupVisible: MutableStateFlow<Boolean> =
        remember {
            MutableStateFlow(
                value = state is GameState.Paused
            )
        }

    ScreenWithGamePausedPopup(isPopupVisible = isPausePopupVisible) {

        ObserveOneTimeEventEffect(oneTimeEvents = menuSelectedOneTimeEvent) { oneTimeEvent ->
            onMenuSelectedOneTimeEvent(oneTimeEvent)
        }
        ObserveOneTimeEventEffect(oneTimeEvents = viewModelOneTimeEvent) { oneTimeEvent ->
            onViewModelOneTimeEvent(oneTimeEvent)
        }
        ObserveOneTimeEventEffect(oneTimeEvents = isPausePopupVisible) { visible ->
            if (!visible) onPausePopupHidden()
        }

        val gameScreenSnackbarHostState = remember { SnackbarHostState() }
        Scaffold(
            modifier = modifier,
            snackbarHost = { SnackbarHost(hostState = gameScreenSnackbarHostState) },
        ) { innerPadding -> content(innerPadding, state.board) }

        coroutineScope.launch {
            when(state) {
                is GameState.Normal -> {
                    isPausePopupVisible.value = false
                    gameScreenSnackbarHostState.dismissNotifications()
                }
                is GameState.Paused -> {
                    isPausePopupVisible.value = true
                    gameScreenSnackbarHostState.showGameHiddenWhilePausedNotification()
                }
                is GameState.GameOver<GAME_OVER_STATE_DETAILS, BOARD_STATE> -> {
                    isPausePopupVisible.value = false
                    gameScreenSnackbarHostState.showGameOverNotification(
                        localizedMessage = { localizedGameOverMessage(state.details) },
                        onAction = { onGameOverAccepted(state.details) }
                    )
                }
            }
        }
    }
}

private suspend fun SnackbarHostState.showGameOverNotification(
    localizedMessage: suspend ()->String =
        { getString(Res.string.game_over) },
    localizedActionLabel: suspend ()->String =
        { getString(Res.string.ok) },
    onAction: ()->Unit
) {
    dismissNotifications()
    val result = showSnackbar(
        message = localizedMessage(),
        withDismissAction = false,
        actionLabel = localizedActionLabel(),
        duration = SnackbarDuration.Indefinite
    )

    if (result == SnackbarResult.ActionPerformed)
        onAction()
}

private suspend fun SnackbarHostState.showGameHiddenWhilePausedNotification(
    localizedMessage: suspend ()->String =
        { getString(Res.string.game_hidden_while_paused) },
) {
    dismissNotifications()
    showSnackbar(
        message = localizedMessage(),
        withDismissAction = false,
        duration = SnackbarDuration.Indefinite
    )
}

private fun SnackbarHostState.dismissNotifications() =
    currentSnackbarData?.dismiss()
