package dinino.marc.games.userflow.tetris.di

import org.koin.dsl.module

val tetrisUserFlowModule = module {
    single { TetrisUserFlowProviders() }
}