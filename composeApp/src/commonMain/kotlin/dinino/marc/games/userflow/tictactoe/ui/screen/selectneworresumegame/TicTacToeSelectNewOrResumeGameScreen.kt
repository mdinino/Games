package dinino.marc.games.userflow.tictactoe.ui.screen.selectneworresumegame

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import dinino.marc.games.userflow.common.ui.route.navigateTo
import dinino.marc.games.userflow.common.ui.screen.selectneworresumegame.SelectNewOrResumeGameOneTimeEvent
import dinino.marc.games.userflow.common.ui.screen.selectneworresumegame.SelectNewOrResumeGameScreen
import dinino.marc.games.userflow.tictactoe.ui.screen.gameover.TicTacToeGameOverRoute
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
        oneTimeEventHandler = { navHostController, event ->
            when(event) {
                SelectNewOrResumeGameOneTimeEvent.NewGameSelected ->
                    navHostController.navigateTo(TicTacToeGameOverRoute)
                SelectNewOrResumeGameOneTimeEvent.ResumeGameSelected ->
                    navHostController.navigateTo(TicTacToeGameOverRoute)
            }
        }
    )
}