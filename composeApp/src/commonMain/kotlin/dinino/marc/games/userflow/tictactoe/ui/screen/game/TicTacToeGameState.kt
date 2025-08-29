package dinino.marc.games.userflow.tictactoe.ui.screen.game

import dinino.marc.games.userflow.common.ui.screen.game.GameState
import dinino.marc.games.userflow.tictactoe.data.TicTacToeCell
import dinino.marc.games.userflow.tictactoe.data.TicTacToeCell.Companion.to
import dinino.marc.games.userflow.tictactoe.ui.screen.game.TicTacToeBoardState.Entry

typealias TicTacToeGameState = GameState<TicTacToeGameOverState, TicTacToeBoardState>

@Suppress("FunctionName")
fun TicTacToeNormalState(turn: Entry.Variant.Normal, gridBuilder: (TicTacToeCell) -> Entry.Player?) =
    GameState.Normal<TicTacToeGameOverState, TicTacToeBoardState>(
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
    gridBuilder: (TicTacToeCell) -> Entry.Player?
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
    gridBuilder: (TicTacToeCell) -> Entry.Player?
) = ticTacToeGameOverPlayerWonState(
    turnEntry = Entry.PlayerXNormal,
    winningEntry = Entry.PlayerXWinner,
    winningCells = winningCells,
    gridBuilder = gridBuilder
)


@Suppress("FunctionName")
fun TicTacToeGameOverPlayerOWonState(
    winningCells: Set<TicTacToeCell>,
    gridBuilder: (TicTacToeCell) -> Entry.Player?
) = ticTacToeGameOverPlayerWonState(
    turnEntry = Entry.PlayerONormal,
    winningEntry = Entry.PlayerOWinner,
    winningCells = winningCells,
    gridBuilder = gridBuilder
)

private fun ticTacToeGameOverPlayerWonState(
    turnEntry: Entry.Variant.Normal,
    winningEntry: Entry.Variant.Winner,
    winningCells: Set<TicTacToeCell>,
    gridBuilder: (TicTacToeCell) -> Entry.Player?
) = GameState.GameOver(
    details = TicTacToeGameOverState.PlayerXWon,
    board = TicTacToeBoardState(
        turn = turnEntry,
        gridBuilder =  { cell ->
            when(cell in winningCells) {
                true ->  winningEntry
                false -> gridBuilder(cell)
            }
        }
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






