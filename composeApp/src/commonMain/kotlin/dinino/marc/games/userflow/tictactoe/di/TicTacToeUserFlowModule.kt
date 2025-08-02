package dinino.marc.games.userflow.tictactoe.di

import dinino.marc.games.userflow.tictactoe.ui.screen.game.TicTacToeGameViewModel
import dinino.marc.games.userflow.tictactoe.ui.screen.gameover.TicTacToeGameOverViewModel
import dinino.marc.games.userflow.tictactoe.ui.screen.selectneworresumegame.TicTacToeSelectNewOrResumeGameViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import org.koin.mp.KoinPlatform

val ticTacToeUserFlowModule = module {
    single { TicTacToeUserFlowProviders() }
    viewModel { TicTacToeSelectNewOrResumeGameViewModel(repository) }
    viewModel { TicTacToeGameViewModel(repository) }
    viewModel { TicTacToeGameOverViewModel() }
}

private val repository
    get() = KoinPlatform.getKoin()
        .get<TicTacToeUserFlowProviders>()
        .repositoryProvider
        .provide()