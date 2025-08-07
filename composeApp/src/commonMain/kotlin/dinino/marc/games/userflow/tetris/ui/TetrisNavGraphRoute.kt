package dinino.marc.games.userflow.tetris.ui

import dinino.marc.games.userflow.common.di.UserFlowProviders
import dinino.marc.games.userflow.common.ui.route.GameUserFlowNavGraphRouteImpl

import dinino.marc.games.userflow.tetris.di.TetrisUserFlowProviders
import dinino.marc.games.userflow.tetris.ui.screen.game.TetrisGameRoute
import dinino.marc.games.userflow.tetris.ui.screen.gameover.TetrisGameOverRoute
import dinino.marc.games.userflow.tetris.ui.screen.selectneworresumegame.TetrisSelectNewOrResumeGameRoute
import kotlinx.serialization.Serializable
import org.koin.mp.KoinPlatform

@Serializable
data object TetrisNavGraphRoute : GameUserFlowNavGraphRouteImpl() {
    override val selectNewOrResumeGameRoute = TetrisSelectNewOrResumeGameRoute
    override val gameRoute = TetrisGameRoute(newGame = true)
    override val gameOverRoute = TetrisGameOverRoute
    override val snackbarControllerProvider: UserFlowProviders.SnackbarControllerProvider
        get() = KoinPlatform.getKoin()
            .get<TetrisUserFlowProviders>()
            .snackbarControllerProvider
}