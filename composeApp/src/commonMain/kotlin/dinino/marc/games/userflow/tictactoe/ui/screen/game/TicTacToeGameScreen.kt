package dinino.marc.games.userflow.tictactoe.ui.screen.game

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import dinino.marc.games.userflow.common.ui.layout.DoNotMirrorForRTL
import dinino.marc.games.userflow.common.ui.layout.MenuSelected
import dinino.marc.games.userflow.common.ui.screen.game.GameScreen
import dinino.marc.games.userflow.tictactoe.data.TicTacToeGameData
import dinino.marc.games.userflow.tictactoe.ui.screen.gameover.TicTacToeGameOverRoute
import games.composeapp.generated.resources.Res
import games.composeapp.generated.resources.game_over
import games.composeapp.generated.resources.player_o_wins
import games.composeapp.generated.resources.player_x_wins
import kotlinx.coroutines.flow.Flow
import org.jetbrains.compose.resources.getString

@Composable
fun TicTacToeGameScreen(
    modifier: Modifier,
    navHostController: NavHostController,
    menuSelectedOneTimeEvent: Flow<MenuSelected>,
    vm: TicTacToeGameViewModel
) {
    GameScreen(
        modifier = modifier,
        navHostController = navHostController,
        vm = vm,
        menuSelectedOneTimeEvent = menuSelectedOneTimeEvent,
        localizedGameOverMessage = {
            getString( resource =
                when(it) {
                    is TicTacToeGameData.GameOverDetails.OWins -> Res.string.player_o_wins
                    is TicTacToeGameData.GameOverDetails.XWins -> Res.string.player_x_wins
                    null -> Res.string.game_over
                }
            )
        },
        gameOverRoute = { TicTacToeGameOverRoute }
    ) { _, board ->
        DoNotMirrorForRTL {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("Under Construction")
            }
        }
    }
}