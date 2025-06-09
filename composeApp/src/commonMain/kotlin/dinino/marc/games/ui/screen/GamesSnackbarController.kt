package dinino.marc.games.ui.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.Channel.Factory.UNLIMITED
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.launch

/**
 * Snackbar controller for the games app flow and all of its subflows.
 * Events posted here are tied to the lifecycle of the entire app and.
 */
class GamesSnackbarController private constructor(
    private val _events: Channel<SnackbarEvent> = Channel(capacity = UNLIMITED)
) {
    @get:Composable
    val events: ReceiveChannel<SnackbarEvent>
        get() = _events

    @Composable
   /**
    * Events posted here are tied to the lifecycle of the entire app and all of its subflows.
   */
   fun SendSnackbarEvent(event: SnackbarEvent) {
        rememberCoroutineScope().launch {
            _events.send(event)
        }
    }

    data class SnackbarEvent(
        val message: String,
        val action: SnackbarAction? = null
    )
    data class SnackbarAction(
        val name: String,
        val action: ()->Unit
    )

    companion object Companion {
        val instance = GamesSnackbarController()
    }
}