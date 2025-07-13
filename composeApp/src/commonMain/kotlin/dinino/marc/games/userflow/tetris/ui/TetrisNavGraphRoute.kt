package dinino.marc.games.userflow.tetris.ui

import dinino.marc.games.userflow.common.ui.route.SerializableUserFlowRoute.UserFlowNavGraphRoute
import dinino.marc.games.userflow.common.ui.route.UserFlowNavGraphRouteImpl
import dinino.marc.games.userflow.tetris.di.TetrisUserFlowProviders
import kotlinx.serialization.Serializable
import org.koin.mp.KoinPlatform

@Serializable
data object TetrisNavGraphRoute :
    UserFlowNavGraphRoute by UserFlowNavGraphRouteImpl(
        landingScreenRoute = TetrisSelectNewOrResumeGameRoute,
        snackbarControllerProvider = KoinPlatform.getKoin()
            .get<TetrisUserFlowProviders>()
            .snackbarControllerProvider
    )