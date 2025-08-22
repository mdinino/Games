package dinino.marc.games.app.ui.theme.sizes

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class Paddings(
    val default: Dp = PaddingsDefaults.DEFAULT.dp,
    val tiny: Dp = PaddingsDefaults.TINY.dp,
    val extraSmall: Dp = PaddingsDefaults.EXTRA_SMALL.dp,
    val small: Dp = PaddingsDefaults.SMALL.dp,
    val medium: Dp = PaddingsDefaults.MEDIUM.dp,
    val large: Dp = PaddingsDefaults.LARGE.dp,
)

private data object PaddingsDefaults {
    const val TINY = 2
    const val EXTRA_SMALL = 4
    const val SMALL = 8
    const val MEDIUM = 16
    const val LARGE = 32
    const val DEFAULT = SMALL
}