package dinino.marc.games.userflow.tictactoe.ui.screen.selectneworresumegame

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import dinino.marc.games.userflow.common.ui.route.ContentWithAppBarScreenRoute
import dinino.marc.games.userflow.common.ui.route.GameUserFlowNavGraphRoute
import dinino.marc.games.userflow.tictactoe.di.TicTacToeUserFlowProviders
import kotlinx.serialization.Serializable
import org.koin.mp.KoinPlatform

@Serializable
data object TicTacToeSelectNewOrResumeGameRoute : ContentWithAppBarScreenRoute(),
    GameUserFlowNavGraphRoute.SelectNewOrResumeGameRoute {

    override val localizedTitleProvider
        get() = KoinPlatform.getKoin()
        .get<TicTacToeUserFlowProviders>().localizedNameProvider

    @Composable
    override fun Content(modifier: Modifier, navHostController: NavHostController) =
        TicTacToeSelectNewOrResumeGameScreen(
            modifier = modifier,
            navHostController = navHostController
        )
}