package dinino.marc.games.userflow.tetris.ui.screen.selectneworresumegame

import dinino.marc.games.userflow.common.ui.selectneworresumegame.DefaultSelectNewOrResumeGameState
import dinino.marc.games.userflow.common.ui.selectneworresumegame.SelectNewOrResumeGameState

class TetrisSelectNewOrResumeGameState(
    isSelectNewGameAvailable: Boolean = true,
    isSelectResumeGameAvailable: Boolean
): SelectNewOrResumeGameState by DefaultSelectNewOrResumeGameState(
    isSelectNewGameAvailable = isSelectNewGameAvailable,
    isSelectResumeGameAvailable = isSelectResumeGameAvailable
)