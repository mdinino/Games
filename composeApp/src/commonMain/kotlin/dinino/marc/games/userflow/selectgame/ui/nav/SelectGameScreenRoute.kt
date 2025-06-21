package dinino.marc.games.userflow.selectgame.ui.nav

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import dinino.marc.games.userflow.common.ui.SnackbarController
import dinino.marc.games.userflow.common.ui.nav.SerializableUserFlowRoute
import dinino.marc.games.userflow.selectgame.ui.SelectGameScreenRoot
import kotlinx.serialization.Serializable

@Serializable
object SelectGameScreenRoute: SerializableUserFlowRoute.UserFlowScreenRoute {

    @Composable
    override fun Screen(navController: NavController, snackbarController: SnackbarController) {
        SelectGameScreenRoot(
            navController = navController,
            snackbarController = snackbarController
        )
    }
}