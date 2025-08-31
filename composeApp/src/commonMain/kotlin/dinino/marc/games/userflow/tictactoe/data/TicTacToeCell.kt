package dinino.marc.games.userflow.tictactoe.data

import kotlinx.serialization.Serializable

@Serializable
data class TicTacToeCell(val row: UInt, val column: UInt) {
    init {
        require(row in 0u..2u && column in 0u..2u) {
            "Row and column must be between 0 and 2"
        }
    }

    companion object Companion {
        const val ROW_COUNT = 3u
        const val COLUMN_COUNT = 3u

        infix fun UInt.to(column: UInt) =
            TicTacToeCell(row = this,  column = column)
    }
}
