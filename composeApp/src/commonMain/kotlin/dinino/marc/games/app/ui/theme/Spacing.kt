package dinino.marc.games.app.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

val MaterialTheme.spacing: Spacing
    @Composable
    @ReadOnlyComposable
    get() = LocalSpacing.current

val LocalSpacing = compositionLocalOf { Spacing() }

data class Spacing(
    val default: Dp = SpacingDefaults.DEFAULT.dp,
    val tiny: Dp = SpacingDefaults.TINY.dp,
    val extraSmall: Dp = SpacingDefaults.EXTRA_SMALL.dp,
    val small: Dp = SpacingDefaults.SMALL.dp,
    val medium: Dp = SpacingDefaults.MEDIUM.dp,
    val large: Dp = SpacingDefaults.LARGE.dp,
)

private data object SpacingDefaults {
    const val TINY = 2
    const val EXTRA_SMALL = 4
    const val SMALL = 8
    const val MEDIUM = 16
    const val LARGE = 32
    const val DEFAULT = SMALL
}