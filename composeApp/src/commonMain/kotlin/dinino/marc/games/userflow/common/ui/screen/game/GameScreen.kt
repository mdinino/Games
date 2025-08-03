package dinino.marc.games.userflow.common.ui.screen.game

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import dinino.marc.games.userflow.common.ui.ObserveOneTimeEventEffect
import games.composeapp.generated.resources.Res
import games.composeapp.generated.resources.game_over
import games.composeapp.generated.resources.ok
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import org.jetbrains.compose.resources.getString

@Composable
fun <GAME_OVER_STATE_DETAILS: Any, BOARD_STATE: Any>
        GameScreen(
    state: GameState<GAME_OVER_STATE_DETAILS, BOARD_STATE>,
    modifier: Modifier
        = Modifier,
    oneTimeEvents: Flow<GameOneTimeEvent>
        = emptyFlow(),
    onOneTimeEvent: (oneTimeEvent: GameOneTimeEvent) -> Unit
        = {},
    localizedGameOverMessage: suspend (gameOverDetails: GAME_OVER_STATE_DETAILS?) -> String
        = { getString(Res.string.game_over) },
    onGameOverAccepted: ()->Unit
        = {},
    content: @Composable (innerPadding: PaddingValues, board: BOARD_STATE) -> Unit
        = { _,_ -> }
) {
    ObserveOneTimeEventEffect(oneTimeEvents = oneTimeEvents) { oneTimeEvent ->
        onOneTimeEvent(oneTimeEvent)
    }

    val gameScreenSnackbarHostState = remember { SnackbarHostState() }
    Scaffold(
        modifier = modifier,
        snackbarHost = { SnackbarHost(hostState = gameScreenSnackbarHostState) },
    ) { innerPadding -> content(innerPadding, state.board) }

    // TODO
    gameScreenSnackbarHostState.showGameOverNotification(
        localizedMessage = { localizedGameOverMessage(null) },
        onAction = onGameOverAccepted
    )
}

private suspend fun SnackbarHostState.showGameOverNotification(
    localizedMessage: suspend ()->String =
        { getString(Res.string.game_over) },
    localizedActionLabel: suspend ()->String =
        { getString(Res.string.ok) },
    onAction: ()->Unit
) {
    dismissGameOverNotification()
    val result = showSnackbar(
        message = localizedMessage(),
        withDismissAction = false,
        actionLabel = localizedActionLabel(),
        duration = SnackbarDuration.Indefinite
    )

    if (result == SnackbarResult.ActionPerformed)
        onAction()
}

private fun SnackbarHostState.dismissGameOverNotification() =
    currentSnackbarData?.dismiss()
