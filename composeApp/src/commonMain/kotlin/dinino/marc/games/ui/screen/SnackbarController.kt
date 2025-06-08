package dinino.marc.games.ui.screen

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.Channel.Factory.UNLIMITED
import kotlinx.coroutines.flow.receiveAsFlow

class SnackbarController private constructor(
    private val _events: Channel<SnackbarEvent> = Channel(capacity = UNLIMITED)
) {
    val events = _events.receiveAsFlow()

   fun sendEvent(event: SnackbarEvent) {
        _events.trySend(event)
    }

    data class SnackbarEvent(
        val message: String,
        val action: SnackbarAction? = null
    )
    data class SnackbarAction(
        val name: String,
        val action: ()->Unit
    )

    companion object {
        val instance = SnackbarController()
    }
}