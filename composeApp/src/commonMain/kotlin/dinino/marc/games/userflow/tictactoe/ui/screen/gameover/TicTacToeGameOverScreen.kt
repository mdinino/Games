package dinino.marc.games.userflow.tictactoe.ui.screen.gameover

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import dinino.marc.games.userflow.common.ui.route.navigateTo
import dinino.marc.games.userflow.common.ui.route.navigateUpTo
import dinino.marc.games.userflow.common.ui.screen.gameover.GameOverOneTimeEvent
import dinino.marc.games.userflow.common.ui.screen.gameover.GameOverScreen
import dinino.marc.games.userflow.selectgame.ui.SelectGameNavGraphRoute
import dinino.marc.games.userflow.tictactoe.ui.screen.game.TicTacToeGameRoute
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
        vm = vm,
        oneTimeEventHandler = { navHostController, oneTimeEvent ->
            when(oneTimeEvent) {
                GameOverOneTimeEvent.StartNewGameSelected ->
                    navHostController.navigateTo(TicTacToeGameRoute())
                GameOverOneTimeEvent.SelectDifferentGameSelected ->
                    navHostController.navigateUpTo(SelectGameNavGraphRoute)
            }
        }
    )
}