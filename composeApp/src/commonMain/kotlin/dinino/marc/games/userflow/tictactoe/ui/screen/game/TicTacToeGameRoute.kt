package dinino.marc.games.userflow.tictactoe.ui.screen.game

import dinino.marc.games.userflow.common.ui.route.ContentWithAppBarScreenRoute
import dinino.marc.games.userflow.common.ui.route.SerializableUserFlowRoute
import dinino.marc.games.userflow.tictactoe.di.TicTacToeUserFlowProviders
import kotlinx.serialization.Serializable
import org.koin.mp.KoinPlatform

@Serializable
data object TicTacToeNewGameRoute :
    SerializableUserFlowRoute.UserFlowScreenRoute by ContentWithAppBarScreenRoute(
        localizedTitleProvider = KoinPlatform.getKoin()
            .get<TicTacToeUserFlowProviders>().localizedNameProvider,
        content = { modifier, navHostController ->
            TicTacToeNewGameScreen(
                modifier = modifier,
                navHostController = navHostController,
            )
        }
    )

@Serializable
data object TicTacToeResumeGameRoute :
    SerializableUserFlowRoute.UserFlowScreenRoute by ContentWithAppBarScreenRoute(
        localizedTitleProvider = KoinPlatform.getKoin()
            .get<TicTacToeUserFlowProviders>().localizedNameProvider,
        content = { modifier, navHostController ->
            TicTacToeResumeGameScreen(
                modifier = modifier,
                navHostController = navHostController,
            )
        }
    )