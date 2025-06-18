package dinino.marc.games.userflow.common.ui

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.material3.FabPosition
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScaffoldDefaults
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.Lifecycle
import dinino.marc.games.app.ui.ObserveOneTimeEventEffect
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow

class SnackbarController(private val _events: Channel<SnackbarEvent> = Channel()) {
    val events: Flow<SnackbarEvent>
        get() = _events.receiveAsFlow()

    suspend fun sendSnackbarEvent(event: SnackbarEvent) =
        _events.send(event)

    data class SnackbarEvent(
        val localizedMessage: String,
        val action: SnackbarAction? = null,
        val onResult: (result: SnackbarResult)->Unit = {}
    ) {
        data class SnackbarAction(val localizedLabel: String)
    }

    companion object {
        @Composable
        fun Flow<SnackbarEvent>.ObserveEffect(
            snackbarHostState: SnackbarHostState,
            minLifecycleState: Lifecycle.State = Lifecycle.State.STARTED
        ) = ObserveOneTimeEventEffect(
            oneTimeEvents = this,
            minLifeCycleState = minLifecycleState
        ) { event ->
            /* dismiss current snackbar */
            snackbarHostState.currentSnackbarData?.dismiss()

            /* show the snackbar and capture what happens */
            val result = snackbarHostState.showSnackbar(
                message = event.localizedMessage,
                withDismissAction = true,
                actionLabel = event.action?.localizedLabel,
                duration = when (event.action) {
                    null -> SnackbarDuration.Short
                    else -> SnackbarDuration.Indefinite
                }
            )

            /* handle result */
            event.onResult(result)
        }
    }
}