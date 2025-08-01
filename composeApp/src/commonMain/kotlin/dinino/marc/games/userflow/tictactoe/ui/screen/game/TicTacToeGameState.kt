package dinino.marc.games.userflow.tictactoe.ui.screen.game

import dinino.marc.games.userflow.common.ui.screen.game.GameState

typealias TicTacToeGameState = GameState<TicTacToeGameOverDetails, TicTacToeBoardState>

sealed interface TicTacToeGameOverDetails {
    object PlayerXWins: TicTacToeGameOverDetails
    object PlayerOWins: TicTacToeGameOverDetails
}

data class TicTacToeBoardState(val test: String = "")