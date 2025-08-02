package dinino.marc.games.userflow.tetris.ui.screen.game

import dinino.marc.games.userflow.common.data.GamePlayData
import dinino.marc.games.userflow.common.data.Repository
import dinino.marc.games.userflow.common.ui.screen.game.GameViewModel
import dinino.marc.games.userflow.tetris.data.TetrisGameData

class TetrisGameViewModel(
    repository: Repository<TetrisGameData>,
    defaultGameData: ()->TetrisGameData =
        { TetrisGameData() },
    convertDataToState: (gameData: TetrisGameData)-> TetrisGameState =
        ::convertDataToState
): GameViewModel<Unit, TetrisGameData, Unit, TetrisBoardState>(
    repository = repository, defaultGameData = defaultGameData, convertDataToState = convertDataToState
) {

    override fun pause() =
        mutateGameData { mutatePaused(paused = true) }

    override fun unPause() =
        mutateGameData { mutatePaused(paused = false) }

    override fun togglePause() =
        mutateGameData {
            when(paused) {
                true -> mutatePaused(false)
                false -> mutatePaused(true)
                null -> this
            }
        }

    override fun userInitiatedGameOver() =
        mutateGameData { mutateGameOver() }

    companion object {
        private fun convertDataToState(gameData: TetrisGameData): TetrisGameState {
            TODO()
        }
    }

    private val TetrisGameData.paused: Boolean?
        get() = when(playData) {
            is GamePlayData.Normal -> false
            is GamePlayData.Paused -> true
            else -> null
        }

    private fun TetrisGameData.mutatePaused(paused: Boolean) =
        when(paused) {
            true -> copy(playData = GamePlayData.Paused())
            false -> copy(playData = GamePlayData.Normal())
        }

    private fun TetrisGameData.mutateGameOver(details: Unit? = null) =
        copy(playData = GamePlayData.GameOver(details))
}
