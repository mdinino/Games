package dinino.marc.games

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import dinino.marc.games.app.di.initKoin
import dinino.marc.games.app.ui.App
import games.composeapp.generated.resources.Res
import games.composeapp.generated.resources.app_name
import org.jetbrains.compose.resources.stringResource

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = stringResource(Res.string.app_name),
    ) {
        initKoin()
        App()
    }
}