package dinino.marc.games.userflow.tetris.ui.screen.game

import dinino.marc.games.userflow.common.ui.screen.game.GameUiState


sealed interface TetrisGameUiState: GameUiState<TetrisGameUiState.Board> {

    class Normal(override val boardState: Board): TetrisGameUiState, GameUiState.Normal<Board>

    sealed class Paused(override val boardState: Board = Board.paused)
        : TetrisGameUiState, GameUiState.Paused<Board> {
            data object UserPaused: Paused(), GameUiState.Paused.UserPaused<Board>
            data object UserToConfirmGameOver: Paused(), GameUiState.Paused.UserToConfirmGameOver<Board>
    }

    sealed class GameOver(override val boardState: Board = Board.paused)
        : TetrisGameUiState, GameUiState.GameOver<Board> {
            data object UserInitialedGameOver: GameOver(), GameUiState.GameOver.UserInitiatedGameOver<Board>
            data object PlayerLost: GameOver(), GameUiState.GameOver<Board>
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