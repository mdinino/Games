package dinino.marc.games.di

import dinino.marc.games.ui.screen.selectgames.SelectGameViewModel
import org.koin.core.context.startKoin
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

fun initKoin() {
    startKoin {
        modules(sharedModules)
    }
}

private val sharedModules = module {
    viewModel { SelectGameViewModel() }
}