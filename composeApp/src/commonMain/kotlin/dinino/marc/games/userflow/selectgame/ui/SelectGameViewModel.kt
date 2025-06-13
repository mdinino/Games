package dinino.marc.games.userflow.selectgame.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import games.composeapp.generated.resources.Res
import games.composeapp.generated.resources.game_tictactoe_not_available
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.getString

class SelectGameViewModel(
    private val _oneTimeEvents: Channel<OneTimeEvent> = Channel(),
) : ViewModel() {

    val oneTimeEvents: Flow<OneTimeEvent>
        get() = _oneTimeEvents.receiveAsFlow()

    fun navigateToTicTacToeFlow() =
        sendErrorEvent(EventRes.string.game_tictactoe_not_available)

    fun navigateToTetrisFLow() =
        sendNavigationEvent(OneTimeEvent.Navigate.NavigateToTetrisFlow)

    private fun sendNavigationEvent(event: OneTimeEvent.Navigate) {
        viewModelScope.launch {
            _oneTimeEvents.send(event)
        }
    }

    private suspend fun sendError(localizedMessage: StringResource) =
        sendErrorEvent(
            event = OneTimeEvent.Error(
                localizedMessage = getString(localizedMessage)
            )
        )

    private fun sendErrorEvent(event: OneTimeEvent.Error) {
        viewModelScope.launch {
            _oneTimeEvents.send(event)
        }
    }

    sealed interface OneTimeEvent {
        data class Error(val localizedMessage: String): OneTimeEvent

        sealed interface Navigate: OneTimeEvent {
            data object NavigateToTicTacToeFlow : Navigate
            data object NavigateToTetrisFlow : Navigate
        }
    }
}