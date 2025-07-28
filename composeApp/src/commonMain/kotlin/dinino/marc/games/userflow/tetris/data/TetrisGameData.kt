package dinino.marc.games.userflow.tetris.data

import dinino.marc.games.userflow.common.data.GameData
import dinino.marc.games.userflow.common.data.GameState
import kotlinx.serialization.Serializable

@Serializable data class TetrisGameData(
    override val gameState: TetrisGameState = TetrisGameState.Normal,
    override val boardData: TetrisBoardData = TetrisBoardData()
) : GameData<TetrisGameState, TetrisBoardData>


@Serializable sealed interface TetrisGameState: GameState {
    @Serializable data object Normal: TetrisGameState
    @Serializable sealed interface Paused: TetrisGameState, GameState.Paused {
        @Serializable data object UserPaused: Paused, GameState.Paused.UserPaused
        @Serializable data object UserToConfirmGameOver: Paused, GameState.Paused.UserToConfirmGameOver
    }
    @Serializable sealed interface GameOver: TetrisGameState, GameState.GameOver {
        @Serializable data object UserInitiatedGameOver : GameOver, GameState.GameOver.UserInitiatedGameOver
    }
}

@Serializable data class TetrisBoardData(val json: String = "")