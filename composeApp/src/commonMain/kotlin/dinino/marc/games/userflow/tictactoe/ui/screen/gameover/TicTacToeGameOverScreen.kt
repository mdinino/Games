package dinino.marc.games.userflow.tictactoe.ui.screen.gameover

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import dinino.marc.games.userflow.common.ui.screen.gameover.GameOverScreen
import dinino.marc.games.userflow.common.ui.screen.selectneworresumegame.SelectNewOrResumeGameScreen
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun TicTacToeGameOverScreen(
    modifier: Modifier,
    navHostController: NavHostController,
    vm: TicTacToeGameOverViewModel = koinViewModel()
) {
    GameOverScreen(
        modifier = modifier,
        navHostController = navHostController,
        vm = vm
    )
}