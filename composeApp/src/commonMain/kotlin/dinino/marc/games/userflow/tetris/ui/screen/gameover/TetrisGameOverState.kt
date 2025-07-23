package dinino.marc.games.userflow.tetris.ui.screen.gameover

import dinino.marc.games.userflow.common.ui.screen.gameover.GameOverState

class TetrisGameOverState(
    override val isSelectStartNewGameAvailable: Boolean = true,
    override val isSelectDifferentGameAvailable: Boolean = true
): GameOverState