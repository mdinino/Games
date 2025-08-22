package dinino.marc.games.userflow.common.ui.screen.game

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Button
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import dinino.marc.games.app.ui.theme.sizes.sizes
import dinino.marc.games.userflow.common.ui.ObserveOneTimeEventEffect
import dinino.marc.games.userflow.common.ui.layout.ElevatedOutlinedCard
import dinino.marc.games.userflow.common.ui.layout.MenuSelected
import dinino.marc.games.userflow.common.ui.route.GameUserFlowNavGraphRoute
import dinino.marc.games.userflow.common.ui.route.navigateForwardTo
import games.composeapp.generated.resources.Res
import games.composeapp.generated.resources.game_hidden_while_paused
import games.composeapp.generated.resources.game_over
import games.composeapp.generated.resources.ok
import games.composeapp.generated.resources.pause_popup_end_game
import games.composeapp.generated.resources.pause_popup_restart_game
import games.composeapp.generated.resources.pause_popup_title
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.getString
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun <GAME_OVER_STATE_DETAILS: Any, BOARD_STATE: Any>
        GameScreen(
    vm: GameViewModel<*, *, GAME_OVER_STATE_DETAILS, BOARD_STATE>,
    modifier: Modifier = Modifier,
    navHostController: NavHostController,
    snackbarCoroutineScope: CoroutineScope = rememberCoroutineScope(),
    menuSelectedOneTimeEvent: Flow<MenuSelected>,
    gameOverRoute: (gameOverDetails: GAME_OVER_STATE_DETAILS?) -> GameUserFlowNavGraphRoute.GameOverRoute,
    localizedGameOverMessage: suspend (gameOverDetails: GAME_OVER_STATE_DETAILS?) -> String
        = { getString(Res.string.game_over) },
    content: @Composable (innerPadding: PaddingValues, board: BOARD_STATE) -> Unit
) = GameScreen(
        vm = vm,
        modifier = modifier,
        snackbarCoroutineScope = snackbarCoroutineScope,
        menuSelectedOneTimeEvent = menuSelectedOneTimeEvent,
        onPausedPopupOneTimeEvent = { event ->
            when(event) {
                GamePausedPopupOneTimeEvent.RestartGameSelected -> vm.resetToNewGame()
                GamePausedPopupOneTimeEvent.EndGameSelected -> vm.userInitiatedGameOver()
                GamePausedPopupOneTimeEvent.PopupDismissed -> vm.unPause()
            }
        },
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
    modifier: Modifier,
    snackbarCoroutineScope: CoroutineScope,
    menuSelectedOneTimeEvent: Flow<MenuSelected>,
    onPausedPopupOneTimeEvent: (event: GamePausedPopupOneTimeEvent)->Unit,
    onViewModelOneTimeEvent: (event: GameOneTimeEvent<GAME_OVER_STATE_DETAILS>)->Unit,
    localizedGameOverMessage: suspend (gameOverDetails: GAME_OVER_STATE_DETAILS?)->String,
    content: @Composable (innerPadding: PaddingValues, board: BOARD_STATE)->Unit
) = GameScreen(
        state = vm.gameState,
        modifier = modifier,
        snackbarCoroutineScope = snackbarCoroutineScope,
        menuSelectedOneTimeEvent = menuSelectedOneTimeEvent,
        onMenuSelectedOneTimeEvent = { vm.togglePause() },
        onPausedPopupOneTimeEvent = onPausedPopupOneTimeEvent,
        viewModelOneTimeEvent = vm.oneTimeEvent,
        onViewModelOneTimeEvent = onViewModelOneTimeEvent,
        localizedGameOverMessage = localizedGameOverMessage,
        onGameOverAccepted = { vm.navigateToGameOverScreen(clearGame = true) },
        content = content
)

