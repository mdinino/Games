package dinino.marc.games.userflow.tictactoe.ui.nav

import dinino.marc.games.userflow.common.ui.nav.SerializableUserFlowRoute.UserFlowNavGraphRoute
import dinino.marc.games.userflow.common.ui.nav.UserFlowNavGraphRouteImpl
import dinino.marc.games.userflow.tictactoe.di.TicTacToeUserFlowProviders
import kotlinx.serialization.Serializable
import org.koin.mp.KoinPlatform

@Serializable
data object TicTacToeNavGraphRoute :
   UserFlowNavGraphRoute by UserFlowNavGraphRouteImpl(
        landingScreenRoute = TicTacToeSelectNewOrResumeGameRoute,
        snackbarControllerProvider = KoinPlatform.getKoin()
            .get<TicTacToeUserFlowProviders>()
            .snackbarControllerProvider
    )