package dinino.marc.games.userflow.tictactoe.data

import dinino.marc.games.userflow.common.data.GameData
import dinino.marc.games.userflow.common.data.GameState
import kotlinx.serialization.Serializable

@Serializable
data class TicTacToeGameData(
    override val gameState: TicTacToeGameState = TicTacToeGameState.Normal,
    override val boardData: TicTacToeBoardData = TicTacToeBoardData()
) : GameData<TicTacToeGameState, TicTacToeBoardData>


@Serializable sealed interface TicTacToeGameState: GameState {
    @Serializable data object Normal: TicTacToeGameState
    @Serializable sealed interface Paused: TicTacToeGameState, GameState.Paused {
        @Serializable data object UserPaused: Paused, GameState.Paused.UserPaused
        @Serializable data object UserToConfirmGameOver: Paused, GameState.Paused.UserToConfirmGameOver
    }
    @Serializable sealed interface GameOver: TicTacToeGameState, GameState.GameOver {
        @Serializable data object UserInitiatedGameOver : GameOver, GameState.GameOver.UserInitiatedGameOver
        @Serializable data object PlayerXWins : GameOver, GameState.GameOver.UserInitiatedGameOver
        @Serializable data object PlayerOWins : GameOver, GameState.GameOver.UserInitiatedGameOver
    }
}

@Serializable data class TicTacToeBoardData(val json: String = "")