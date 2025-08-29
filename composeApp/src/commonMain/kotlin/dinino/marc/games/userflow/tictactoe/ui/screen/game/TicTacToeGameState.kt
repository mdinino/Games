package dinino.marc.games.userflow.tictactoe.ui.screen.game

import dinino.marc.games.userflow.common.ui.screen.game.GameState
import dinino.marc.games.userflow.tictactoe.data.TicTacToeCell
import dinino.marc.games.userflow.tictactoe.data.TicTacToeGameData
import dinino.marc.games.userflow.tictactoe.ui.screen.game.TicTacToeBoardState.Entry

typealias TicTacToeGameState = GameState<TicTacToeGameData.GameOverDetails, TicTacToeGameData.BoardData>

val defaultTicTacToeBoardState = TicTacToeGameData.BoardData()


sealed interface TicTacToeGameOverState {
    data object PlayerXWon: TicTacToeGameOverState
    data object PlayerOWon: TicTacToeGameOverState
    data object Draw: TicTacToeGameOverState
}


data class TicTacToeBoardState <TURN> (
    val turn: TURN,
    val grid: Map<TicTacToeCell, Entry>
) where TURN: Entry.Player, TURN:  Entry.Variant.Normal {


    sealed interface Entry {

        sealed interface Player: Entry {
            interface X: Player
            interface O: Player
        }

        sealed interface Variant: Entry {
            interface Normal: Variant
            interface Winner: Variant
        }
    }
}

