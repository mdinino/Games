package dinino.marc.games.userflow.common.ui.screen.selectneworresumegame

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dinino.marc.games.stateflow.mapStateFlow
import dinino.marc.games.userflow.common.domain.GameUseCases
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

abstract class SelectNewOrResumeGameViewModel<out STATE: SelectNewOrResumeGameState>(
    private val useCases: GameUseCases<*, *>,
    private val _oneTimeEvents: Channel<SelectNewOrResumeGameOneTimeEvent> = Channel(),
    private val stateFactory: (isResumeAvailable: Boolean)->STATE,
): ViewModel() {

    val selectNewOrResumeGameState: StateFlow<STATE>
        get() = useCases.hasEntry()
            .mapStateFlow { hasEntry -> stateFactory.invoke(hasEntry) }

    val oneTimeEvents: Flow<SelectNewOrResumeGameOneTimeEvent>
        get() = _oneTimeEvents.receiveAsFlow()

    fun selectNewGame() {
        viewModelScope.launch {
            _oneTimeEvents.send(SelectNewOrResumeGameOneTimeEvent.NewGameSelected)
        }
    }

    fun selectResumeGame() {
        viewModelScope.launch {
            _oneTimeEvents.send(SelectNewOrResumeGameOneTimeEvent.ResumeGameSelected)
        }
    }
}