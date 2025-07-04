package dinino.marc.games.platform.di

import dinino.marc.games.platform.PlatformManager
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

actual val platformModule: Module =
    module {
        singleOf(::PlatformManager)
    }