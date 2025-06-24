package dinino.marc.games.userflow.selectgame.ui

import dinino.marc.games.userflow.common.ui.SerializableUserFlowRoute.UserFlowNavGraphRoute
import dinino.marc.games.userflow.common.ui.UserFlowNavGraphRouteImpl
import dinino.marc.games.userflow.selectgame.di.SelectGameUserFlowProviders
import kotlinx.serialization.Serializable
import org.koin.mp.KoinPlatform.getKoin

@Serializable
data object SelectGameNavGraphRoute :
    UserFlowNavGraphRoute by UserFlowNavGraphRouteImpl(
        landingScreenRoute = SelectGameScreenRoute,
        snackbarController = getKoin()
            .get<SelectGameUserFlowProviders>()
            .snackbarControllerProvider
    )