package dinino.marc.games.userflow.selectgame.ui

import dinino.marc.games.userflow.common.ui.route.SerializableUserFlowRoute
import dinino.marc.games.userflow.common.ui.route.UserFlowNavGraphRoute
import dinino.marc.games.userflow.selectgame.di.SelectGameUserFlowProviders
import dinino.marc.games.userflow.tetris.ui.TetrisNavGraphRoute
import dinino.marc.games.userflow.tictactoe.ui.TicTacToeNavGraphRoute
import kotlinx.serialization.Serializable
import org.koin.mp.KoinPlatform

@Serializable
data object SelectGameNavGraphRoute : UserFlowNavGraphRoute() {
    override val landingScreenRoute
        get() = SelectGameScreenRoute
    override val otherRoutes: List<SerializableUserFlowRoute>
        get() = listOf(TicTacToeNavGraphRoute, TetrisNavGraphRoute)
    override val snackbarControllerProvider
        get() = KoinPlatform.getKoin()
            .get<SelectGameUserFlowProviders>()
                .snackbarControllerProvider
}