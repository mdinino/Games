package dinino.marc.games.app.di

import dinino.marc.games.platform.PlatformManager
import org.koin.dsl.module

val appModule = module {
    single { PlatformManager() }
}