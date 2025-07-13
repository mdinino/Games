package dinino.marc.games.userflow.tictactoe.di

import androidx.compose.runtime.Composable
import dinino.marc.games.Database
import dinino.marc.games.userflow.common.di.GameUserFlowProviders
import dinino.marc.games.userflow.common.di.UserFlowProviders
import dinino.marc.games.userflow.common.ui.SnackbarController
import dinino.marc.games.userflow.tictactoe.data.TicTacToeGame
import dinino.marc.games.userflow.tictactoe.data.TicTacToeGameRepository
import games.composeapp.generated.resources.Res
import games.composeapp.generated.resources.userflow_tictactoe
import org.jetbrains.compose.resources.stringResource
import org.koin.mp.KoinPlatform.getKoin

class TicTacToeUserFlowProviders(
    override val localizedNameProvider: UserFlowProviders.LocalizedNameProvider =
        _localizedNameProvider,
    override val snackbarControllerProvider: UserFlowProviders.SnackbarControllerProvider =
        _snackbarControllerProvider,
    override val repositoryProvider: GameUserFlowProviders.RepositoryProvider<TicTacToeGame> =
        _repositoryProvider
): GameUserFlowProviders<TicTacToeGame> {
    companion object {
        private val _localizedNameProvider = object : UserFlowProviders.LocalizedNameProvider {
            @Composable override fun provide() = stringResource(Res.string.userflow_tictactoe)
        }

        private val _snackbarControllerProvider = object : UserFlowProviders.SnackbarControllerProvider {
            @Composable override fun provide() = _snackbarController
        }

        private val _repositoryProvider = object :
            GameUserFlowProviders.RepositoryProvider<TicTacToeGame> {
                override fun provide() = _repostory
        }

        private val _snackbarController = SnackbarController()

        private val _repostory = TicTacToeGameRepository(getKoin().get<Database>().ticTacToeGameEntityQueries)
    }
}