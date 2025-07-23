package dinino.marc.games.userflow.tictactoe.ui.screen.gameover

import dinino.marc.games.userflow.common.ui.screen.gameover.GameOverViewModel

class TicTacToeGameOverViewModel(
    state: TicTacToeGameOverState = TicTacToeGameOverState()
): GameOverViewModel<TicTacToeGameOverState>(initialState = state)