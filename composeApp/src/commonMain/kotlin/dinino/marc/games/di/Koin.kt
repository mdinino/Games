package dinino.marc.games.di

import dinino.marc.games.ui.screen.selectgames.SelectGamesViewModel
import org.koin.core.context.startKoin
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

object Koin {
    fun init() {
        startKoin { modules(appModule) }
    }

    private val appModule = module {
        viewModel { SelectGamesViewModel() }
    }
}

