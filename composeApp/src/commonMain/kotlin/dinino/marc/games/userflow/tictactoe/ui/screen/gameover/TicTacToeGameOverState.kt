package dinino.marc.games.userflow.tictactoe.ui.screen.gameover

import dinino.marc.games.userflow.common.ui.screen.gameover.GameOverState

class TicTacToeGameOverState(
    override val isSelectStartNewGameAvailable: Boolean = true,
    override val isSelectDifferentGameAvailable: Boolean = true
): GameOverState