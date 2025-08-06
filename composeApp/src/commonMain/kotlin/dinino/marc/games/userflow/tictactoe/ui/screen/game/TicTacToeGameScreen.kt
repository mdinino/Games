package dinino.marc.games.userflow.tictactoe.ui.screen.game

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavHostController
import dinino.marc.games.userflow.common.ui.screen.game.GameScreen
import dinino.marc.games.userflow.tictactoe.data.TicTacToeGameData
import dinino.marc.games.userflow.tictactoe.ui.screen.gameover.TicTacToeGameOverRoute
import games.composeapp.generated.resources.Res
import games.composeapp.generated.resources.game_over
import games.composeapp.generated.resources.player_o_wins
import games.composeapp.generated.resources.player_x_wins
import org.jetbrains.compose.resources.getString
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun TicTacToeNewGameScreen(
    modifier: Modifier,
    navHostController: NavHostController,
    vm: TicTacToeGameViewModel = koinViewModel()
) = TicTacToeGameScreen(
    modifier = modifier,
    navHostController = navHostController,
    newGame = true,
    vm = vm
)

@Composable
fun TicTacToeResumeGameScreen(
    modifier: Modifier,
    navHostController: NavHostController,
    vm: TicTacToeGameViewModel = koinViewModel()
) = TicTacToeGameScreen(
    modifier = modifier,
    navHostController = navHostController,
    newGame = false,
    vm = vm
)

@Composable
private fun TicTacToeGameScreen(
    modifier: Modifier,
    navHostController: NavHostController,
    newGame: Boolean,
    vm: TicTacToeGameViewModel = koinViewModel()
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    LaunchedEffect(newGame, vm) {
        lifecycleOwner.repeatOnLifecycle(state = Lifecycle.State.CREATED) {
            vm.resetToNewGame()
        }
    }

    GameScreen(
        modifier = modifier,
        navHostController = navHostController,
        vm = vm,
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
    )
}