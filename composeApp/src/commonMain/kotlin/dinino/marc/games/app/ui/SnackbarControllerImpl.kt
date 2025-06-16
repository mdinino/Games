package dinino.marc.games.app.ui

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow

internal class SnackbarControllerImpl(
    private val _events: Channel<SnackbarController.SnackbarEvent> = Channel()
): SnackbarController {
    override val events
        get() = _events.receiveAsFlow()

    override suspend fun sendSnackbarEvent(event: SnackbarController.SnackbarEvent) {
        _events.send(event)
    }
}