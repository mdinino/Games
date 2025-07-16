package dinino.marc.games.userflow.tetris.di


import dinino.marc.games.userflow.tetris.ui.screen.gameover.TetrisGameOverViewModel
import dinino.marc.games.userflow.tetris.ui.screen.selectneworresumegame.TetrisSelectNewOrResumeGameViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import org.koin.mp.KoinPlatform

val tetrisUserFlowModule = module {
    single { TetrisUserFlowProviders() }
    viewModel { TetrisSelectNewOrResumeGameViewModel(
        repository = KoinPlatform.getKoin()
            .get<TetrisUserFlowProviders>()
            .repositoryProvider
            .provide())
    }
    viewModel { TetrisGameOverViewModel() }
}