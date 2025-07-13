package dinino.marc.games.userflow.tictactoe.ui.screen.selectneworresumegame

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import dinino.marc.games.userflow.common.ui.selectneworresumegame.SelectNewOrResumeGameScreen
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun TicTacToeSelectNewOrResumeGameScreen(
    modifier: Modifier,
    navHostController: NavHostController,
    vm: TicTacToeSelectNewOrResumeGameViewModel = koinViewModel()
) {
    SelectNewOrResumeGameScreen(
        modifier = modifier,
        navHostController = navHostController,
        vm = vm,
        initialState = {
            TicTacToeSelectNewOrResumeGameState(
                isSelectNewGameAvailable = true,
                isSelectResumeGameAvailable = false
            )
        }
    )
}