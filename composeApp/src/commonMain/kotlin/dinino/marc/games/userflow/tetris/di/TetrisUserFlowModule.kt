package dinino.marc.games.userflow.tetris.di

import dinino.marc.games.userflow.tetris.ui.screen.game.TetrisGameViewModel
import dinino.marc.games.userflow.tetris.ui.screen.game.dialog.TetrisPauseGameViewModel
import dinino.marc.games.userflow.tetris.ui.screen.gameover.TetrisGameOverViewModel
import dinino.marc.games.userflow.tetris.ui.screen.selectneworresumegame.TetrisSelectNewOrResumeGameViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val tetrisUserFlowModule = module {
    single { TetrisUserFlowProviders() }
    viewModelOf(::TetrisSelectNewOrResumeGameViewModel)
    viewModel { (newGame: Boolean) -> TetrisGameViewModel(newGame = newGame) }
    viewModelOf(::TetrisPauseGameViewModel)
    viewModelOf(::TetrisGameOverViewModel)
}