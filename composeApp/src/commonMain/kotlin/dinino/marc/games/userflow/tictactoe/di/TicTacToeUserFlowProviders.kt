package dinino.marc.games.userflow.tictactoe.di

import androidx.compose.runtime.Composable
import dinino.marc.games.Database
import dinino.marc.games.userflow.common.di.GameUserFlowProviders
import dinino.marc.games.userflow.common.di.UserFlowProviders
import dinino.marc.games.userflow.common.ui.SnackbarController
import dinino.marc.games.userflow.tictactoe.data.TicTacToeGameData
import dinino.marc.games.userflow.tictactoe.data.TicTacToeGameDataRepository
import dinino.marc.games.userflow.tictactoe.domain.TicTacToeUseCases
import games.composeapp.generated.resources.Res
import games.composeapp.generated.resources.userflow_tictactoe
import org.jetbrains.compose.resources.stringResource
import org.koin.mp.KoinPlatform.getKoin

class TicTacToeUserFlowProviders(
    override val localizedNameProvider: UserFlowProviders.LocalizedNameProvider =
        _localizedNameProvider,
    override val snackbarControllerProvider: UserFlowProviders.SnackbarControllerProvider =
        _snackbarControllerProvider,
    override val useCasesProvider: GameUserFlowProviders.UseCasesProvider<TicTacToeGameData> =
        _useCasesProvider
): GameUserFlowProviders<TicTacToeGameData> {
    companion object {
        private val _localizedNameProvider = object : UserFlowProviders.LocalizedNameProvider {
            @Composable override fun provide() = stringResource(Res.string.userflow_tictactoe)
        }

        private val _snackbarControllerProvider = object : UserFlowProviders.SnackbarControllerProvider {
            @Composable override fun provide() = _snackbarController
        }

        private val _useCasesProvider = object :
            GameUserFlowProviders.UseCasesProvider<TicTacToeGameData> {
                override fun provide() = _useCases
        }

        private val _snackbarController = SnackbarController()

        private val _repository = TicTacToeGameDataRepository(getKoin().get<Database>().ticTacToeGameEntityQueries)
        private val _useCases = TicTacToeUseCases(_repository)
    }
}