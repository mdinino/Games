package dinino.marc.games.userflow.tictactoe.ui.screen.game

import dinino.marc.games.userflow.common.ui.screen.game.GameState
import dinino.marc.games.userflow.tictactoe.data.TicTacToeGameData

typealias TicTacToeGameState = GameState<TicTacToeGameData.GameOverDetails, TicTacToeBoardState>

data class TicTacToeBoardState(val test: String = "")