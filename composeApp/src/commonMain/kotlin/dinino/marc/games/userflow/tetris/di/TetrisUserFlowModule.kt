package dinino.marc.games.userflow.tetris.di

import dinino.marc.games.userflow.tetris.ui.screen.game.TetrisGameViewModel
import dinino.marc.games.userflow.tetris.ui.screen.gameover.TetrisGameOverViewModel
import dinino.marc.games.userflow.tetris.ui.screen.selectneworresumegame.TetrisSelectNewOrResumeGameViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val tetrisUserFlowModule = module {
    single { TetrisUserFlowProviders() }
    viewModel { TetrisSelectNewOrResumeGameViewModel() }
    viewModel { TetrisGameViewModel() }
    viewModel { TetrisGameOverViewModel() }
}