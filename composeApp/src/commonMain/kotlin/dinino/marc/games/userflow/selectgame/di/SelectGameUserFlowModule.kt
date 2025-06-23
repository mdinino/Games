package dinino.marc.games.userflow.selectgame.di

import dinino.marc.games.userflow.common.ui.SnackbarController
import dinino.marc.games.userflow.selectgame.ui.SelectGameViewModel
import games.composeapp.generated.resources.Res
import games.composeapp.generated.resources.userflow_select_game
import org.jetbrains.compose.resources.stringResource
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val selectGameUserFLowModule = module {
    viewModel { SelectGameViewModel() }
    single {
        SelectGameUserFlowProviders(
            localizedNameProvider = { stringResource(Res.string.userflow_select_game) },
            snackbarControllerProvider = { snackbarController }
        )
    }
}

private val snackbarController = SnackbarController()