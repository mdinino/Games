package dinino.marc.games.userflow.common.ui.nav

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.material3.FabPosition
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScaffoldDefaults
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/*
 * Taken from
 * https://github.com/philipplackner/GlobalSnackbarsCompose/blob/master/app/src/main/java/com/plcoding/globalsnackbarscompose/SnackbarController.kt
 */
@Composable
fun <T> ObserveOneTimeEventEffect(
    oneTimeEvents: Flow<T>,
    minLifeCycleState: Lifecycle.State = Lifecycle.State.STARTED,
    onOneTimeEvent: suspend (T) -> Unit
) {
    val scope = rememberCoroutineScope()
    val lifecycleOwner = LocalLifecycleOwner.current
    LaunchedEffect(oneTimeEvents,lifecycleOwner.lifecycle, onOneTimeEvent) {
        lifecycleOwner.repeatOnLifecycle(state = minLifeCycleState) {
            /* the reason for Dispatchers.main.immediate is discussed
            * at this video/timestamp
            * https://youtu.be/njchj9d_Lf8?t=1272 */
            withContext(Dispatchers.Main.immediate) {
                oneTimeEvents.collect { event ->
                    scope.launch { onOneTimeEvent(event) }
                }
            }
        }
    }
}

@Composable
fun <T> ObserveOneTimeEventLayout(
    modifier: Modifier = Modifier,
    containerColor: Color = MaterialTheme.colorScheme.background,
    contentColor: Color = contentColorFor(containerColor),
    contentWindowInsets: WindowInsets = ScaffoldDefaults.contentWindowInsets,
    oneTimeEvents: Flow<T> = emptyFlow(),
    onOneTimeEvent: suspend (event: T)->Unit,
    content: @Composable (PaddingValues) -> Unit
) {
    Scaffold(
        modifier = modifier,
        containerColor = containerColor,
        contentColor = contentColor,
        contentWindowInsets = contentWindowInsets,
    ) { innerPadding ->
        ObserveOneTimeEventEffect(
            oneTimeEvents = oneTimeEvents,
            onOneTimeEvent = onOneTimeEvent
        )
        content(innerPadding)
    }
}