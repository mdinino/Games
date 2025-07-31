package dinino.marc.games.userflow.tictactoe.data

import dinino.marc.games.userflow.common.data.GameData
import dinino.marc.games.userflow.common.data.GamePlayData
import kotlinx.serialization.Serializable


@Serializable
data class TicTacToeGameData(
    override val playData: GamePlayData<GameOverDetails> = GamePlayData.Normal(),
    override val boardData: BoardData = BoardData()
): GameData<TicTacToeGameData.GameOverDetails, TicTacToeGameData.BoardData> {
    @Serializable
    data class BoardData(val string: String = "")

    @Serializable
    sealed interface GameOverDetails {
        @Serializable data object XWins: GameOverDetails
        @Serializable data object OWins: GameOverDetails
    }
}

