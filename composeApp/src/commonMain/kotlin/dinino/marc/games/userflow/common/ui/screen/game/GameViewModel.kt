package dinino.marc.games.userflow.common.ui.screen.game

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dinino.marc.games.stateflow.mapStateFlow
import dinino.marc.games.userflow.common.data.GameData
import dinino.marc.games.userflow.common.data.Repository
import dinino.marc.games.userflow.common.domain.GameUseCases
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

abstract class GameViewModel<
        GAME_OVER_DATA_DETAILS: Any,
        GAME_DATA: GameData<GAME_OVER_DATA_DETAILS, *>,
        GAME_OVER_STATE_DETAILS: Any,
        out BOARD_STATE: Any>(
    newGame: Boolean = false,
    private val _oneTimeEvents: Channel<GameOneTimeEvent<GAME_OVER_STATE_DETAILS>> = Channel(),
    private val useCases: GameUseCases<Repository<GAME_DATA>, GAME_DATA>,
    private val defaultGameData: ()->GAME_DATA,
    private val convertDataToState: (gameData: GAME_DATA)->GameState<GAME_OVER_STATE_DETAILS, BOARD_STATE>
): ViewModel() {

    val gameState: StateFlow<GameState<GAME_OVER_STATE_DETAILS, BOARD_STATE>> by lazy {
        if (newGame) resetToNewGame()
        useCases.getLatestStatus()
            .mapStateFlow { convertDataToState(it ?: defaultGameData()) }
    }

    val oneTimeEvent: Flow<GameOneTimeEvent<GAME_OVER_STATE_DETAILS>>
        get() = _oneTimeEvents.receiveAsFlow()

    fun navigateToGameOverScreen(
        gameOverDetails: GAME_OVER_STATE_DETAILS? = null,
        clearGame : Boolean = true
    ) {
        viewModelScope.launch {
            if (clearGame) useCases.clearEntries()
            _oneTimeEvents.send(element =
                GameOneTimeEvent.NavigateToGameOverScreen(
                    gameOverDetails = gameOverDetails
                )
            )
        }
    }

    protected fun mutateGameData(mutator: GAME_DATA.()->GAME_DATA) {
        val mutated = (useCases.getLatestStatus().value ?: defaultGameData()).mutator()
        viewModelScope.launch {
            useCases.upsertLatestItemIfDifferent(mutated)
        }
    }

    fun resetToNewGame() {
        viewModelScope.launch {
            useCases.clearEntries()
        }
    }

    abstract fun pause()
    abstract fun unPause()
    abstract fun togglePause()
    abstract fun userInitiatedGameOver()
}