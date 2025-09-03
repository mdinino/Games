package dinino.marc.games.userflow.tetris.ui.screen.selectneworresumegame

import dinino.marc.games.userflow.common.domain.RepositoryUseCases
import dinino.marc.games.userflow.common.ui.screen.selectneworresumegame.SelectNewOrResumeGameViewModel
import dinino.marc.games.userflow.tetris.di.TetrisUserFlowProviders
import org.koin.mp.KoinPlatform

class TetrisSelectNewOrResumeGameViewModel(
    useCases: RepositoryUseCases<*, *> = defaultUseCases,
): SelectNewOrResumeGameViewModel<TetrisSelectNewOrResumeGameState>(
    useCases = useCases,
    stateFactory = { TetrisSelectNewOrResumeGameState(isSelectResumeGameAvailable = it) },
)

private val defaultUseCases
    get() = KoinPlatform.getKoin()
        .get<TetrisUserFlowProviders>()
        .useCasesProvider
        .provide()