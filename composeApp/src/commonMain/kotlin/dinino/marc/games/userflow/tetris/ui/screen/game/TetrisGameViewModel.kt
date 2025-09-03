package dinino.marc.games.userflow.tetris.ui.screen.game

import dinino.marc.games.userflow.common.data.GamePlayData
import dinino.marc.games.userflow.common.data.Repository
import dinino.marc.games.userflow.common.domain.GameUseCases
import dinino.marc.games.userflow.common.ui.screen.game.GameState
import dinino.marc.games.userflow.common.ui.screen.game.GameViewModel
import dinino.marc.games.userflow.tetris.data.TetrisGameData
import dinino.marc.games.userflow.tetris.di.TetrisUserFlowProviders
import org.koin.mp.KoinPlatform

class TetrisGameViewModel(
    newGame: Boolean = false,
    useCases: GameUseCases<Repository<TetrisGameData>, TetrisGameData> =
        defaultUseCases,
    defaultGameData: ()->TetrisGameData =
        { TetrisGameData() },
    convertDataToState: (gameData: TetrisGameData)-> TetrisGameState =
        ::convertDataToState
): GameViewModel<Unit, TetrisGameData, Unit, TetrisBoardState>(
    newGame = newGame, useCases = useCases,
    defaultGameData = defaultGameData, convertDataToState = convertDataToState
) {

    override fun pause() =
        mutateGameData { mutatePaused(paused = true) }

    override fun unPause() =
        mutateGameData { mutatePaused(paused = false) }

    override fun togglePause() =
        mutateGameData {
            when(paused) {
                true -> mutatePaused(paused = false)
                false -> mutatePaused(paused = true)
                null -> this
            }
        }

    override fun userInitiatedGameOver() =
        mutateGameData { mutateGameOver() }

    companion object {
        private fun convertDataToState(gameData: TetrisGameData): TetrisGameState =
            when(gameData.playData) {
                is GamePlayData.Normal ->
                    GameState.Normal(defaultTetrisBoardState)
                is GamePlayData.Paused -> GameState.Paused(hiddenTetrisBoardState)
                is GamePlayData.GameOver<Unit> -> GameState.GameOver(
                    details = gameData.playData.details,
                    board = defaultTetrisBoardState
                )
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

private val defaultUseCases
    get() = KoinPlatform.getKoin()
        .get<TetrisUserFlowProviders>()
        .useCasesProvider
        .provide()
