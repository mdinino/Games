package dinino.marc.games.ui.screen.selectgames

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import games.composeapp.generated.resources.Res
import games.composeapp.generated.resources.game_tetris_not_available
import games.composeapp.generated.resources.game_tictactoe_not_available
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.getString

class SelectGameViewModel(
    private val navigateChannel: Channel<Action.Navigate> = Channel(),
    private val _errors: Channel<SideEffect.Error> = Channel()
) : ViewModel() {

    init {
        viewModelScope.launch {
            navigateChannel
                .receiveAsFlow()
                .collect { it.process() }
        }
    }

    val errors = _errors.receiveAsFlow()

    fun navigate(to: Action.Navigate) {
        viewModelScope.launch {
            navigateChannel.send(to)
        }
    }

    private suspend fun Action.process() =
        when(this) {
            Action.Navigate.NavigateToTetris ->
                notifyError(Res.string.game_tetris_not_available)

            Action.Navigate.NavigateToTicTacToe ->
                notifyError(Res.string.game_tictactoe_not_available)
        }

    private suspend fun notifyError(stringResource: StringResource) =
        notifyError(getString(stringResource))

    private fun notifyError(localizedMessage: String) {
        viewModelScope.launch {
            _errors.send(SideEffect.Error(localizedMessage))
        }
    }

    sealed interface Action {
        sealed interface Navigate: Action {
            data object NavigateToTicTacToe : Navigate
            data object NavigateToTetris : Navigate
        }
    }
}