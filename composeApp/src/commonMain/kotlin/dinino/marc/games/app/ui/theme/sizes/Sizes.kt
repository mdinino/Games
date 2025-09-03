package dinino.marc.games.app.ui.theme.sizes

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.compositionLocalOf

val MaterialTheme.sizes: Sizes
    @Composable
    @ReadOnlyComposable
    get() = LocalSizes.current

val LocalSizes = compositionLocalOf { Sizes() }

data class Sizes(
    val buttons: ButtonSizes = ButtonSizes(),
    val icons: IconSizes = IconSizes(),
    val paddings: Paddings = Paddings(),
    val spacings: Spacings = Spacings(),
    val ticTacToeCellBorder: TicTacToeCellBorder = TicTacToeCellBorder()
)