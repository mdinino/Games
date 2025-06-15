package dinino.marc.games.app.di

import dinino.marc.games.userflow.selectgame.di.SelectGameUserFlowModule
import org.koin.core.context.startKoin

fun initKoin() {
    startKoin {
        modules(SelectGameUserFlowModule.koinModule)
    }
}