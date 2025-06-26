package dinino.marc.games.userflow.selectgame.di


import dinino.marc.games.userflow.selectgame.ui.screen.SelectGameViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val selectGameUserFlowModule = module {
    viewModel<SelectGameViewModel> { SelectGameViewModel() }
    single { SelectGameUserFlowProviders() }
}