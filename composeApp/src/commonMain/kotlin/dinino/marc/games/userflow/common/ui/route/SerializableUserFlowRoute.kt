package dinino.marc.games.userflow.common.ui.route

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController

/**
 * ALl implementing classes should be marked with @Serializable
 */
sealed interface SerializableUserFlowRoute {
    interface UserFlowScreenRoute: SerializableUserFlowRoute {

        @Composable
        fun Screen(modifier: Modifier, navHostController: NavHostController)

        /**
         * When navigating to UserFlowScreenRoutes that inherit from this market interface
         * all items in the current user flow will be cleared before navigating.
         * Parent nav graphs will be preserved
         */
        interface ClearUserFlowBackStack
    }

    interface UserFlowNavGraphRoute: SerializableUserFlowRoute {

        @Composable
        fun Navigation(modifier: Modifier, navHostController: NavHostController)

        /**
         * The first route screen of the NavGraph
         */
        val landingScreenRoute: UserFlowScreenRoute

        /**
         * Other routes (either screen or NavGraphs) in the NavGraph
         */
        val otherRoutes: List<SerializableUserFlowRoute>
    }
}
