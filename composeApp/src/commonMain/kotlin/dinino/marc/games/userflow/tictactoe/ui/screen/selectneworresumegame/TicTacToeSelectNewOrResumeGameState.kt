package dinino.marc.games.userflow.tictactoe.ui.screen.selectneworresumegame

import dinino.marc.games.userflow.common.ui.selectneworresumegame.DefaultSelectNewOrResumeGameState
import dinino.marc.games.userflow.common.ui.selectneworresumegame.SelectNewOrResumeGameState

class TicTacToeSelectNewOrResumeGameState(
    isSelectNewGameAvailable: Boolean = true,
    isSelectResumeGameAvailable: Boolean
): SelectNewOrResumeGameState by DefaultSelectNewOrResumeGameState(
    isSelectNewGameAvailable = isSelectNewGameAvailable,
    isSelectResumeGameAvailable = isSelectResumeGameAvailable
)