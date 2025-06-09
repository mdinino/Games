package dinino.marc.games

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import dinino.marc.games.ui.screen.ObserveOneTimeEffect
import dinino.marc.games.ui.screen.GamesSnackbarController
import dinino.marc.games.ui.screen.selectgames.SelectGameScreenRoot
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.launch
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.KoinContext

@Composable
fun App() {
    App(snackbarEvents = GamesSnackbarController.instance.events)
}

@Composable
@Preview
private fun App(
    snackbarEvents: ReceiveChannel<GamesSnackbarController.SnackbarEvent> = Channel()
) {
    MaterialTheme {
        KoinContext {
            val snackbarHostState = remember { SnackbarHostState() }
            val scope = rememberCoroutineScope()

            ObserveOneTimeEffect(oneTimeEffects = snackbarEvents) { snackbarEvent ->
                scope.launch {
                    snackbarHostState.currentSnackbarData?.dismiss()
                    val snackbarResult = snackbarHostState.showSnackbar(
                        message = snackbarEvent.message,
                        actionLabel = snackbarEvent.action?.name,
                        withDismissAction = true
                    )
                    if (snackbarResult == SnackbarResult.ActionPerformed) {
                        snackbarEvent.action?.action()
                    }
                }
            }

            Scaffold(
                snackbarHost = {
                    SnackbarHost(hostState = snackbarHostState)
                }
            ) {
                SelectGameScreenRoot()
            }
        }
    }
}