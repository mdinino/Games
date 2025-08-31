package dinino.marc.games.userflow.tictactoe.ui.screen.game

import dinino.marc.games.userflow.common.data.GamePlayData
import dinino.marc.games.userflow.common.ui.screen.game.GameState
import dinino.marc.games.userflow.tictactoe.data.TicTacToeCell
import dinino.marc.games.userflow.tictactoe.data.TicTacToeCell.Companion.to
import dinino.marc.games.userflow.tictactoe.data.TicTacToeGameData

typealias TicTacToeGameState = GameState<TicTacToeGameOverState, TicTacToeBoardState>



@Suppress("FunctionName")
fun TicTacToeNormalState(
    turn: Entry.Normal,
    gridBuilder: (TicTacToeCell) -> Entry.Normal?
) = GameState.Normal<TicTacToeGameOverState, TicTacToeBoardState>(
        board = TicTacToeBoardState(
            turn = turn,
            gridBuilder = gridBuilder
        )
)

@Suppress("FunctionName")
fun TicTacToePausedState(
    lastTurn: Entry.Normal
) = GameState.Paused<TicTacToeGameOverState, TicTacToeBoardState>(
        board = TicTacToeBoardState(
            turn = lastTurn,
            gridBuilder = { null }
        )
    )

@Suppress("FunctionName")
fun TicTacToeGameOverDrawState(
    lastTurn: Entry.Normal,
    gridBuilder: (TicTacToeCell) -> Entry.Normal
) = GameState.GameOver(
        details = TicTacToeGameOverState.Draw,
        board = TicTacToeBoardState(
            turn = lastTurn,
            gridBuilder = gridBuilder
        )
)

@Suppress("FunctionName")
fun  TicTacToeGameOverPlayerXWonState(
    winningCells: Set<TicTacToeCell>,
    gridBuilder: (TicTacToeCell) -> Entry.Normal?
)  = GameState.GameOver(
        details = TicTacToeGameOverState.PlayerXWon,
        board = TicTacToeBoardState(
            turn = Entry.Normal.PlayerXNormal,
            gridBuilder = { cell ->
                when(cell in winningCells) {
                    true ->  Entry.Winner.PlayerXWinner
                    false -> gridBuilder(cell)
                }
            }
        )
    )

@Suppress("FunctionName")
fun TicTacToeGameOverPlayerOWonState(
    winningCells: Set<TicTacToeCell>,
    gridBuilder: (TicTacToeCell) -> Entry.Normal?
) = GameState.GameOver(
        details = TicTacToeGameOverState.PlayerOWon,
        board = TicTacToeBoardState(
            turn = Entry.Normal.PlayerONormal,
            gridBuilder =  { cell ->
                when(cell in winningCells) {
                    true ->  Entry.Winner.PlayerOWinner
                    false -> gridBuilder(cell)
                }
            }
    )
)

@Suppress("FunctionName")
fun TicTacToeUserInitiatedGameOverState(
    lastTurn: Entry.Normal,
    gridBuilder: (TicTacToeCell) -> Entry?
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

sealed interface Entry {
    sealed interface Normal : Entry {
        data object PlayerXNormal : Normal
        data object PlayerONormal : Normal
    }

    sealed interface Winner : Entry {
        data object PlayerXWinner : Winner
        data object PlayerOWinner : Winner
    }
}


data class TicTacToeBoardState private constructor(
    val turn: Entry.Normal,
    val grid: Map<TicTacToeCell, Entry?>
) {
    constructor(
        turn: Entry.Normal,
        gridBuilder: (TicTacToeCell) -> Entry? = { null }
    ) : this(
        turn = turn,
        grid = buildMap {
            for (row in 0u..<TicTacToeCell.ROW_COUNT) {
                for (column in 0u..<TicTacToeCell.COLUMN_COUNT) {
                    val cell: TicTacToeCell = row to column
                    this[cell] = gridBuilder(cell)
                }
            }
        }
    )
}

fun TicTacToeGameData.toGameState(): TicTacToeGameState =
    when(playData) {
            is GamePlayData.Normal ->
                TicTacToeNormalState(turn = turn) { cell ->
                    boardData.grid.entries[cell]?.toNormalState()
                }
            is GamePlayData.Paused ->
                TicTacToePausedState(lastTurn = turn)
            is GamePlayData.GameOver ->
                when(playData.details) {
                    is TicTacToeGameData.GameOverDetails.XWins ->
                        TicTacToeGameOverPlayerXWonState(
                            winningCells = playData.details.winningCells,
                            gridBuilder = { cell ->
                                boardData.grid.entries[cell].toNormalState()
                            }
                        )
                    is TicTacToeGameData.GameOverDetails.OWins ->
                        TicTacToeGameOverPlayerOWonState(
                            winningCells = playData.details.winningCells,
                            gridBuilder = { cell ->
                                boardData.grid.entries[cell].toNormalState()
                            }
                        )
                    is TicTacToeGameData.GameOverDetails.Draw ->
                        TicTacToeGameOverDrawState(
                            lastTurn = turn,
                            gridBuilder = { cell ->
                                boardData.grid.entries[cell].toNormalState()!!
                            }
                        )
                    null -> TicTacToeUserInitiatedGameOverState(
                        lastTurn = turn,
                        gridBuilder = { cell ->
                            boardData.grid.entries[cell]?.toNormalState()
                        }
                    )

                }
        }

private val TicTacToeGameData.turn: Entry.Normal
    get() = when(boardData.turn) {
        TicTacToeGameData.BoardData.Entry.PlayerX -> Entry.Normal.PlayerXNormal
        TicTacToeGameData.BoardData.Entry.PlayerO -> Entry.Normal.PlayerONormal
    }

fun TicTacToeGameData.BoardData.Entry?.toNormalState(): Entry.Normal? =
        when(this) {
            null -> null
            TicTacToeGameData.BoardData.Entry.PlayerX -> Entry.Normal.PlayerXNormal
            TicTacToeGameData.BoardData.Entry.PlayerO -> Entry.Normal.PlayerONormal
        }








