package dinino.marc.games.app.di

import dinino.marc.games.userflow.selectgame.di.selectGameUserFLowModule
import org.koin.core.context.startKoin

fun initKoin() {
    startKoin {
        modules(selectGameUserFLowModule)
    }
}