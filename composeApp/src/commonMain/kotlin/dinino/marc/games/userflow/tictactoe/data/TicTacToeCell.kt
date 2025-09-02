package dinino.marc.games.userflow.tictactoe.data

import kotlinx.serialization.Serializable

@Serializable
data class TicTacToeCell(val row: Int, val column: Int) {
    init {
        requireValidCell(row = row, column = column)
    }

    companion object Companion {
        val rowRange = 0..< 3
        val columnRange = 0..< 3

        fun requireValidCell(row: Int, column: Int) =
            require(row in rowRange && column in columnRange) {
                "Row and column must be between 0 and 2 (inclusively)"
            }


        infix fun Int.to(column: Int) =
            TicTacToeCell(row = this,  column = column)
    }
}
