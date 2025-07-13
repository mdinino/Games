package dinino.marc.games.userflow.selectgame.di

import androidx.compose.runtime.Composable
import dinino.marc.games.userflow.common.di.UserFlowProviders
import dinino.marc.games.userflow.common.ui.SnackbarController
import games.composeapp.generated.resources.Res
import games.composeapp.generated.resources.userflow_select_game
import org.jetbrains.compose.resources.stringResource

class SelectGameUserFlowProviders(
    override val localizedNameProvider: UserFlowProviders.LocalizedNameProvider =
        _localizedNameProvider,
    override val snackbarControllerProvider: UserFlowProviders.SnackbarControllerProvider =
        _snackbarControllerProvider
): UserFlowProviders {
    companion object {
        private val _localizedNameProvider = object : UserFlowProviders.LocalizedNameProvider {
            @Composable override fun provide() = stringResource(Res.string.userflow_select_game)
        }

        private val _snackbarControllerProvider = object : UserFlowProviders.SnackbarControllerProvider {
            @Composable override fun provide() = _snackbarController
        }

        private val _snackbarController = SnackbarController()
    }
}


