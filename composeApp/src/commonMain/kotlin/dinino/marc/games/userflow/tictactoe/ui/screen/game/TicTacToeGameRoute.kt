package dinino.marc.games.userflow.tictactoe.ui.screen.game

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import dinino.marc.games.stateflow.mapStateFlow
import dinino.marc.games.userflow.common.ui.layout.MenuSelected
import dinino.marc.games.userflow.common.ui.layout.ShownDisabled
import dinino.marc.games.userflow.common.ui.layout.ShownEnabled
import dinino.marc.games.userflow.common.ui.route.ContentWithActionBarScreenRoute
import dinino.marc.games.userflow.common.ui.route.GameUserFlowNavGraphRoute
import dinino.marc.games.userflow.common.ui.screen.game.GameState
import dinino.marc.games.userflow.tictactoe.di.TicTacToeUserFlowProviders
import kotlinx.coroutines.flow.Flow
import kotlinx.serialization.Serializable
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf
import org.koin.mp.KoinPlatform

@Serializable
data class TicTacToeGameRoute(val newGame: Boolean = false) :
    ContentWithActionBarScreenRoute(), GameUserFlowNavGraphRoute.GameRoute {

    override val localizedTitleProvider
        get() = KoinPlatform.getKoin()
            .get<TicTacToeUserFlowProviders>().localizedNameProvider

    @Composable
    override fun showMenuIconFlowFactory() =
        viewModel.gameState
            .mapStateFlow {
                when(it) {
                    is GameState.Normal -> ShownEnabled
                    is GameState.Paused -> ShownEnabled
                    is GameState.GameOver -> ShownDisabled
                }
            }

    @Composable
    override fun Content(
        modifier: Modifier,
        navHostController: NavHostController,
        menuSelectedOneTimeEvent: Flow<MenuSelected>
    ) = TicTacToeGameScreen(
            modifier = modifier,
            navHostController = navHostController,
            menuSelectedOneTimeEvent = menuSelectedOneTimeEvent,
            vm = viewModel
        )

    @get:Composable
    private val viewModel
        get() = koinViewModel<TicTacToeGameViewModel>(
            parameters = { parametersOf(newGame) }
        )
}
