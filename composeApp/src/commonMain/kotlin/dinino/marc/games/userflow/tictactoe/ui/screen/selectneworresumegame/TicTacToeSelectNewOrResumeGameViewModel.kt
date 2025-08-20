package dinino.marc.games.userflow.tictactoe.ui.screen.selectneworresumegame

import dinino.marc.games.userflow.common.data.Repository
import dinino.marc.games.userflow.common.ui.screen.selectneworresumegame.SelectNewOrResumeGameViewModel
import dinino.marc.games.userflow.tictactoe.data.TicTacToeGameData
import dinino.marc.games.userflow.tictactoe.di.TicTacToeUserFlowProviders
import org.koin.mp.KoinPlatform

class TicTacToeSelectNewOrResumeGameViewModel(
    repository: Repository<TicTacToeGameData> = defaultRepository
): SelectNewOrResumeGameViewModel
<TicTacToeGameData, TicTacToeSelectNewOrResumeGameState>(
    repository = repository,
    newGameFactory = { TicTacToeGameData() },
    stateFactory = { TicTacToeSelectNewOrResumeGameState(isSelectResumeGameAvailable = it) },
)

private val defaultRepository
    get() = KoinPlatform.getKoin()
        .get<TicTacToeUserFlowProviders>()
        .repositoryProvider
        .provide()