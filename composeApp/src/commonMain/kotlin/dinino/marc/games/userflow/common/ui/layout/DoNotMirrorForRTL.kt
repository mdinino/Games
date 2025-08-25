package dinino.marc.games.userflow.common.ui.layout

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.LayoutDirection

/**
 * Composables within this scope will be rendered in LTR,
 * regardless of the devive's layout direction
 */
@Composable
fun DoNotMirrorForRTL(content: @Composable ()->Unit) {
    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
        content()
    }
}