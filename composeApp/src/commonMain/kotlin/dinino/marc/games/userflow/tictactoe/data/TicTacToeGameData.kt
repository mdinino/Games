package dinino.marc.games.userflow.tictactoe.data

import dinino.marc.games.userflow.common.data.GameData
import dinino.marc.games.userflow.common.data.GamePlayData
import dinino.marc.games.userflow.tictactoe.data.TicTacToeGameData.BoardData.Grid.Companion.copy
import dinino.marc.games.userflow.tictactoe.data.TicTacToeCell.Companion.to
import kotlinx.serialization.Serializable

@Serializable
data class TicTacToeGameData(
    override val playData: GamePlayData<GameOverDetails> = GamePlayData.Normal(),
    override val boardData: BoardData = BoardData()
): GameData<TicTacToeGameData.GameOverDetails, TicTacToeGameData.BoardData> {

    @Serializable
    sealed interface GameOverDetails {
        @Serializable
        data class XWins(val winningCells: Set<TicTacToeCell>) : GameOverDetails

        @Serializable
        data class OWins(val winningCells: Set<TicTacToeCell>) : GameOverDetails

        @Serializable
        data object Draw : GameOverDetails
    }

    @Serializable
    data class BoardData(
        val turn: Entry = Entry.PlayerX,
        val grid: Grid = Grid()
    )  {
        @Suppress("PropertyName")
        @Serializable
        data class Grid(
            val `0x0`: Entry? = null,
            val `0x1`: Entry? = null,
            val `0x2`: Entry? = null,
            val `1x0`: Entry? = null,
            val `1x1`: Entry? = null,
            val `1x2`: Entry? = null,
            val `2x0`: Entry? = null,
            val `2x1`: Entry? = null,
            val `2x2`: Entry? = null
        ) {
            val entries: Map<TicTacToeCell, Entry?>
                by lazy {
                    mapOf (
                        0u to 0u to `0x0`,
                        0u to 1u to `0x1`,
                        0u to 2u to `0x2`,
                        1u to 0u to `1x0`,
                        1u to 1u to `1x1`,
                        1u to 2u to `1x2`,
                        2u to 0u to `2x0`,
                        2u to 1u to `2x1`,
                        2u to 2u to `2x2`
                    )
                }


            companion object {
                fun Map<TicTacToeCell, Entry?>.toGrid(): Grid =
                    let { map ->
                        Grid(
                            `0x0` = map[0u to 0u],
                            `0x1` = map[0u to 1u],
                            `0x2` = map[0u to 2u],
                            `1x0` = map[1u to 0u],
                            `1x1` = map[1u to 1u],
                            `1x2` = map[1u to 2u],
                            `2x0` = map[2u to 0u],
                            `2x1` = map[2u to 1u],
                            `2x2` = map[2u to 2u]
                        )
                    }

                fun Grid.copy(mutator: MutableMap<TicTacToeCell, Entry?>.() -> Unit): Grid =
                    entries.toMutableMap()
                        .apply(mutator)
                        .toGrid()
            }
        }

        @Serializable
        sealed interface Entry {
            @Serializable
            data object PlayerX: Entry

            @Serializable
            data object PlayerO: Entry
        }

        companion object {
            fun BoardData.copy(
                turn: Entry = this.turn,
                gridMutator: MutableMap<TicTacToeCell, Entry?>.()->Unit = {}
            ) = BoardData(
                    turn = turn,
                    grid = grid.copy(mutator = gridMutator)
            )

            fun BoardData.calculateGameOverDetails(player: Entry): GameOverDetails? {
                val winningCells = calculateWin(player)

                return when {
                    winningCells.isNotEmpty() -> {
                        when (player) {
                            Entry.PlayerX -> GameOverDetails.XWins(winningCells.flatten().toSet())
                            Entry.PlayerO -> GameOverDetails.OWins(winningCells.flatten().toSet())
                        }
                    }
                    areAllCellsFilled -> GameOverDetails.Draw
                    else -> null
                }
            }

            private val BoardData.areAllCellsFilled: Boolean
                get() = grid.entries.all { (_, entry) -> entry != null }

            private fun BoardData.calculateWin(player: Entry): Set<Set<TicTacToeCell>> =
                rowsColumnAndDiagonals.filter {
                    it.checkWin(player)
                }.toSet()

            context(boardData: BoardData)
            private fun Set<TicTacToeCell>.checkWin(player: Entry): Boolean =
                all { cell ->
                    boardData.grid.entries[cell] == player
                }
        }
    }
}

private val topRow = setOf<TicTacToeCell>(
    0u to 0u,
    0u to 1u,
    0u to 2u
)

private val middleRow = setOf<TicTacToeCell>(
    1u to 0u,
    1u to 1u,
    1u to 2u
)

private val bottomRow = setOf<TicTacToeCell>(
    2u to 0u,
    2u to 1u,
    2u to 2u
)

private val leftColumn = setOf<TicTacToeCell>(
    0u to 0u,
    1u to 0u,
    2u to 0u
)

private val middleColumn = setOf<TicTacToeCell>(
    0u to 1u,
    1u to 1u,
    2u to 1u
)

private val rightColumn = setOf<TicTacToeCell>(
    0u to 2u,
    1u to 2u,
    2u to 2u
)

private val topLeftToBottomRightDiagonal = setOf<TicTacToeCell>(
    0u to 0u,
    1u to 1u,
    2u to 2u
)

private val topRightToBottomLeftDiagonal = setOf<TicTacToeCell>(
    0u to 2u,
    1u to 1u,
    2u to 0u
)

private val rowsColumnAndDiagonals = setOf(
    topRow,
    middleRow,
    bottomRow,
    leftColumn,
    middleColumn,
    rightColumn,
    topLeftToBottomRightDiagonal,
    topRightToBottomLeftDiagonal
)


