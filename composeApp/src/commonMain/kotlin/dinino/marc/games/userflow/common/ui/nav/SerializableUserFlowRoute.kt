package dinino.marc.games.userflow.common.ui.nav

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import dinino.marc.games.userflow.common.ui.SnackbarController

typealias ComposableFun = @Composable ()->Unit

/**
 * ALl implementing classes should be marked with @Serializable
 */
sealed interface SerializableUserFlowRoute {

    interface UserFlowScreenRoute: SerializableUserFlowRoute {

        @Composable
        fun Screen(navController: NavController, snackbarController: SnackbarController)

        /**
         * A marker interface that denotes:
         * When navigating to here clear the backstack, the user cannot move back.
         */
        interface ClearBackStack
    }

    interface UserFlowNavGraphRoute: SerializableUserFlowRoute {
        val landingRoute: UserFlowScreenRoute
        val otherRoutes: List<UserFlowScreenRoute>
    }
}