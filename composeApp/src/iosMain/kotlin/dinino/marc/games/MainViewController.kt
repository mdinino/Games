package dinino.marc.games

import androidx.compose.ui.window.ComposeUIViewController
import dinino.marc.games.app.di.initKoin
import dinino.marc.games.app.ui.screen.App

fun MainViewController() = ComposeUIViewController(
    configure = {
        initKoin()
    }
) {
    App()
}