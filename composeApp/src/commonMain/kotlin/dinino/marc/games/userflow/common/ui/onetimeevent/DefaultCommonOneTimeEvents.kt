package dinino.marc.games.userflow.common.ui.onetimeevent

import dinino.marc.games.userflow.common.ui.SnackbarController
import dinino.marc.games.userflow.common.ui.SnackbarController.SnackbarEvent.SnackbarAction
import dinino.marc.games.userflow.common.ui.route.SerializableUserFlowRoute
import kotlin.String
import games.composeapp.generated.resources.Res
import games.composeapp.generated.resources.ok
import org.jetbrains.compose.resources.getString

data class DefaultNavigateOneTimeEvent(
    override val route: SerializableUserFlowRoute
): CommonOneTimeEvents.NavigateOneTimeEvent

data class DefaultNotifyOneTimeEvent(
    override val localizedMessage: suspend ()->String,
    override val action: (()->Unit)?
): CommonOneTimeEvents.NotifyOneTimeEvent

suspend fun CommonOneTimeEvents.NotifyOneTimeEvent.asSnackbarEvent() {
    val snackbarMessage: String  = localizedMessage.invoke()
    when(val action = this.action) {
        null -> SnackbarController.SnackbarEvent(
            localizedMessage = snackbarMessage
        )
        else -> SnackbarController.SnackbarEvent(
            localizedMessage = snackbarMessage,
            action = SnackbarAction(
                localizedLabel = getString(Res.string.ok)
            ),
            onResult = { action.invoke() }
        )
    }
}