package dinino.marc.games.userflow.selectgame.ui.nav

import dinino.marc.games.userflow.common.ui.nav.ContentWithAppBarScreenRoute
import dinino.marc.games.userflow.common.ui.nav.SerializableUserFlowRoute.UserFlowScreenRoute
import dinino.marc.games.userflow.common.ui.nav.SerializableUserFlowRoute.UserFlowScreenRoute.ClearBackStackUpToHere
import dinino.marc.games.userflow.selectgame.di.SelectGameUserFlowProviders
import dinino.marc.games.userflow.selectgame.ui.screen.SelectGameScreen
import kotlinx.serialization.Serializable
import org.koin.mp.KoinPlatform

@Serializable
data object SelectGameScreenRoute :
    UserFlowScreenRoute by ContentWithAppBarScreenRoute(
        localizedTitleProvider = KoinPlatform.getKoin()
            .get<SelectGameUserFlowProviders>().localizedNameProvider,
        content = { modifier, navHostController ->
            SelectGameScreen(
                modifier = modifier,
                navHostController = navHostController
            )
        }
    ),
    ClearBackStackUpToHere