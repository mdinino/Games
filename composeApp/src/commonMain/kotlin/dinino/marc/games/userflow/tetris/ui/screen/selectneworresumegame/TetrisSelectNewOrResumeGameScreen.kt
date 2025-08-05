package dinino.marc.games.userflow.tetris.ui.screen.selectneworresumegame

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import dinino.marc.games.userflow.common.ui.route.navigateTo
import dinino.marc.games.userflow.common.ui.screen.selectneworresumegame.SelectNewOrResumeGameOneTimeEvent
import dinino.marc.games.userflow.common.ui.screen.selectneworresumegame.SelectNewOrResumeGameScreen
import dinino.marc.games.userflow.tetris.ui.screen.game.TetrisGameRoute
import dinino.marc.games.userflow.tetris.ui.screen.gameover.TetrisGameOverRoute
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun TetrisSelectNewOrResumeGameScreen(
    modifier: Modifier,
    navHostController: NavHostController,
    vm: TetrisSelectNewOrResumeGameViewModel = koinViewModel()
) {
    SelectNewOrResumeGameScreen(
        modifier = modifier,
        navHostController = navHostController,
        vm = vm,
        oneTimeEventHandler = { navHostController, event ->
            when(event) {
                SelectNewOrResumeGameOneTimeEvent.NewGameSelected ->
                    navHostController.navigateTo(TetrisGameRoute(newGame = true))
                SelectNewOrResumeGameOneTimeEvent.ResumeGameSelected ->
                    navHostController.navigateTo(TetrisGameRoute(newGame = false))
            }
        }
    )
}