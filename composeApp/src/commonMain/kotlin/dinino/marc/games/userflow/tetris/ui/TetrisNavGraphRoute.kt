package dinino.marc.games.userflow.tetris.ui

import dinino.marc.games.userflow.common.ui.route.GameUserFlowNavGraphRouteImpl
import dinino.marc.games.userflow.common.ui.route.SerializableUserFlowRoute.UserFlowNavGraphRoute
import dinino.marc.games.userflow.tetris.di.TetrisUserFlowProviders
import dinino.marc.games.userflow.tetris.ui.screen.game.TetrisGameRoute
import dinino.marc.games.userflow.tetris.ui.screen.gameover.TetrisGameOverRoute
import dinino.marc.games.userflow.tetris.ui.screen.selectneworresumegame.TetrisSelectNewOrResumeGameRoute
import kotlinx.serialization.Serializable
import org.koin.mp.KoinPlatform

@Serializable
data object TetrisNavGraphRoute :
    UserFlowNavGraphRoute by GameUserFlowNavGraphRouteImpl(
        selectNewOrResumeGameRoute = TetrisSelectNewOrResumeGameRoute,
        gameRoute = TetrisGameRoute(newGame = true),
        gameOverRoute = TetrisGameOverRoute,
        snackbarControllerProvider = KoinPlatform.getKoin()
            .get<TetrisUserFlowProviders>()
            .snackbarControllerProvider
    )