package dinino.marc.games.userflow.tetris.ui.screen.gameover

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import dinino.marc.games.userflow.common.ui.route.navigateTo
import dinino.marc.games.userflow.common.ui.route.navigateUpTo
import dinino.marc.games.userflow.common.ui.screen.gameover.GameOverOneTimeEvent
import dinino.marc.games.userflow.common.ui.screen.gameover.GameOverScreen
import dinino.marc.games.userflow.selectgame.ui.SelectGameNavGraphRoute
import dinino.marc.games.userflow.tetris.ui.screen.game.TetrisGameRoute
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun TetrisGameOverScreen(
    modifier: Modifier,
    navHostController: NavHostController,
    vm: TetrisGameOverViewModel = koinViewModel()
) {
    GameOverScreen(
        modifier = modifier,
        navHostController = navHostController,
        vm = vm,
        oneTimeEventHandler = { navHostController, oneTimeEvent ->
            when(oneTimeEvent) {
                GameOverOneTimeEvent.StartNewGameSelected ->
                    navHostController.navigateTo(TetrisGameRoute())
                GameOverOneTimeEvent.SelectDifferentGameSelected ->
                    navHostController.navigateUpTo(SelectGameNavGraphRoute)
            }
        }
    )
}