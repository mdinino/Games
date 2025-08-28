package dinino.marc.games.userflow.tictactoe.ui.screen.game

import dinino.marc.games.userflow.common.data.GamePlayData
import dinino.marc.games.userflow.common.data.Repository
import dinino.marc.games.userflow.common.ui.screen.game.GameState
import dinino.marc.games.userflow.common.ui.screen.game.GameViewModel
import dinino.marc.games.userflow.tictactoe.data.TicTacToeGameData
import dinino.marc.games.userflow.tictactoe.data.TicTacToeGameData.BoardData.Companion.calculateGameOverDetails
import dinino.marc.games.userflow.tictactoe.data.TicTacToeGameData.BoardData.Companion.copy
import dinino.marc.games.userflow.tictactoe.data.TicTacToeGameData.BoardData.Entry
import dinino.marc.games.userflow.tictactoe.data.TicTacToeGameData.BoardData.Grid.Companion.copy
import dinino.marc.games.userflow.tictactoe.data.TicTacToeGameData.BoardData.Grid.Cell.Companion.to
import dinino.marc.games.userflow.tictactoe.di.TicTacToeUserFlowProviders
import org.koin.mp.KoinPlatform

class TicTacToeGameViewModel(
    newGame: Boolean = false,
    repository: Repository<TicTacToeGameData> =
        defaultRepository,
    defaultGameData: ()->TicTacToeGameData =
        { TicTacToeGameData() },
    convertDataToState: (gameData: TicTacToeGameData)->TicTacToeGameState =
        ::convertDataToState
): GameViewModel<TicTacToeGameData.GameOverDetails, TicTacToeGameData,
        TicTacToeGameData.GameOverDetails, TicTacToeGameData.BoardData>(
    newGame = newGame, repository = repository,
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

    fun play(row: UInt, column: UInt) {

    }

    private fun TicTacToeGameData.setMove(row: UInt, column: UInt, entry: Entry) =
        when {
            playData !is GamePlayData.Normal ->
                this
            boardData.grid.entries[row to column] != null ->
                this
            else ->
                copy(boardData = boardData
                    .copy { this[row to column] = entry })
        }



    private fun TicTacToeGameData.setTurn(player: Entry) =
        copy(boardData = boardData.copy(turn = player))

    private fun TicTacToeGameData.setGameOver(details: TicTacToeGameData.GameOverDetails) =
        when(playData) {
            is GamePlayData.GameOver -> this
            is GamePlayData.Paused -> this
            is GamePlayData.Normal -> copy(playData = GamePlayData.GameOver(details))
        }

    private fun Entry.toggle() =
        when(this) {
            Entry.PlayerX -> Entry.PlayerO
            Entry.PlayerO -> Entry.PlayerX
        }

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

private val defaultRepository
    get() = KoinPlatform.getKoin()
        .get<TicTacToeUserFlowProviders>()
        .repositoryProvider
        .provide()
