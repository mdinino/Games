package dinino.marc.games.userflow.tictactoe.ui.screen.selectneworresumegame

import dinino.marc.games.userflow.common.data.Repository
import dinino.marc.games.userflow.common.ui.screen.selectneworresumegame.SelectNewOrResumeGameViewModel
import dinino.marc.games.userflow.tictactoe.data.TicTacToeGameData

class TicTacToeSelectNewOrResumeGameViewModel(
    repository: Repository<TicTacToeGameData>
): SelectNewOrResumeGameViewModel
<TicTacToeGameData, TicTacToeSelectNewOrResumeGameState>(
    repository = repository,
    stateFactory = { TicTacToeSelectNewOrResumeGameState(isSelectResumeGameAvailable = it) },
)