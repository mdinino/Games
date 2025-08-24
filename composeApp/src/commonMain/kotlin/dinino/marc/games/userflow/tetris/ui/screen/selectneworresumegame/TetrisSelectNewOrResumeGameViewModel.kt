package dinino.marc.games.userflow.tetris.ui.screen.selectneworresumegame

import dinino.marc.games.userflow.common.data.Repository
import dinino.marc.games.userflow.common.ui.screen.selectneworresumegame.SelectNewOrResumeGameViewModel
import dinino.marc.games.userflow.tetris.data.TetrisGameData
import dinino.marc.games.userflow.tetris.di.TetrisUserFlowProviders
import org.koin.mp.KoinPlatform

class TetrisSelectNewOrResumeGameViewModel(
    repository: Repository<TetrisGameData> = defaultRepository
): SelectNewOrResumeGameViewModel
    <TetrisGameData, TetrisSelectNewOrResumeGameState>(
    repository = repository,
    stateFactory = { TetrisSelectNewOrResumeGameState(isSelectResumeGameAvailable = it) },
)

private val defaultRepository
    get() = KoinPlatform.getKoin()
        .get<TetrisUserFlowProviders>()
        .repositoryProvider
        .provide()