@Composable
private fun <GAME_OVER_STATE_DETAILS: Any, BOARD_STATE: Any>
        GameScreen(
    state: StateFlow<GameState<GAME_OVER_STATE_DETAILS, BOARD_STATE>>,
    modifier: Modifier,
    snackbarCoroutineScope: CoroutineScope,
    menuSelectedOneTimeEvent: Flow<MenuSelected>,
    onMenuSelectedOneTimeEvent: (event: MenuSelected)->Unit ,
    onPausedPopupOneTimeEvent: (event: GamePausedPopupOneTimeEvent)->Unit,
    viewModelOneTimeEvent: Flow<GameOneTimeEvent<GAME_OVER_STATE_DETAILS>>,
    onViewModelOneTimeEvent: (event: GameOneTimeEvent<GAME_OVER_STATE_DETAILS>)->Unit,
    localizedGameOverMessage: suspend (gameOverDetails: GAME_OVER_STATE_DETAILS?)->String,
    onGameOverAccepted: (gameOVerDetails: GAME_OVER_STATE_DETAILS?)->Unit,
    content: @Composable (innerPadding: PaddingValues, board: BOARD_STATE)->Unit
) {
    val currentState = state.collectAsStateWithLifecycle()
    GameScreen(
        state = currentState.value,
        modifier = modifier,
        snackbarCoroutineScope = snackbarCoroutineScope,
        menuSelectedOneTimeEvent = menuSelectedOneTimeEvent,
        onMenuSelectedOneTimeEvent = onMenuSelectedOneTimeEvent,
        onPausedPopupOneTimeEvent = onPausedPopupOneTimeEvent,
        viewModelOneTimeEvent = viewModelOneTimeEvent,
        onViewModelOneTimeEvent = onViewModelOneTimeEvent,
        localizedGameOverMessage = localizedGameOverMessage,
        onGameOverAccepted = onGameOverAccepted,
        content = content
    )
}

@Composable
private fun <GAME_OVER_STATE_DETAILS: Any, BOARD_STATE: Any>
        GameScreen(
    state: GameState<GAME_OVER_STATE_DETAILS, BOARD_STATE>,
    modifier: Modifier,
    snackbarCoroutineScope: CoroutineScope,
    menuSelectedOneTimeEvent: Flow<MenuSelected>,
    onMenuSelectedOneTimeEvent: (event: MenuSelected)->Unit,
    onPausedPopupOneTimeEvent: (event: GamePausedPopupOneTimeEvent)->Unit,
    viewModelOneTimeEvent: Flow<GameOneTimeEvent<GAME_OVER_STATE_DETAILS>>,
    onViewModelOneTimeEvent: (event: GameOneTimeEvent<GAME_OVER_STATE_DETAILS>) -> Unit,
    localizedGameOverMessage: suspend (gameOverDetails: GAME_OVER_STATE_DETAILS?) -> String,
    onGameOverAccepted: (gameOverDetails: GAME_OVER_STATE_DETAILS?)->Unit,
    content: @Composable (innerPadding: PaddingValues, board: BOARD_STATE)->Unit
) {
    ObserveOneTimeEventEffect(oneTimeEvents = viewModelOneTimeEvent) { oneTimeEvent ->
        onViewModelOneTimeEvent(oneTimeEvent)
    }
    ObserveOneTimeEventEffect(oneTimeEvents = menuSelectedOneTimeEvent) { oneTimeEvent ->
        onMenuSelectedOneTimeEvent(oneTimeEvent)
    }

    val gameScreenSnackbarHostState = remember { SnackbarHostState() }
    Scaffold(
        modifier = modifier,
        snackbarHost = { SnackbarHost(hostState = gameScreenSnackbarHostState) }
    ) { innerPadding -> content(innerPadding, state.board) }

    when(state) {
        is GameState.Normal -> {
            gameScreenSnackbarHostState.dismissNotifications()
        }
        is GameState.Paused -> {
            gameScreenSnackbarHostState.showGameHiddenWhilePausedNotification(
                on = snackbarCoroutineScope
            )
            ShowGamePausedPopup(onEvent = onPausedPopupOneTimeEvent)
        }
        is GameState.GameOver<GAME_OVER_STATE_DETAILS, BOARD_STATE> -> {
            gameScreenSnackbarHostState.showGameOverNotification(
                on = snackbarCoroutineScope,
                localizedMessage = { localizedGameOverMessage(state.details) },
                onAction = { onGameOverAccepted(state.details) }
            )
        }
    }
}

