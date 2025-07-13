package dinino.marc.games.userflow.tictactoe.ui

import dinino.marc.games.userflow.common.ui.route.SerializableUserFlowRoute.UserFlowNavGraphRoute
import dinino.marc.games.userflow.common.ui.route.UserFlowNavGraphRouteImpl
import dinino.marc.games.userflow.tictactoe.di.TicTacToeUserFlowProviders
import dinino.marc.games.userflow.tictactoe.ui.screen.selectneworresumegame.TicTacToeSelectNewOrResumeGameRoute
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