package dinino.marc.games.userflow.selectgame.ui.nav

import dinino.marc.games.userflow.common.ui.nav.SerializableUserFlowRoute
import dinino.marc.games.userflow.common.ui.nav.UserFlowWithAppBarScreenFactory
import dinino.marc.games.userflow.selectgame.ui.SelectGameScreenRoot
import games.composeapp.generated.resources.Res
import games.composeapp.generated.resources.userflow_select_game
import kotlinx.serialization.Serializable
import org.jetbrains.compose.resources.stringResource

@Serializable
data object SelectGameScreenRoute: SerializableUserFlowRoute.UserFlowScreenRoute,
    SerializableUserFlowRoute.UserFlowScreenRoute.ClearBackStack {
    override val screenCreator = UserFlowWithAppBarScreenFactory(
            userFlowTitle = { stringResource(Res.string.userflow_select_game) },
            screenContentsCreator = { navController, snackbarController ->
                { SelectGameScreenRoot(
                    navController = navController,
                    snackbarController = snackbarController)
                }
            }
        )
}