package dinino.marc.games.userflow.selectgame.ui

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ReceiveChannel

class SelectGameFlowSnackbarController(private val _events: Channel<SnackbarEvent> = Channel()) {
    val events: ReceiveChannel<SnackbarEvent>
        get() = _events

   suspend fun sendSnackbarEvent(event: SnackbarEvent) {
       _events.send(event)
    }

    data class SnackbarEvent(
        val message: String,
        val action: SnackbarAction? = null
    )
    data class SnackbarAction(
        val name: String,
        val action: ()->Unit
    )
}