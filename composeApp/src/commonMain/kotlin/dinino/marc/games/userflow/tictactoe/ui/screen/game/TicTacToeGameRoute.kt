package dinino.marc.games.userflow.tictactoe.ui.screen.game

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import dinino.marc.games.userflow.common.ui.route.ContentWithAppBarScreenRoute
import dinino.marc.games.userflow.common.ui.route.GameUserFlowNavGraphRoute
import dinino.marc.games.userflow.tictactoe.di.TicTacToeUserFlowProviders
import kotlinx.serialization.Serializable
import org.koin.mp.KoinPlatform

@Serializable
data object TicTacToeGameRoute : ContentWithAppBarScreenRoute(),
    GameUserFlowNavGraphRoute.GameRoute {

    override val localizedTitleProvider
        get() = KoinPlatform.getKoin()
            .get<TicTacToeUserFlowProviders>().localizedNameProvider

    override val onMenuSelected
        get() = {  viewModel.togglePause() }

    @Composable
    override fun Content(modifier: Modifier, navHostController: NavHostController) =
        TicTacToeGameScreen(
            modifier = modifier,
            navHostController = navHostController,
            vm = viewModel
        )

    private val viewModel: TicTacToeGameViewModel
        get() = KoinPlatform.getKoin()
            .get<TicTacToeGameViewModel>()
}
