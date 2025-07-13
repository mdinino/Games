package dinino.marc.games.userflow.tictactoe.ui.screen.selectneworresumegame

import dinino.marc.games.userflow.common.ui.route.ContentWithAppBarScreenRoute
import dinino.marc.games.userflow.common.ui.route.SerializableUserFlowRoute
import dinino.marc.games.userflow.tictactoe.di.TicTacToeUserFlowProviders
import kotlinx.serialization.Serializable
import org.koin.mp.KoinPlatform

@Serializable
data object TicTacToeSelectNewOrResumeGameRoute :
    SerializableUserFlowRoute.UserFlowScreenRoute by ContentWithAppBarScreenRoute(
        localizedTitleProvider = KoinPlatform.getKoin()
            .get<TicTacToeUserFlowProviders>().localizedNameProvider,
        content = { modifier, navHostController ->
            TicTacToeSelectNewOrResumeGameScreen(
                modifier = modifier,
                navHostController = navHostController
            )
        }
    ), SerializableUserFlowRoute.UserFlowScreenRoute.ClearBackStackUpToHere