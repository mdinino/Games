package dinino.marc.games.userflow.selectgame.di

import dinino.marc.games.userflow.selectgame.ui.SelectGameViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val selectGameUserFlowModule = module {
    viewModelOf(::SelectGameViewModel)
    single { SelectGameUserFlowProviders() }
}