private sealed interface GamePausedPopupOneTimeEvent {
    /**
     * User clicked on Restart Game button
     */
    object RestartGameSelected: GamePausedPopupOneTimeEvent

    /**
     * User clicked on End Game button
     */
    object EndGameSelected: GamePausedPopupOneTimeEvent

    /**
     * User dismissed popup. Only called if done by the user.
     */
    object PopupDismissed: GamePausedPopupOneTimeEvent
}

@Composable
private fun ShowGamePausedPopup(
    modifier: Modifier = Modifier.padding(MaterialTheme.sizes.spacings.large),
    alignment: Alignment = Alignment.Center,
    properties: PopupProperties = PopupProperties(
        focusable = true,
        dismissOnClickOutside = true,
        dismissOnBackPress = true
    ),
    onEvent: (event: GamePausedPopupOneTimeEvent)->Unit
) {
    Popup(
        onDismissRequest = { onEvent(GamePausedPopupOneTimeEvent.PopupDismissed) },
        alignment = alignment,
        properties = properties,
    ) {
        GamePausedLayout(
            modifier = modifier,
            onRestartGameSelected = { onEvent(GamePausedPopupOneTimeEvent.RestartGameSelected) },
            onEndGameSelected = { onEvent(GamePausedPopupOneTimeEvent.EndGameSelected) }
        )
    }
}

@Composable
@Preview
private fun GamePausedLayout(
    modifier: Modifier = Modifier.padding(MaterialTheme.sizes.spacings.large),
    onRestartGameSelected: ()->Unit = {},
    onEndGameSelected: ()->Unit = {},
) {
    ElevatedOutlinedCard {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier
        ) {
            Text(
                textAlign = TextAlign.Center,
                text = stringResource(Res.string.pause_popup_title),
            )

            Spacer(modifier = Modifier.height(MaterialTheme.sizes.spacings.medium))

            Button(
                modifier = Modifier
                    .width(MaterialTheme.sizes.buttons.medium.width)
                    .height(MaterialTheme.sizes.buttons.medium.height),
                onClick = onRestartGameSelected
            ) {
                Text(stringResource(Res.string.pause_popup_restart_game))
            }

            Spacer(modifier = Modifier.height(MaterialTheme.sizes.spacings.extraSmall))

            Button(
                modifier = Modifier
                    .width(MaterialTheme.sizes.buttons.medium.width)
                    .height(MaterialTheme.sizes.buttons.medium.height),
                onClick = onEndGameSelected
            ) {
                Text(stringResource(Res.string.pause_popup_end_game))
            }
        }
    }
}

private fun SnackbarHostState.showGameOverNotification(
    on: CoroutineScope,
    localizedMessage: suspend ()->String =
        { getString(Res.string.game_over) },
    localizedActionLabel: suspend ()->String =
        { getString(Res.string.ok) },
    onAction: ()->Unit
) {
    on.launch {
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
}

private fun SnackbarHostState.showGameHiddenWhilePausedNotification(
    on: CoroutineScope,
    localizedMessage: suspend ()->String = {
        getString(Res.string.game_hidden_while_paused)
    }
) {
    on.launch {
        dismissNotifications()
        showSnackbar(
            message = localizedMessage(),
            withDismissAction = false,
            duration = SnackbarDuration.Indefinite
        )
    }
}

private fun SnackbarHostState.dismissNotifications() =
    currentSnackbarData?.dismiss()

