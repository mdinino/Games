package dinino.marc.games.userflow.tictactoe.ui

import dinino.marc.games.userflow.common.ui.route.GameUserFlowNavGraphRouteImpl
import dinino.marc.games.userflow.common.ui.route.SerializableUserFlowRoute.UserFlowNavGraphRoute
import dinino.marc.games.userflow.tictactoe.di.TicTacToeUserFlowProviders
import dinino.marc.games.userflow.tictactoe.ui.screen.gameover.TicTacToeGameOverRoute
import dinino.marc.games.userflow.tictactoe.ui.screen.selectneworresumegame.TicTacToeSelectNewOrResumeGameRoute
import kotlinx.serialization.Serializable
import org.koin.mp.KoinPlatform

@Serializable
data object TicTacToeNavGraphRoute :
   UserFlowNavGraphRoute by GameUserFlowNavGraphRouteImpl(
       selectNewOrResumeGameRoute = TicTacToeSelectNewOrResumeGameRoute,
       gameOverRoute = TicTacToeGameOverRoute,
       snackbarControllerProvider = KoinPlatform.getKoin()
           .get<TicTacToeUserFlowProviders>()
           .snackbarControllerProvider
   )