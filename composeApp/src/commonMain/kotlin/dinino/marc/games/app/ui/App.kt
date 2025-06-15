package dinino.marc.games.app.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import dinino.marc.games.userflow.selectgame.ui.SelectGameScreenRoot
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.KoinContext


@Composable
@Preview
fun App() {
    MaterialTheme {
        KoinContext {
            Scaffold(modifier = Modifier.fillMaxSize()) {
                SelectGameScreenRoot()
            }

        }
    }
}