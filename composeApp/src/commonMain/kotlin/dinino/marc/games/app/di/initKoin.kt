package dinino.marc.games.app.di

import dinino.marc.games.userflow.selectgame.ui.nav.SelectGameUserFlow
import dinino.marc.games.userflow.selectgame.di.selectGameUserFLowModule
import org.koin.core.context.startKoin

fun initKoin() {
    startKoin {
        modules(SelectGameUserFlow.koinModule)
    }
}