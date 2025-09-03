package dinino.marc.games.userflow.tetris.di

import androidx.compose.runtime.Composable
import dinino.marc.games.Database
import dinino.marc.games.userflow.common.data.Repository
import dinino.marc.games.userflow.common.di.GameUserFlowProviders
import dinino.marc.games.userflow.common.di.UserFlowProviders
import dinino.marc.games.userflow.common.domain.RepositoryUseCases
import dinino.marc.games.userflow.common.domain.RepositoryUseCasesImpl
import dinino.marc.games.userflow.common.ui.SnackbarController
import dinino.marc.games.userflow.tetris.data.TetrisGameData
import dinino.marc.games.userflow.tetris.data.TetrisGameDataRepository
import dinino.marc.games.userflow.tetris.domain.TetrisUseCases
import games.composeapp.generated.resources.Res
import games.composeapp.generated.resources.userflow_tetris
import org.jetbrains.compose.resources.stringResource
import org.koin.mp.KoinPlatform.getKoin

class TetrisUserFlowProviders(
    override val localizedNameProvider: UserFlowProviders.LocalizedNameProvider =
        _localizedNameProvider,
    override val snackbarControllerProvider: UserFlowProviders.SnackbarControllerProvider =
        _snackbarControllerProvider,
    override val useCasesProvider: GameUserFlowProviders.UseCasesProvider<TetrisGameData> =
        _useCasesProvider,
): GameUserFlowProviders<TetrisGameData> {
    companion object {
        private val _localizedNameProvider = object : UserFlowProviders.LocalizedNameProvider {
            @Composable override fun provide() = stringResource(Res.string.userflow_tetris)
        }

        private val _snackbarControllerProvider = object : UserFlowProviders.SnackbarControllerProvider {
            @Composable override fun provide() = _snackbarController
        }

        private val _useCasesProvider = object : GameUserFlowProviders.UseCasesProvider<TetrisGameData> {
            override fun provide(): RepositoryUseCases<Repository<TetrisGameData>, TetrisGameData> {
                return _useCases
            }
        }

        private val _snackbarController = SnackbarController()

        private val _repository = TetrisGameDataRepository(getKoin().get<Database>().tetrisGameEntityQueries)
        private val _useCases = TetrisUseCases(_repository)
    }
}