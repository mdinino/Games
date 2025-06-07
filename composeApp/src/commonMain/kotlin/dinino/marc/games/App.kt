package dinino.marc.games

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import dinino.marc.games.di.Koin
import dinino.marc.games.ui.screen.selectgames.SelectGameScreenRoot
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    Koin.init()
    MaterialTheme {
        SelectGameScreenRoot()
    }
}