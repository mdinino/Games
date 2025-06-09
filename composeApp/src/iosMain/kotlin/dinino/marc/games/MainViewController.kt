package dinino.marc.games

import androidx.compose.ui.window.ComposeUIViewController
import dinino.marc.games.di.initKoin

fun MainViewController() = ComposeUIViewController(
    configure = {
        initKoin()
    }
) {
    App()
}