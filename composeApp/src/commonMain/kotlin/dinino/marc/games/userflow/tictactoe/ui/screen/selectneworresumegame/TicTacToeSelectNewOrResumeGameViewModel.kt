package dinino.marc.games.userflow.tictactoe.ui.screen.selectneworresumegame

import dinino.marc.games.userflow.common.data.Repository
import dinino.marc.games.userflow.common.domain.RepositoryUseCases
import dinino.marc.games.userflow.common.ui.screen.selectneworresumegame.SelectNewOrResumeGameViewModel
import dinino.marc.games.userflow.tictactoe.data.TicTacToeGameData
import dinino.marc.games.userflow.tictactoe.di.TicTacToeUserFlowProviders
import org.koin.mp.KoinPlatform

class TicTacToeSelectNewOrResumeGameViewModel(
    useCases: RepositoryUseCases<*, *> = defaultUSeCases
): SelectNewOrResumeGameViewModel<TicTacToeSelectNewOrResumeGameState>(
    useCases = useCases,
    stateFactory = { TicTacToeSelectNewOrResumeGameState(isSelectResumeGameAvailable = it) },
)

private val defaultUSeCases
    get() = KoinPlatform.getKoin()
        .get<TicTacToeUserFlowProviders>()
        .useCasesProvider
        .provide()