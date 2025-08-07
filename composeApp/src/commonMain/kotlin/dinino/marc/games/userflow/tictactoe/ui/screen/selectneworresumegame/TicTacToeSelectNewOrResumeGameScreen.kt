package dinino.marc.games.userflow.tictactoe.ui.screen.selectneworresumegame

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import dinino.marc.games.userflow.common.ui.route.navigateForwardTo
import dinino.marc.games.userflow.common.ui.screen.selectneworresumegame.SelectNewOrResumeGameOneTimeEvent
import dinino.marc.games.userflow.common.ui.screen.selectneworresumegame.SelectNewOrResumeGameScreen
import dinino.marc.games.userflow.tictactoe.ui.screen.game.TicTacToeGameRoute
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
                    navHostController.navigateForwardTo(route = TicTacToeGameRoute(newGame = true))
                SelectNewOrResumeGameOneTimeEvent.ResumeGameSelected ->
                    navHostController.navigateForwardTo(route = TicTacToeGameRoute(newGame = false))
            }
        }
    )
}