package dinino.marc.games.userflow.tetris.ui.screen.selectneworresumegame

import dinino.marc.games.userflow.common.ui.route.ContentWithAppBarScreenRoute
import dinino.marc.games.userflow.common.ui.route.SerializableUserFlowRoute
import dinino.marc.games.userflow.tetris.di.TetrisUserFlowProviders
import kotlinx.serialization.Serializable
import org.koin.mp.KoinPlatform

@Serializable
data object TetrisSelectNewOrResumeGameRoute :
    SerializableUserFlowRoute.UserFlowScreenRoute by ContentWithAppBarScreenRoute(
        localizedTitleProvider = KoinPlatform.getKoin()
            .get<TetrisUserFlowProviders>().localizedNameProvider,
        content = { modifier, navHostController ->
            TetrisSelectNewOrResumeGameScreen(
                modifier = modifier,
                navHostController = navHostController
            )
        }
    ), SerializableUserFlowRoute.UserFlowScreenRoute.ClearBackStackUpToHere