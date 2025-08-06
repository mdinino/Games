package dinino.marc.games.userflow.tetris.ui.screen.game

import dinino.marc.games.userflow.common.ui.route.ContentWithAppBarScreenRoute
import dinino.marc.games.userflow.common.ui.route.SerializableUserFlowRoute
import dinino.marc.games.userflow.tictactoe.di.TicTacToeUserFlowProviders
import kotlinx.serialization.Serializable
import org.koin.mp.KoinPlatform

@Serializable
data object TetrisNewGameRoute :
    SerializableUserFlowRoute.UserFlowScreenRoute by ContentWithAppBarScreenRoute(
        localizedTitleProvider = KoinPlatform.getKoin()
            .get<TicTacToeUserFlowProviders>().localizedNameProvider,
        content = { modifier, navHostController ->
            TetrisNewGameScreen(
                modifier = modifier,
                navHostController = navHostController)
        }
    )

@Serializable
data object TetrisResumeGameRoute :
    SerializableUserFlowRoute.UserFlowScreenRoute by ContentWithAppBarScreenRoute(
        localizedTitleProvider = KoinPlatform.getKoin()
            .get<TicTacToeUserFlowProviders>().localizedNameProvider,
        content = { modifier, navHostController ->
            TetrisResumeGameScreen(
                modifier = modifier,
                navHostController = navHostController)
        }
    )