package dinino.marc.games.di

import dinino.marc.games.userflow.selectgame.di.selectGameUserFlowModule
import org.koin.core.context.startKoin

fun initKoin() {
    startKoin {
        modules(selectGameUserFlowModule)
    }
}