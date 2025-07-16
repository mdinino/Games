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

abstract class GameOverViewModel<
        out STATE: GameOverState,
        out ONE_TIME_EVENT: GameOverOneTimeEvent
>(
    private val _oneTimeEvents: Channel<ONE_TIME_EVENT> = Channel(),
    initialState: STATE,
    private val _state: MutableStateFlow<STATE> = MutableStateFlow(value = initialState),
    private val navigateToNewGameEventFactory: ()->ONE_TIME_EVENT,
    private val navigateToDifferentGameEventFactory: ()->ONE_TIME_EVENT
): ViewModel() {

    val state: StateFlow<STATE>
        get() = _state.asStateFlow()

    val oneTimeEvents: Flow<ONE_TIME_EVENT>
        get() = _oneTimeEvents.receiveAsFlow()

    fun selectNewGame() =
        sendOneTimeEvent(event = navigateToNewGameEventFactory.invoke())

    fun selectDifferentGame() =
        sendOneTimeEvent(event = navigateToDifferentGameEventFactory.invoke())

    private fun sendOneTimeEvent(event: ONE_TIME_EVENT) {
        viewModelScope.launch {
            _oneTimeEvents.send(event)
        }
    }
}