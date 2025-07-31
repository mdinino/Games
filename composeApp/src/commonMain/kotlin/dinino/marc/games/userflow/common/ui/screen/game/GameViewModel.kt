package dinino.marc.games.userflow.common.ui.screen.game

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dinino.marc.games.userflow.common.data.GameData
import dinino.marc.games.userflow.common.data.Repository
import dinino.marc.games.userflow.common.data.Repository.Companion.lastestItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

abstract class GameViewModel<
    out GAME_OVER_DATA_DETAILS: Any,
    out BOARD_DATA: Any,
    out GAME_DATA: GameData<GAME_OVER_DATA_DETAILS, BOARD_DATA>,
    out GAME_OVER_STATE_DETAILS: Any,
    out BOARD_STATE: Any,
    out GAME_STATE: GameState<GAME_OVER_STATE_DETAILS, BOARD_STATE>
>(
    private val repository: Repository<GAME_DATA>,
    private val defaultGameData: ()->GAME_DATA,
    private val convertDataToState: (gameData: GAME_DATA)->GAME_STATE,
    private val _gameState: MutableStateFlow<GAME_STATE>
): ViewModel() {
    val gameState: StateFlow<GAME_STATE>
        get() = _gameState.asStateFlow()

    fun pause() =
        repository.mutate {
        }



    private fun Repository<GAME_DATA>.mutate(
        default: ()->GAME_DATA = defaultGameData,
        mutator: (current: GAME_DATA)->GAME_DATA,
    ) {

    } lastestItem.value ?: default.invoke()
        .apply {
            mutator(GAME_DATA)
            updateIfDifferent(gameData = this)
        }

    private fun Repository<GAME_DATA>.updateIfDifferent(gameData: GAME_DATA) {
        viewModelScope.launch {
            if (lastestItem.value == gameData) return@launch
            upsertLatestItemIfDifferent(gameData)
            _gameState.value = convertDataToState.invoke(gameData)
        }
    }
}