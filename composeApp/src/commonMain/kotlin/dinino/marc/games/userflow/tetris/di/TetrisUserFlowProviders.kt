package dinino.marc.games.userflow.tetris.di

import androidx.compose.runtime.Composable
import dinino.marc.games.Database
import dinino.marc.games.userflow.common.di.GameUserFlowProviders
import dinino.marc.games.userflow.common.di.UserFlowProviders
import dinino.marc.games.userflow.common.ui.SnackbarController
import dinino.marc.games.userflow.tetris.data.TetrisGameData
import dinino.marc.games.userflow.tetris.data.TetrisGameDataRepository
import games.composeapp.generated.resources.Res
import games.composeapp.generated.resources.userflow_tetris
import org.jetbrains.compose.resources.stringResource
import org.koin.mp.KoinPlatform.getKoin

class TetrisUserFlowProviders(
    override val localizedNameProvider: UserFlowProviders.LocalizedNameProvider =
        _localizedNameProvider,
    override val snackbarControllerProvider: UserFlowProviders.SnackbarControllerProvider =
        _snackbarControllerProvider,
    override val repositoryProvider: GameUserFlowProviders.RepositoryProvider<TetrisGameData> =
        _repositoryProvider
): GameUserFlowProviders<TetrisGameData> {
    companion object {
        private val _localizedNameProvider = object : UserFlowProviders.LocalizedNameProvider {
            @Composable override fun provide() = stringResource(Res.string.userflow_tetris)
        }

        private val _snackbarControllerProvider = object : UserFlowProviders.SnackbarControllerProvider {
            @Composable override fun provide() = _snackbarController
        }

        private val _repositoryProvider = object :
            GameUserFlowProviders.RepositoryProvider<TetrisGameData> {
            override fun provide() = _repostory
        }

        private val _snackbarController = SnackbarController()

        private val _repostory = TetrisGameDataRepository(getKoin().get<Database>().tetrisGameEntityQueries)
    }
}