package dinino.marc.games.userflow.tictactoe.di

import dinino.marc.games.userflow.tictactoe.ui.screen.selectneworresumegame.TicTacToeSelectNewOrResumeGameViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import org.koin.mp.KoinPlatform

val ticTacToeUserFlowModule = module {
    single { TicTacToeUserFlowProviders() }
    viewModel {
        TicTacToeSelectNewOrResumeGameViewModel(
            repository = KoinPlatform.getKoin()
                .get<TicTacToeUserFlowProviders>()
                .repositoryProvider
                .provide()
        )
    }
}