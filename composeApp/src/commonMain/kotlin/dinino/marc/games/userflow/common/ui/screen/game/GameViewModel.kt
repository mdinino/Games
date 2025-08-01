package dinino.marc.games.userflow.common.ui.screen.game

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dinino.marc.games.stateflow.mapStateFlow
import dinino.marc.games.userflow.common.data.GameData
import dinino.marc.games.userflow.common.data.Repository
import dinino.marc.games.userflow.common.data.Repository.Companion.lastestItem
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

abstract class GameViewModel<
        GAME_OVER_DATA_DETAILS: Any,
        BOARD_DATA: Any,
        GAME_DATA: GameData<GAME_OVER_DATA_DETAILS, BOARD_DATA>,
        out GAME_OVER_STATE_DETAILS: Any,
        out BOARD_STATE: Any>(
    newGame: Boolean,
    private val _oneTimeEvents: Channel<GameOneTimeEvent> = Channel(),
    private val repository: Repository<GAME_DATA>,
    private val defaultGameData: ()->GAME_DATA,
    private val convertDataToState: (gameData: GAME_DATA)->GameState<GAME_OVER_STATE_DETAILS, BOARD_STATE>
): ViewModel() {

    init {
        if (newGame) {
            viewModelScope.launch {
                repository.upsertLatestItemIfDifferent(defaultGameData())
            }
        }
    }

    val gameState: StateFlow<GameState<GAME_OVER_STATE_DETAILS, BOARD_STATE>> =
        repository.lastestItem
            .mapStateFlow { convertDataToState(it ?: defaultGameData()) }

    val oneTimeEvent: Flow<GameOneTimeEvent>
        get() = _oneTimeEvents.receiveAsFlow()

    fun navigateToGameOverScreen() =
        sendOneTimeEvent(GameOneTimeEvent.NavigateToGameOverScreen)

    protected fun mutateGameData(mutator: GAME_DATA.()->GAME_DATA) {
        val mutated = (repository.lastestItem.value ?: defaultGameData()).mutator()
        viewModelScope.launch {
            repository.upsertLatestItemIfDifferent(mutated)
        }
    }

    private fun sendOneTimeEvent(event: GameOneTimeEvent) {
        viewModelScope.launch {
            _oneTimeEvents.send(event)
        }
    }

    abstract fun pause()
    abstract fun unPause()
    abstract fun togglePause()
    abstract fun userInitiatedGameOver()

}