package dinino.marc.games.userflow.tictactoe.ui.screen.game

import dinino.marc.games.userflow.common.ui.screen.game.GameUiState


sealed interface TicTacToeGameUiState: GameUiState<TicTacToeGameUiState.Board> {

    class Normal(override val boardState: Board): TicTacToeGameUiState, GameUiState.Normal<Board>

    sealed class Paused(override val boardState: Board = Board.paused)
        : TicTacToeGameUiState, GameUiState.Paused<Board> {
            data object UserPaused: Paused(), GameUiState.Paused.UserPaused<Board>
            data object UserToConfirmGameOver: Paused(), GameUiState.Paused.UserToConfirmGameOver<Board>
    }

    sealed class GameOver(override val boardState: Board = Board.paused)
        : TicTacToeGameUiState, GameUiState.GameOver<Board> {
            data object UserInitialedGameOver: GameOver(), GameUiState.GameOver.UserInitiatedGameOver<Board>
            data object PlayerXWins: GameOver(), GameUiState.GameOver<Board>
            data object PlayerOWins: GameOver(), GameUiState.GameOver<Board>
    }

    data class Board(val foo: String = "") {
        companion object {
            val empty: Board
                get() = TODO()

            val paused: Board
                get() = TODO()
        }
    }
}