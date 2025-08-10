package dinino.marc.games.userflow.tetris.ui.screen.game

import dinino.marc.games.userflow.common.ui.screen.game.GameState

typealias TetrisGameState = GameState<Unit, TetrisBoardState>

val defaultTetrisBoardState = TetrisBoardState()

val hiddenTetrisBoardState = TetrisBoardState()

data class TetrisBoardState(val test: String = "")