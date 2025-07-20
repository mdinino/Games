package dinino.marc.games.userflow.tetris.ui.screen.gameover

import dinino.marc.games.userflow.common.ui.route.ContentWithAppBarScreenRoute
import dinino.marc.games.userflow.common.ui.route.SerializableUserFlowRoute
import dinino.marc.games.userflow.tetris.di.TetrisUserFlowProviders
import kotlinx.serialization.Serializable
import org.koin.mp.KoinPlatform

@Serializable
data object TetrisGameOverRoute :
    SerializableUserFlowRoute.UserFlowScreenRoute by ContentWithAppBarScreenRoute(
        localizedTitleProvider = KoinPlatform.getKoin()
            .get<TetrisUserFlowProviders>().localizedNameProvider,
        content = { modifier, navHostController ->
            TetrisGameOverScreen(
                modifier = modifier,
                navHostController = navHostController
            )
        }
    ), SerializableUserFlowRoute.UserFlowScreenRoute.ClearUserFlowBackStack