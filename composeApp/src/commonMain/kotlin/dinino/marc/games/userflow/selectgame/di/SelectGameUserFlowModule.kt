package dinino.marc.games.userflow.selectgame.di

import dinino.marc.games.app.di.UserFlowModule
import dinino.marc.games.app.ui.SnackbarController
import dinino.marc.games.userflow.selectgame.ui.SelectGameViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

object SelectGameUserFlowModule: UserFlowModule {
    override val koinModule = module {
        single<SnackbarController>(snackbarControllerQualifier) { SnackbarController() }
        viewModel { SelectGameViewModel() }
    }
    override val snackbarControllerQualifier
        get() = named("SelectGameUserFlowSnackbarController")
}
