package dinino.marc.games.userflow.tetris.ui.screen.game

import dinino.marc.games.userflow.common.ui.screen.game.GameState

typealias TetrisGameState = GameState<Unit, TetrisBoardState>

data class TetrisBoardState(val test: String = "")