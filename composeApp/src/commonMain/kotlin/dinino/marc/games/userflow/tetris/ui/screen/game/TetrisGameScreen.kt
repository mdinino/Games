package dinino.marc.games.userflow.tetris.ui.screen.game

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import dinino.marc.games.userflow.common.ui.layout.MenuSelected
import dinino.marc.games.userflow.common.ui.screen.game.GameScreen
import dinino.marc.games.userflow.tetris.ui.screen.gameover.TetrisGameOverRoute
import kotlinx.coroutines.flow.Flow

@Composable
fun TetrisGameScreen(
    modifier: Modifier,
    navHostController: NavHostController,
    menuSelectedOneTimeEvent: Flow<MenuSelected>,
    vm: TetrisGameViewModel
) {
    GameScreen(
        modifier = modifier,
        navHostController = navHostController,
        menuSelectedOneTimeEvent = menuSelectedOneTimeEvent,
        vm = vm,
        gameOverRoute = { TetrisGameOverRoute }
    ) { _, board ->
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Under Construction")
        }
    }
}