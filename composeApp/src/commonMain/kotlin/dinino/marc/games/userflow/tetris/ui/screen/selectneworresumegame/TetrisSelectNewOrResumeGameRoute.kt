package dinino.marc.games.userflow.tetris.ui.screen.selectneworresumegame

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import dinino.marc.games.userflow.common.ui.route.ContentWithAppBarScreenRoute
import dinino.marc.games.userflow.common.ui.route.GameUserFlowNavGraphRouteImpl
import dinino.marc.games.userflow.common.ui.route.SelectNewOrResumeGameRoute
import dinino.marc.games.userflow.common.ui.route.SerializableUserFlowRoute
import dinino.marc.games.userflow.tetris.di.TetrisUserFlowProviders
import kotlinx.serialization.Serializable
import org.koin.mp.KoinPlatform

@Serializable
data object TetrisSelectNewOrResumeGameRoute : ContentWithAppBarScreenRoute(),
    SelectNewOrResumeGameRoute {

    override val localizedTitleProvider
        get() = KoinPlatform.getKoin()
            .get<TetrisUserFlowProviders>().localizedNameProvider

    @Composable
    override fun Content(modifier: Modifier, navHostController: NavHostController) =
        TetrisSelectNewOrResumeGameScreen(
            modifier = modifier,
            navHostController = navHostController
        )
    }