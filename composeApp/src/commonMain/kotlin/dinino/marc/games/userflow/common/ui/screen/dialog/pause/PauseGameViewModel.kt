package dinino.marc.games.userflow.common.ui.screen.dialog.pause

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dinino.marc.games.userflow.common.data.GameData
import dinino.marc.games.userflow.common.data.Repository
import dinino.marc.games.userflow.common.data.Repository.Companion.lastestItem
import kotlinx.coroutines.launch

abstract class PauseGameViewModel<GAME_DATA: GameData<*, *>>(
    private val repository: Repository<GAME_DATA>,
    private val defaultGameData: ()->GAME_DATA,
    private val setGameUnPaused: (gameData: GAME_DATA) -> GAME_DATA,
    private val setUserInitiatedGameOver: (gameData: GAME_DATA) -> GAME_DATA,
): ViewModel() {

    fun resumeGame() =
        mutateGameData { setGameUnPaused(it) }

    fun restartGame() {
        viewModelScope.launch {
            repository.clearEntries()
        }
    }

    fun endGame() =
        mutateGameData { setUserInitiatedGameOver(it) }

    protected fun mutateGameData(mutator: (current: GAME_DATA)->GAME_DATA) {
        viewModelScope.launch {
            repository.upsertLatestItemIfDifferent(
                item =  mutator(repository.lastestItem.value ?: defaultGameData())
            )
        }
    }
}