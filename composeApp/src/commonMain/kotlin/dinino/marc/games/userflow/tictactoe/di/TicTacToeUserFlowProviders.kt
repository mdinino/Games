package dinino.marc.games.userflow.tictactoe.di

import androidx.compose.runtime.Composable
import dinino.marc.games.userflow.common.di.UserFlowProviders
import dinino.marc.games.userflow.common.ui.nav.SnackbarController
import games.composeapp.generated.resources.Res
import games.composeapp.generated.resources.userflow_tictactoe
import org.jetbrains.compose.resources.stringResource

class TicTacToeUserFlowProviders(
    override val localizedNameProvider: UserFlowProviders.LocalizedNameProvider =
        _localizedNameProvider,
    override val snackbarControllerProvider: UserFlowProviders.SnackbarControllerProvider =
        _snackbarControllerProvider

): UserFlowProviders {
    companion object {
        private val _localizedNameProvider = object : UserFlowProviders.LocalizedNameProvider {
            @Composable override fun provide() = stringResource(Res.string.userflow_tictactoe)
        }

        private val _snackbarControllerProvider = object : UserFlowProviders.SnackbarControllerProvider {
            @Composable override fun provide() = _snackbarController
        }

        private val _snackbarController = SnackbarController()
    }
}