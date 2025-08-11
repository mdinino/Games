package dinino.marc.games.userflow.tetris.ui.screen.game

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import dinino.marc.games.userflow.common.ui.route.ContentWithAppBarScreenRoute
import dinino.marc.games.userflow.common.ui.route.GameUserFlowNavGraphRoute
import dinino.marc.games.userflow.tetris.di.TetrisUserFlowProviders
import kotlinx.serialization.Serializable
import org.koin.mp.KoinPlatform

@Serializable
data object TetrisGameRoute : ContentWithAppBarScreenRoute(),
    GameUserFlowNavGraphRoute.GameRoute {

    override val localizedTitleProvider
        get() = KoinPlatform.getKoin()
                .get<TetrisUserFlowProviders>()
                .localizedNameProvider

    override val onMenuSelected
        get() = {  viewModel.togglePause() }

    @Composable
    override fun Content(modifier: Modifier, navHostController: NavHostController) =
        TetrisGameScreen(
            modifier = modifier,
            navHostController = navHostController,
            onActionBarMenuSelected = onMenuSelected,
            vm = viewModel
        )

    private val viewModel
        get() = KoinPlatform.getKoin()
            .get<TetrisGameViewModel>()
}
