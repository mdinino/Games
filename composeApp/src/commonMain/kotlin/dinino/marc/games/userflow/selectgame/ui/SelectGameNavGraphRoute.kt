package dinino.marc.games.userflow.selectgame.ui

import dinino.marc.games.userflow.common.ui.route.SerializableUserFlowRoute
import dinino.marc.games.userflow.common.ui.route.UserFlowNavGraphRouteImpl
import dinino.marc.games.userflow.selectgame.di.SelectGameUserFlowProviders
import dinino.marc.games.userflow.tictactoe.ui.TicTacToeNavGraphRoute
import kotlinx.serialization.Serializable
import org.koin.mp.KoinPlatform

@Serializable
data object SelectGameNavGraphRoute :
    SerializableUserFlowRoute.UserFlowNavGraphRoute by UserFlowNavGraphRouteImpl(
        landingScreenRoute = SelectGameScreenRoute,
        otherRoutes = listOf(TicTacToeNavGraphRoute),
        snackbarControllerProvider = KoinPlatform.getKoin()
            .get<SelectGameUserFlowProviders>()
            .snackbarControllerProvider
    )