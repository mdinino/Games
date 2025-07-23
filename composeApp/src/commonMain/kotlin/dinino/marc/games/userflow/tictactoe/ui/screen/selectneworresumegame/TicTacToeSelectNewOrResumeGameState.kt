package dinino.marc.games.userflow.tictactoe.ui.screen.selectneworresumegame

import dinino.marc.games.userflow.common.ui.screen.selectneworresumegame.SelectNewOrResumeGameState

class TicTacToeSelectNewOrResumeGameState(
    override val isSelectNewGameAvailable: Boolean = true,
    override val isSelectResumeGameAvailable: Boolean = false
): SelectNewOrResumeGameState