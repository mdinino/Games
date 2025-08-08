package dinino.marc.games.userflow.tetris.data

import dinino.marc.games.userflow.common.data.GameData
import dinino.marc.games.userflow.common.data.GamePlayData
import kotlinx.serialization.Serializable


@Serializable
data class TetrisGameData(
    override val playData: GamePlayData<Unit> = GamePlayData.Normal(),
    override val boardData: BoardData = BoardData()
): GameData<Unit, TetrisGameData.BoardData> {
    @Serializable
    data class BoardData(val string: String = "")
}

