package dinino.marc.games.userflow.common.ui.screen.selectneworresumegame

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class DefaultSelectNewOrResumeGameViewModel<
        out STATE: SelectNewOrResumeGameState,
        out ONE_TIME_EVENT: SelectNewOrResumeGameOneTimeEvent>(
            private val initialState: STATE,
            private val selectNewGameEventFactory: ()->ONE_TIME_EVENT,
            private val selectResumeGameEventFactory: ()->ONE_TIME_EVENT,
            private val errorEventFactory: (localizedMessage: String)->ONE_TIME_EVENT,
            private val _selectNewOrResumeGameState: Flow<STATE> = MutableStateFlow(value = initialState),
            private val _oneTimeEvents: Channel<ONE_TIME_EVENT> = Channel()
) : SelectNewOrResumeGameViewModel<STATE, ONE_TIME_EVENT>, ViewModel() {

    override val selectNewOrResumeGameState
        get() = _selectNewOrResumeGameState

    override val oneTimeEvents
        get() = _oneTimeEvents.receiveAsFlow()

    override fun selectNewGame() =
        sendOneTimeEvent(event = selectNewGameEventFactory())

    override fun selectResumeGame() =
        sendOneTimeEvent(event = selectResumeGameEventFactory())

    private fun sendOneTimeEvent(event: ONE_TIME_EVENT) {
        viewModelScope.launch {
            _oneTimeEvents.send(event)
        }
    }
}