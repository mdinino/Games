package dinino.marc.games.ui.screen.selectgames

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import games.composeapp.generated.resources.Res
import games.composeapp.generated.resources.game_tetris_not_available
import games.composeapp.generated.resources.game_tictactoe_not_available
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.Channel.Factory.CONFLATED
import kotlinx.coroutines.channels.Channel.Factory.UNLIMITED
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.getString

class SelectGameViewModel(
    private val actionChannel: Channel<Action> = Channel(capacity = CONFLATED),
    private val sideEffectChannel: Channel<SideEffect> = Channel(capacity = UNLIMITED)
) : ViewModel() {

    init {
        viewModelScope.launch {
            actionChannel
                .receiveAsFlow()
                .collect { it.process() }
        }
    }

    val sideEffectFlow: Flow<SideEffect>
        get() = sideEffectChannel.receiveAsFlow()

    fun navigate(to: Action.Navigate) {
        viewModelScope.launch {
            actionChannel.send(to)
        }
    }

    private suspend fun Action.process() =
        when(this) {
            Action.Navigate.NavigateToTetris ->
                queueToast(Res.string.game_tetris_not_available)

            Action.Navigate.NavigateToTicTacToe ->
                queueToast(Res.string.game_tictactoe_not_available)
        }

    private suspend fun queueToast(stringResource: StringResource) =
        queueToast(getString(stringResource))

    private fun queueToast(localizedMessage: String) {
        viewModelScope.launch {
            sideEffectChannel.send(SideEffect.ShowSnackbar(localizedMessage))
        }
    }

    sealed interface Action {
        sealed interface Navigate: Action {
            data object NavigateToTicTacToe : Navigate
            data object NavigateToTetris : Navigate
        }
    }
}