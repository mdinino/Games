package dinino.marc.games.userflow.selectgame.ui

import dinino.marc.games.userflow.common.ui.route.ContentWithAppBarScreenRoute
import dinino.marc.games.userflow.common.ui.route.SerializableUserFlowRoute
import dinino.marc.games.userflow.selectgame.di.SelectGameUserFlowProviders
import kotlinx.serialization.Serializable
import org.koin.mp.KoinPlatform

@Serializable
data object SelectGameScreenRoute :
    SerializableUserFlowRoute.UserFlowScreenRoute by ContentWithAppBarScreenRoute(
        localizedTitleProvider = KoinPlatform.getKoin()
            .get<SelectGameUserFlowProviders>().localizedNameProvider,
        content = { modifier, navHostController ->
            SelectGameScreen(
                modifier = modifier,
                navHostController = navHostController
            )
        }
    ),
    SerializableUserFlowRoute.UserFlowScreenRoute.ClearUserFlowBackStack