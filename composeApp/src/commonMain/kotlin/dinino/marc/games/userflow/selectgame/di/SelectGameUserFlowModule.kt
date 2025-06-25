package dinino.marc.games.userflow.selectgame.di

import androidx.compose.runtime.Composable
import dinino.marc.games.userflow.common.di.UserFlowProviders
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
            localizedNameProvider = object : UserFlowProviders.LocalizedNameProvider {
                @Composable override fun provide() = stringResource(Res.string.userflow_select_game)
            },
            snackbarControllerProvider = object : UserFlowProviders.SnackbarControllerProvider {
                @Composable override fun provide() = snackbarController
            }
        )
    }
}

private val snackbarController = SnackbarController()