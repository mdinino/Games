package dinino.marc.games.userflow.tictactoe.ui.nav

import dinino.marc.games.userflow.common.ui.nav.ContentWithAppBarScreenRoute
import dinino.marc.games.userflow.common.ui.nav.SerializableUserFlowRoute.UserFlowScreenRoute
import dinino.marc.games.userflow.common.ui.nav.SerializableUserFlowRoute.UserFlowScreenRoute.ClearBackStack
import dinino.marc.games.userflow.tictactoe.di.TicTacToeUserFlowProviders
import dinino.marc.games.userflow.tictactoe.ui.screen.TicTacToeSelectNewOrResumeGameScreen
import kotlinx.serialization.Serializable
import org.koin.mp.KoinPlatform

@Serializable
data object TicTacToeSelectNewOrResumeGameRoute :
    UserFlowScreenRoute by ContentWithAppBarScreenRoute(
        localizedTitleProvider = KoinPlatform.getKoin()
            .get<TicTacToeUserFlowProviders>().localizedNameProvider,
        content = { modifier ->
            TicTacToeSelectNewOrResumeGameScreen(modifier = modifier)
        }
    ),
    ClearBackStack