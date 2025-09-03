package dinino.marc.games.userflow.tictactoe.data

import dinino.marc.games.userflow.common.data.GameData
import dinino.marc.games.userflow.common.data.GamePlayData
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
    data class  BoardData(
        val turn: Entry = Entry.PlayerX,
        val grid: Grid = Grid()
    )  {
        fun copy(
            turn : Entry = this.turn,
            gridMutator: HashMap<TicTacToeCell, Entry?>.()->Unit
        ) = BoardData(
            turn = turn,
            grid = grid.copy(gridMutator)
        )

        @Serializable
        data class Grid private constructor(
            /*
             * Must be this signature in order for serializer to work
             */
            private val _entries: List<List<Entry?>>
        ) {
            constructor(
                builder: (TicTacToeCell)-> Entry? = { null }
            ) : this(
                _entries = buildEntries(builder)
                    .toListOfLists()
            )

            val entries: Map<TicTacToeCell, Entry?> by
                lazy {
                    _entries
                        .toHashMap()
                        .toMap()
                }

            fun copy(mutator: HashMap<TicTacToeCell, Entry?>.()->Unit): Grid {
                val copied = HashMap(entries)
                copied.mutator()

                return Grid(_entries = copied.toListOfLists())
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
            private fun Map<TicTacToeCell, Entry?>.toListOfLists(): List<List<Entry?>> =
                buildList {
                    for (row in TicTacToeCell.rowRange) {
                        val rowList = buildList {
                            for (column in TicTacToeCell.columnRange) {
                                val cell: TicTacToeCell = row to column
                                add(this@toListOfLists[cell])
                            }
                        }
                        add(rowList)
                    }
                }


            private fun List<List<Entry?>>.toHashMap(): HashMap<TicTacToeCell, Entry?> =
                HashMap<TicTacToeCell, Entry?>()
                    .apply {
                        for (row in TicTacToeCell.rowRange) {
                            for (column in TicTacToeCell.columnRange) {
                                val cell: TicTacToeCell = row to column
                                this[cell] = getEntry(row, column)
                            }
                        }
                    }

            private fun List<List<Entry?>>.getEntry(
                row: Int,
                column: Int,
                lenient: Boolean = true,
            ): Entry? = when(lenient) {
                true -> getOrNull(row)?.getOrNull(column)
                false -> this[row][column]
            }

            fun buildEntries(builder: (TicTacToeCell)-> Entry?): HashMap<TicTacToeCell, Entry?> =
                HashMap<TicTacToeCell, Entry?>()
                    .apply {
                        for (row in TicTacToeCell.rowRange) {
                            for (column in TicTacToeCell.columnRange) {
                                val cell: TicTacToeCell = row to column
                                this[cell] = builder(cell)
                            }
                        }
                    }

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
    0 to 0,
    0 to 1,
    0 to 2
)

private val middleRow = setOf<TicTacToeCell>(
    1 to 0,
    1 to 1,
    1 to 2
)

private val bottomRow = setOf<TicTacToeCell>(
    2 to 0,
    2 to 1,
    2 to 2
)

private val leftColumn = setOf<TicTacToeCell>(
    0 to 0,
    1 to 0,
    2 to 0
)

private val middleColumn = setOf<TicTacToeCell>(
    0 to 1,
    1 to 1,
    2 to 1
)

private val rightColumn = setOf<TicTacToeCell>(
    0 to 2,
    1 to 2,
    2 to 2
)

private val topLeftToBottomRightDiagonal = setOf<TicTacToeCell>(
    0 to 0,
    1 to 1,
    2 to 2
)

private val topRightToBottomLeftDiagonal = setOf<TicTacToeCell>(
    0 to 2,
    1 to 1,
    2 to 0
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


