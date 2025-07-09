package dinino.marc.games.app.di

import dinino.marc.games.Database
import dinino.marc.games.platform.PlatformManager
import org.koin.core.module.Module
import org.koin.dsl.module

val appModule: Module =
    module {
        single { Database(get<PlatformManager>().databaseDriver) }
    }