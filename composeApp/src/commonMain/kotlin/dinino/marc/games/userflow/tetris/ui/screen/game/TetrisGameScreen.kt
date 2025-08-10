package dinino.marc.games.userflow.tetris.ui.screen.game

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavHostController
import dinino.marc.games.userflow.common.ui.layout.ActionBarEvent
import dinino.marc.games.userflow.common.ui.screen.game.GameScreen
import dinino.marc.games.userflow.tetris.ui.screen.gameover.TetrisGameOverRoute
import kotlinx.coroutines.channels.ReceiveChannel
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun TetrisGameScreen(
    modifier: Modifier,
    navHostController: NavHostController,
    newGame: Boolean,
    actionBarOneTimeEvent: ReceiveChannel<ActionBarEvent.MenuSelected>,
    vm: TetrisGameViewModel = koinViewModel()
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    LaunchedEffect(newGame, vm) {
        lifecycleOwner.repeatOnLifecycle(state = Lifecycle.State.CREATED) {
            vm.resetToNewGame()
        }
    }

    GameScreen(
        modifier = modifier,
        navHostController = navHostController,
        vm = vm,
        actionBarOneTimeEvent = actionBarOneTimeEvent,
        gameOverRoute = { TetrisGameOverRoute }
    )
}