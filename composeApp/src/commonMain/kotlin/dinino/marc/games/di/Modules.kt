package dinino.marc.games.di

import dinino.marc.games.ui.screen.selectgames.SelectGameViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val modules = module {
    viewModelOf(::SelectGameViewModel)
}