package dinino.marc.games.userflow.tictactoe.ui.screen.game

import dinino.marc.games.userflow.common.data.GamePlayData
import dinino.marc.games.userflow.common.data.Repository
import dinino.marc.games.userflow.common.domain.RepositoryUseCases
import dinino.marc.games.userflow.common.ui.screen.game.GameViewModel
import dinino.marc.games.userflow.tictactoe.data.TicTacToeGameData
import dinino.marc.games.userflow.tictactoe.data.TicTacToeCell.Companion.to
import dinino.marc.games.userflow.tictactoe.data.TicTacToeGameData.BoardData.Companion.calculateGameOverDetails
import dinino.marc.games.userflow.tictactoe.data.TicTacToeGameData.BoardData.Entry
import dinino.marc.games.userflow.tictactoe.di.TicTacToeUserFlowProviders
import org.koin.mp.KoinPlatform

class TicTacToeGameViewModel(
    newGame: Boolean = false,
    useCases: RepositoryUseCases<Repository<TicTacToeGameData>, TicTacToeGameData> =
        defaultUseCases,
    defaultGameData: ()->TicTacToeGameData =
        { TicTacToeGameData() },
    convertDataToState: (gameData: TicTacToeGameData)->TicTacToeGameState =
        { it.toGameState() }
): GameViewModel<TicTacToeGameData.GameOverDetails, TicTacToeGameData,
        TicTacToeGameOverState, TicTacToeBoardState>(
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

    fun play(row: Int, column: Int) =
        mutateGameData {
            when(playData) {
                is GamePlayData.GameOver ->
                    this
                is GamePlayData.Paused ->
                    this
                is GamePlayData.Normal ->
                    setMove(row, column, boardData.turn)
                        .run {
                            val gameOverDetails = calculateGameOverDetails(boardData.turn)
                            when(gameOverDetails) {
                                null -> setTurn(
                                    player = when(boardData.turn) {
                                        Entry.PlayerX -> Entry.PlayerO
                                        Entry.PlayerO -> Entry.PlayerX
                                    }
                                )
                                else -> setGameOver(gameOverDetails)
                            }
                        }
            }

        }

    private fun TicTacToeGameData.setMove(row: Int, column: Int, player: Entry) =
        when {
            playData !is GamePlayData.Normal ->
                this
            boardData.grid.entries[row to column] != null ->
                this
            else ->
                copy(boardData =
                    boardData.copy {
                        this[row to column] = player
                    }
                )
        }

    private fun TicTacToeGameData.setTurn(player: Entry) =
        copy(boardData = boardData.copy(turn = player))

    private fun TicTacToeGameData.setGameOver(details: TicTacToeGameData.GameOverDetails) =
        when(playData) {
            is GamePlayData.GameOver -> this
            is GamePlayData.Paused -> this
            is GamePlayData.Normal -> copy(playData = GamePlayData.GameOver(details))
        }

    private fun TicTacToeGameData.calculateGameOverDetails(player: Entry) =
        this.boardData.calculateGameOverDetails(player)


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

private val defaultUseCases
    get() = KoinPlatform.getKoin()
        .get<TicTacToeUserFlowProviders>()
        .useCasesProvider
        .provide()
