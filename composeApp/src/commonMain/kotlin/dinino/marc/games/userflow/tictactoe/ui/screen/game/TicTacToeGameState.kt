package dinino.marc.games.userflow.tictactoe.ui.screen.game

import dinino.marc.games.userflow.common.data.GamePlayData
import dinino.marc.games.userflow.common.ui.screen.game.GameState
import dinino.marc.games.userflow.tictactoe.data.TicTacToeCell
import dinino.marc.games.userflow.tictactoe.data.TicTacToeCell.Companion.to
import dinino.marc.games.userflow.tictactoe.data.TicTacToeGameData
import dinino.marc.games.userflow.tictactoe.ui.screen.game.TicTacToeBoardState.Entry

typealias TicTacToeGameState = GameState<TicTacToeGameOverState, TicTacToeBoardState>

fun TicTacToeGameData.toGameState(): TicTacToeGameState =
    when(playData) {
        is GamePlayData.Normal ->
            TicTacToeNormalState(turn = turn) { cell ->
                boardData.grid.entries[cell]?.toVariant()
            }
        is GamePlayData.Paused ->
            TicTacToePausedState(lastTurn = turn)
        is GamePlayData.GameOver ->
            when(playData.details) {
                is TicTacToeGameData.GameOverDetails.XWins ->
                    TicTacToeGameOverPlayerXWonState(
                        winningCells = playData.details.winningCells,
                        gridBuilder = { cell ->
                            boardData.grid.entries[cell]!!.toVariant()
                        }
                    )
                is TicTacToeGameData.GameOverDetails.OWins ->
                    TicTacToeGameOverPlayerOWonState(
                        winningCells = playData.details.winningCells,
                        gridBuilder = { cell ->
                            boardData.grid.entries[cell]!!.toVariant()
                        }
                    )
                is TicTacToeGameData.GameOverDetails.Draw ->
                    TicTacToeGameOverDrawState(
                        lastTurn = turn,
                        gridBuilder = { cell ->
                            boardData.grid.entries[cell]!!.toVariant()
                        }
                    )
                null -> TicTacToeUserInitiatedGameOverState(
                    lastTurn = turn,
                    gridBuilder = { cell ->
                        boardData.grid.entries[cell]?.toVariant()
                    }
                )

            }
    }

private val TicTacToeGameData.turn: Entry.Variant.Normal
    get() = when(boardData.turn) {
        TicTacToeGameData.BoardData.Entry.PlayerX -> Entry.PlayerXNormal
        TicTacToeGameData.BoardData.Entry.PlayerO -> Entry.PlayerONormal
    }

private fun TicTacToeGameData.BoardData.Entry.toVariant(): Entry.Variant.Normal =
    when(this) {
        TicTacToeGameData.BoardData.Entry.PlayerX -> Entry.PlayerXNormal
        TicTacToeGameData.BoardData.Entry.PlayerO -> Entry.PlayerONormal
    }

@Suppress("FunctionName")
fun TicTacToeNormalState(
    turn: Entry.Variant.Normal,
    gridBuilder: (TicTacToeCell) -> Entry.Variant.Normal?
) = GameState.Normal<TicTacToeGameOverState, TicTacToeBoardState>(
        board = TicTacToeBoardState(
            turn = turn,
            gridBuilder = gridBuilder
        )
)

@Suppress("FunctionName")
fun TicTacToePausedState(lastTurn: Entry.Variant.Normal) =
    GameState.Paused<TicTacToeGameOverState, TicTacToeBoardState>(
        board = TicTacToeBoardState(
            turn = lastTurn,
            gridBuilder = { null }
        )
    )

@Suppress("FunctionName")
fun TicTacToeGameOverDrawState(
    lastTurn: Entry.Variant.Normal,
    gridBuilder: (TicTacToeCell) -> Entry.Variant.Normal
) = GameState.GameOver(
    details = TicTacToeGameOverState.Draw,
    board = TicTacToeBoardState(
        turn = lastTurn,
        gridBuilder = gridBuilder
    )
)

@Suppress("FunctionName")
fun TicTacToeGameOverPlayerXWonState(
    winningCells: Set<TicTacToeCell>,
    gridBuilder: (TicTacToeCell) -> Entry.Variant.Normal
) = GameState.GameOver(
    details = TicTacToeGameOverState.PlayerXWon,
    board = TicTacToeBoardState(
        turn = Entry.PlayerXNormal,
        gridBuilder =  { cell ->
            when(cell in winningCells) {
                true ->  Entry.PlayerXWinner
                false -> gridBuilder(cell)
            }
        }
    )
)


@Suppress("FunctionName")
fun TicTacToeGameOverPlayerOWonState(
    winningCells: Set<TicTacToeCell>,
    gridBuilder: (TicTacToeCell) -> Entry.Variant.Normal
) = GameState.GameOver(
    details = TicTacToeGameOverState.PlayerOWon,
    board = TicTacToeBoardState(
        turn = Entry.PlayerONormal,
        gridBuilder =  { cell ->
            when(cell in winningCells) {
                true ->  Entry.PlayerOWinner
                false -> gridBuilder(cell)
            }
        }
    )
)

@Suppress("FunctionName")
fun TicTacToeUserInitiatedGameOverState(
    lastTurn: Entry.Variant.Normal,
    gridBuilder: (TicTacToeCell) -> Entry.Variant.Normal?
) = GameState.GameOver(
    details = null,
    board = TicTacToeBoardState(
        turn = lastTurn,
        gridBuilder = gridBuilder
    )
)

sealed interface TicTacToeGameOverState {
    data object PlayerXWon: TicTacToeGameOverState
    data object PlayerOWon: TicTacToeGameOverState
    data object Draw: TicTacToeGameOverState
}

data class TicTacToeBoardState private constructor  (
    val turn: Entry.Variant.Normal = Entry.PlayerXNormal,
    val grid: Map<TicTacToeCell, Entry?>
) {
    constructor(
        turn: Entry.Variant.Normal,
        gridBuilder: (TicTacToeCell) -> Entry? = { null }
    ) : this(
        turn = turn,
        grid = buildMap {
            for (row in 0u..<3u) {
                for (column in 0u..<3u) {
                    val cell: TicTacToeCell = row to column
                    this[cell] = gridBuilder(cell)
                }
            }
        }
    )

    sealed interface Entry {
        sealed interface Player: Entry {
            interface X: Player
            interface O: Player
        }

        sealed interface Variant: Entry {
            interface Normal: Variant
            interface Winner: Variant
        }

        data object PlayerXNormal: Player.X, Variant.Normal
        data object PlayerONormal: Player.O, Variant.Normal
        data object PlayerXWinner: Player.X, Variant.Winner
        data object PlayerOWinner: Player.O, Variant.Winner
    }
}






