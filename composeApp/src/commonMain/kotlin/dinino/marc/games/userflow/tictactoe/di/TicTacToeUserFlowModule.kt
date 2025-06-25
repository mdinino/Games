package dinino.marc.games.userflow.tictactoe.di

import org.koin.dsl.module

val ticTacToeUserFLowModule = module {
    single { TicTacToeUserFlowProviders() }
}