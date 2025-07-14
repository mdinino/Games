package dinino.marc.games.userflow.tetris.ui.screen.selectneworresumegame

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import dinino.marc.games.userflow.common.ui.selectneworresumegame.SelectNewOrResumeGameScreen
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
        vm = vm
    )
}