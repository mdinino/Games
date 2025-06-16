package dinino.marc.games.app.ui

import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.Composable
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
        val action: SnackbarAction? = null
    )
    data class SnackbarAction(
        val localizedName: String,
        val onAction: ()->Unit
    )

    companion object {
        @Composable
        fun Flow<SnackbarEvent>.ObserveEffect(snackbarHostState: SnackbarHostState) =
            ObserveOneTimeEventEffect(this) { event ->
                /* dismiss current snackbar */
                snackbarHostState.currentSnackbarData?.dismiss()

                /* show the snackbar and capture what happens */
                val result = snackbarHostState.showSnackbar(
                    message = event.localizedMessage,
                    withDismissAction = true,
                    actionLabel = event.action?.localizedName,
                    duration = when(event.action) {
                        null -> SnackbarDuration.Short
                        else -> SnackbarDuration.Indefinite
                    }
                )

                /* if an action was performed, invoke the lambda */
                if(result == SnackbarResult.ActionPerformed) {
                    event.action?.onAction?.invoke()
                }
            }
    }
}