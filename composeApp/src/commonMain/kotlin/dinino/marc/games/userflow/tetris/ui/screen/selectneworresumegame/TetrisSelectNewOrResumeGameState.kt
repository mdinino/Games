package dinino.marc.games.userflow.tetris.ui.screen.selectneworresumegame

import dinino.marc.games.userflow.common.ui.screen.selectneworresumegame.SelectNewOrResumeGameState

data class TetrisSelectNewOrResumeGameState(
    override val isSelectNewGameAvailable: Boolean = true,
    override val isSelectResumeGameAvailable: Boolean = false
): SelectNewOrResumeGameState