package dinino.marc.games.userflow.common.ui.selectneworresumegame

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dinino.marc.games.stateflow.mapStateFlow
import dinino.marc.games.userflow.common.data.Repository
import dinino.marc.games.userflow.common.data.Repository.Companion.hasEntry
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class DefaultSelectNewOrResumeGameViewModel<out GAME: Any, out STATE: SelectNewOrResumeGameState>(
    private val repository: Repository<GAME>,
    private val _oneTimeEvents: Channel<SelectNewOrResumeGameOneTimeEvent> = Channel(),
    private val stateFactory: (isResumeAvailable: Boolean)->STATE,
    private val selectNewGameEventFactory: ()->SelectNewOrResumeGameOneTimeEvent.Navigate,
    private val selectResumeGameEventFactory: ()->SelectNewOrResumeGameOneTimeEvent.Navigate,
) : SelectNewOrResumeGameViewModel<STATE, SelectNewOrResumeGameOneTimeEvent>, ViewModel() {

    override val selectNewOrResumeGameState
        get() = repository.hasEntry
            .mapStateFlow { hasEntry -> stateFactory.invoke(hasEntry) }

        override val oneTimeEvents
            get() = _oneTimeEvents.receiveAsFlow()

        override fun selectNewGame() =
            sendOneTimeEvent(event = selectNewGameEventFactory.invoke())

        override fun selectResumeGame() =
            sendOneTimeEvent(event = selectResumeGameEventFactory.invoke())

        private fun sendOneTimeEvent(event: SelectNewOrResumeGameOneTimeEvent) {
            viewModelScope.launch {
                _oneTimeEvents.send(event)
            }
        }
}