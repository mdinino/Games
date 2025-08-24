package dinino.marc.games.userflow.tictactoe.ui

import dinino.marc.games.userflow.common.ui.route.GameUserFlowNavGraphRoute
import dinino.marc.games.userflow.tictactoe.di.TicTacToeUserFlowProviders
import dinino.marc.games.userflow.tictactoe.ui.screen.game.TicTacToeGameRoute
import dinino.marc.games.userflow.tictactoe.ui.screen.gameover.TicTacToeGameOverRoute
import dinino.marc.games.userflow.tictactoe.ui.screen.selectneworresumegame.TicTacToeSelectNewOrResumeGameRoute
import kotlinx.serialization.Serializable
import org.koin.mp.KoinPlatform

@Serializable
data object TicTacToeNavGraphRoute : GameUserFlowNavGraphRoute() {
    override val selectNewOrResumeGameRoute = TicTacToeSelectNewOrResumeGameRoute
    override val gameRoute = TicTacToeGameRoute()
    override val gameOverRoute = TicTacToeGameOverRoute
    override val snackbarControllerProvider
        get() = KoinPlatform.getKoin()
            .get<TicTacToeUserFlowProviders>()
            .snackbarControllerProvider
}