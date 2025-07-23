package dinino.marc.games.userflow.tetris.ui.screen.gameover

import dinino.marc.games.userflow.common.ui.screen.gameover.GameOverViewModel

class TetrisGameOverViewModel(
    state: TetrisGameOverState = TetrisGameOverState()
): GameOverViewModel<TetrisGameOverState>(initialState = state)