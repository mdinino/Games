package dinino.marc.games.userflow.tictactoe.ui.screen.selectneworresumegame

import dinino.marc.games.userflow.common.domain.GameUseCases
import dinino.marc.games.userflow.common.ui.screen.selectneworresumegame.SelectNewOrResumeGameViewModel
import dinino.marc.games.userflow.tictactoe.di.TicTacToeUserFlowProviders
import org.koin.mp.KoinPlatform

class TicTacToeSelectNewOrResumeGameViewModel(
    useCases: GameUseCases<*, *> = defaultUSeCases
): SelectNewOrResumeGameViewModel<TicTacToeSelectNewOrResumeGameState>(
    useCases = useCases,
    stateFactory = { TicTacToeSelectNewOrResumeGameState(isSelectResumeGameAvailable = it) },
)

private val defaultUSeCases
    get() = KoinPlatform.getKoin()
        .get<TicTacToeUserFlowProviders>()
        .useCasesProvider
        .provide()