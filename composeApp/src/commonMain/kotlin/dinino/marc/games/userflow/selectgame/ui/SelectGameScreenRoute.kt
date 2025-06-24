package dinino.marc.games.userflow.selectgame.ui

import dinino.marc.games.userflow.common.ui.ContentWithAppBarScreenRoute
import dinino.marc.games.userflow.common.ui.SerializableUserFlowRoute.UserFlowScreenRoute
import dinino.marc.games.userflow.common.ui.SerializableUserFlowRoute.UserFlowScreenRoute.ClearBackStack
import dinino.marc.games.userflow.selectgame.di.SelectGameUserFlowProviders
import kotlinx.serialization.Serializable
import org.koin.mp.KoinPlatform.getKoin

@Serializable
data object SelectGameScreenRoute :
    UserFlowScreenRoute by ContentWithAppBarScreenRoute(
        localizedTitle = getKoin().get<SelectGameUserFlowProviders>().localizedNameProvider,
        content = { modifier ->
            SelectGameScreen(modifier = modifier)
        }
    ),
    ClearBackStack