package dinino.marc.games.app.di

import dinino.marc.games.Database
import org.koin.core.module.Module
import org.koin.dsl.module

val appModule: Module =
    module {
        single { Database(driver = get()) }
    }