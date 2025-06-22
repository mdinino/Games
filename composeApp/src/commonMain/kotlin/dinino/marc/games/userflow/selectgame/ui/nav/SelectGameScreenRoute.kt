package dinino.marc.games.userflow.selectgame.ui.nav

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import dinino.marc.games.userflow.common.ui.SnackbarController
import dinino.marc.games.userflow.common.ui.nav.ComposableFun
import dinino.marc.games.userflow.common.ui.nav.SerializableUserFlowRoute
import dinino.marc.games.userflow.common.ui.nav.UserFlowWithAppBarScreenFactory
import dinino.marc.games.userflow.selectgame.ui.SelectGameScreenRoot
import kotlinx.serialization.Serializable

@Serializable
data class SelectGameScreenRoute(
    override val userFlowTitle: String
): SerializableUserFlowRoute.UserFlowScreenRoute {
    override val screenCreator
        get() = UserFlowWithAppBarScreenFactory(
            localizedUserFlowTitle = userFlowTitle,
            screenContentsCreator = { navController, snackbarController ->
                { SelectGameScreenRoot(
                    navController = navController,
                    snackbarController = snackbarController)
                }
            }
        )
}