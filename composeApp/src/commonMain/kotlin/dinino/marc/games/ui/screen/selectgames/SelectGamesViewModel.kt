package dinino.marc.games.ui.screen.selectgames

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dinino.marc.games.ui.UiError
import games.composeapp.generated.resources.Res
import games.composeapp.generated.resources.game_tetris_not_available
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.Channel.Factory.CONFLATED
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.getString

class SelectGamesViewModel private constructor(
    private val actionChannel: Channel<Action>,
    private val _errorChannel: Channel<UiError>
) : ViewModel() {

    constructor() : this(
        actionChannel = Channel<Action>(capacity = CONFLATED),
        _errorChannel = Channel<UiError>(capacity = CONFLATED)
    )

    init {
        viewModelScope.launch {
            actionChannel
                .receiveAsFlow()
                .collect { it.process() }
        }
    }

    val errors: ReceiveChannel<UiError>
        get() = _errorChannel

    fun navigateToTicTacToe() {
        actionChannel.trySend(Action.NavigateAction.NavigateToTicTacToeAction)
    }

    fun navigateToTetris() {
        actionChannel.trySend(Action.NavigateAction.NavigateTetrisAction)
    }

    private suspend fun Action.process() {
        when(this) {
            Action.NavigateAction.NavigateTetrisAction ->
                sendUiError(Res.string.game_tetris_not_available)

            Action.NavigateAction.NavigateToTicTacToeAction -> {

            }
        }
    }

    private suspend fun sendUiError(stringResource: StringResource) =
        sendUiError(getString(stringResource))

    private fun sendUiError(localizedMessage: String) {
        _errorChannel.trySend(UiError(localizedMessage))
    }

    private sealed interface Action {
        sealed interface NavigateAction: Action {
            data object NavigateToTicTacToeAction : NavigateAction
            data object NavigateTetrisAction : NavigateAction
        }
    }
}