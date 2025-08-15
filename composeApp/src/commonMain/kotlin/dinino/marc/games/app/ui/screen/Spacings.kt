package dinino.marc.games.app.ui.screen

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class Spacings(
    val default: Dp = SpacingsDefaults.DEFAULT.dp,
    val tiny: Dp = SpacingsDefaults.TINY.dp,
    val extraSmall: Dp = SpacingsDefaults.EXTRA_SMALL.dp,
    val small: Dp = SpacingsDefaults.SMALL.dp,
    val medium: Dp = SpacingsDefaults.MEDIUM.dp,
    val large: Dp = SpacingsDefaults.LARGE.dp,
)

private data object SpacingsDefaults {
    const val TINY = 2
    const val EXTRA_SMALL = 4
    const val SMALL = 8
    const val MEDIUM = 16
    const val LARGE = 32
    const val DEFAULT = SMALL
}