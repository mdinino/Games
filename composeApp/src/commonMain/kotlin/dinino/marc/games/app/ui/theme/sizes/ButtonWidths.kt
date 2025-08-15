package dinino.marc.games.app.ui.theme.sizes

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class ButtonWidths(
    val default: Dp = ButtonWidthDefaults.DEFAULT.dp,
    val medium: Dp = ButtonWidthDefaults.MEDIUM.dp,
)

private data object ButtonWidthDefaults {
    const val MEDIUM = 175
    const val DEFAULT = MEDIUM
}