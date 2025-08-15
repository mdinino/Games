package dinino.marc.games.app.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import dinino.marc.games.app.ui.theme.sizes.LocalSizes
import dinino.marc.games.app.ui.theme.sizes.Sizes

@Composable
fun GamesTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    CompositionLocalProvider(
        LocalSizes provides Sizes(),
    ) {
        MaterialTheme(
            colorScheme = scheme(darkTheme),
            content = content
        )
    }
}

@Composable
private fun scheme(darkTheme: Boolean) =
    when(darkTheme) {
        true -> DarkModeScheme
        else -> LightModeScheme
    }

private val LightModeScheme = lightColorScheme(
    background = BackgroundLightMode,
)

private val DarkModeScheme = darkColorScheme(
    background = BackgroundDarkMode,
)