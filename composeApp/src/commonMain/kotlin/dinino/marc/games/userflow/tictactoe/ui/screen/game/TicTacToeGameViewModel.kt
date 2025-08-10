package dinino.marc.games.userflow.tictactoe.ui.screen.game

import dinino.marc.games.userflow.common.data.GamePlayData
import dinino.marc.games.userflow.common.data.Repository
import dinino.marc.games.userflow.common.ui.screen.game.GameState
import dinino.marc.games.userflow.common.ui.screen.game.GameViewModel
import dinino.marc.games.userflow.tictactoe.data.TicTacToeGameData

class TicTacToeGameViewModel(
    repository: Repository<TicTacToeGameData>,
    defaultGameData: ()->TicTacToeGameData =
        { TicTacToeGameData() },
    convertDataToState: (gameData: TicTacToeGameData)->TicTacToeGameState =
        ::convertDataToState
): GameViewModel<TicTacToeGameData.GameOverDetails, TicTacToeGameData,
        TicTacToeGameData.GameOverDetails, TicTacToeBoardState>(
    repository = repository, defaultGameData = defaultGameData, convertDataToState = convertDataToState) {

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
        private fun convertDataToState(gameData: TicTacToeGameData): TicTacToeGameState =
            when(gameData.playData) {
                is GamePlayData.Normal ->
                    GameState.Normal(defaultTicTacToeBoardState)
                is GamePlayData.Paused -> GameState.Paused(hiddenTicTacToeBoardState)
                is GamePlayData.GameOver<TicTacToeGameData.GameOverDetails> -> GameState.GameOver(
                    details = gameData.playData.details,
                    board = defaultTicTacToeBoardState
                )
            }
    }

    private val TicTacToeGameData.paused: Boolean?
        get() = when(playData) {
            is GamePlayData.Normal -> false
            is GamePlayData.Paused -> true
            else -> null
        }

    private fun TicTacToeGameData.mutatePaused(paused: Boolean) =
        when(paused) {
            true -> copy(playData = GamePlayData.Paused())
            false -> copy(playData = GamePlayData.Normal())
        }

    private fun TicTacToeGameData.mutateGameOver(details: TicTacToeGameData.GameOverDetails? = null) =
        copy(playData = GamePlayData.GameOver(details))
}
