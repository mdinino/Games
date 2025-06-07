package dinino.marc.games

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import dinino.marc.games.ui.screen.selectgames.SelectGameScreen
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    MaterialTheme {
        SelectGameScreen()
    }
}