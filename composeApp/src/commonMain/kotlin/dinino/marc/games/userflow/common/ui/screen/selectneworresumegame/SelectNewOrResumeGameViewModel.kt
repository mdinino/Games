package dinino.marc.games.userflow.common.ui.screen.selectneworresumegame

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dinino.marc.games.stateflow.mapStateFlow
import dinino.marc.games.userflow.common.data.Repository
import dinino.marc.games.userflow.common.data.Repository.Companion.hasEntry
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

abstract class SelectNewOrResumeGameViewModel<
        out GAME: Any,
        out STATE: SelectNewOrResumeGameState,
        out ONE_TIME_EVENT: SelectNewOrResumeGameOneTimeEvent
>(
    private val repository: Repository<GAME>,
    private val _oneTimeEvents: Channel<ONE_TIME_EVENT> = Channel(),
    private val stateFactory: (isResumeAvailable: Boolean)->STATE,
    private val navigateToNewGameEventFactory: ()->ONE_TIME_EVENT,
    private val navigateToResumeGameEventFactory: ()->ONE_TIME_EVENT
): ViewModel() {

    val selectNewOrResumeGameState: StateFlow<STATE>
        get() = repository.hasEntry
            .mapStateFlow { hasEntry -> stateFactory.invoke(hasEntry) }

    val oneTimeEvents: Flow<ONE_TIME_EVENT>
        get() = _oneTimeEvents.receiveAsFlow()

    fun selectNewGame() =
        sendOneTimeEvent(event = navigateToNewGameEventFactory.invoke())

    fun selectResumeGame() =
        sendOneTimeEvent(event = navigateToResumeGameEventFactory.invoke())

    private fun sendOneTimeEvent(event: ONE_TIME_EVENT) {
        viewModelScope.launch {
            _oneTimeEvents.send(event)
        }
    }
}