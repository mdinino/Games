package dinino.marc.games.app.ui.theme.sizes

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class TicTacToeCellBorder(
    val default: Dp = TicTacToeGridBorderDefaults.DEFAULT.dp,
    val extraTiny: Dp = TicTacToeGridBorderDefaults.EXTRA_TINY.dp,
    val tiny: Dp = TicTacToeGridBorderDefaults.TINY.dp,
    val extraSmall: Dp = TicTacToeGridBorderDefaults.EXTRA_SMALL.dp,
    val small: Dp = TicTacToeGridBorderDefaults.SMALL.dp,
    val medium: Dp = TicTacToeGridBorderDefaults.MEDIUM.dp,
    val large: Dp = TicTacToeGridBorderDefaults.LARGE.dp
)

private data object TicTacToeGridBorderDefaults {

    const val EXTRA_TINY = 1
    const val TINY = 2
    const val EXTRA_SMALL = 4
    const val SMALL = 8
    const val MEDIUM = 16
    const val LARGE = 32
    const val DEFAULT = EXTRA_TINY
}