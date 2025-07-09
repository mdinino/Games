package dinino.marc.games.userflow.tictactoe.di

import org.koin.dsl.module

val ticTacToeUserFlowModule = module {
    single { TicTacToeUserFlowProviders() }
}