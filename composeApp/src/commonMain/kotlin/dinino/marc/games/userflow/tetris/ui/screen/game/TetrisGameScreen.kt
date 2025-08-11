package dinino.marc.games.userflow.tetris.ui.screen.game

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import dinino.marc.games.userflow.common.ui.screen.game.GameScreen
import dinino.marc.games.userflow.tetris.ui.screen.gameover.TetrisGameOverRoute

@Composable
fun TetrisGameScreen(
    modifier: Modifier,
    navHostController: NavHostController,
    vm: TetrisGameViewModel
) {
    GameScreen(
        modifier = modifier,
        navHostController = navHostController,
        vm = vm,
        gameOverRoute = { TetrisGameOverRoute }
    )
}