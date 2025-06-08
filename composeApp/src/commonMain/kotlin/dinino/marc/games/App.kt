package dinino.marc.games

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import dinino.marc.games.di.Koin
import dinino.marc.games.ui.screen.ObserveEffect
import dinino.marc.games.ui.screen.SnackbarController
import dinino.marc.games.ui.screen.selectgames.SelectGameScreenRoot
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.launch
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun App() {
    Koin.init()
    App(snackbarEvents = SnackbarController.instance.events)
}

@Composable
@Preview
private fun App(
    snackbarEvents: Flow<SnackbarController.SnackbarEvent> = emptyFlow()
) {
    MaterialTheme {
        val snackbarHostState = remember { SnackbarHostState() }
        val scope = rememberCoroutineScope()

        ObserveEffect(flow = snackbarEvents) { snackbarEvent ->
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