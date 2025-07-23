package dinino.marc.games.userflow.common.ui.screen.gameover

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

abstract class GameOverViewModel<out STATE: GameOverState>(
    private val _oneTimeEvents: Channel<GameOverOneTimeEvent> = Channel(),
    initialState: STATE,
    private val _state: MutableStateFlow<STATE> = MutableStateFlow(value = initialState),
): ViewModel() {

    val state: StateFlow<STATE>
        get() = _state.asStateFlow()

    val oneTimeEvents: Flow<GameOverOneTimeEvent>
        get() = _oneTimeEvents.receiveAsFlow()

    fun selectStartNewGame() =
        sendOneTimeEvent(event = GameOverOneTimeEvent.StartNewGameSelected)

    fun selectDifferentGame() =
        sendOneTimeEvent(event = GameOverOneTimeEvent.SelectDifferentGameSelected)

    private fun sendOneTimeEvent(event: GameOverOneTimeEvent) {
        viewModelScope.launch {
            _oneTimeEvents.send(event)
        }
    }
}