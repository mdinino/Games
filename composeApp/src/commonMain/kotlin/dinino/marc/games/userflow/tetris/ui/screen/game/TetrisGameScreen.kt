package dinino.marc.games.userflow.tetris.ui.screen.game

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import dinino.marc.games.userflow.common.ui.layout.MenuSelected
import dinino.marc.games.userflow.common.ui.screen.game.GameScreen
import dinino.marc.games.userflow.tetris.ui.screen.gameover.TetrisGameOverRoute
import kotlinx.coroutines.flow.Flow
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun TetrisGameScreen(
    modifier: Modifier,
    navHostController: NavHostController,
    menuSelectedOneTimeEvent: Flow<MenuSelected>,
    vm: TetrisGameViewModel = koinViewModel()
) {
    GameScreen(
        modifier = modifier,
        navHostController = navHostController,
        menuSelectedOneTimeEvent = menuSelectedOneTimeEvent,
        vm = vm,
        gameOverRoute = { TetrisGameOverRoute }
    ) { innerPadding, board ->
        // TODO
    }
}