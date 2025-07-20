package dinino.marc.games.userflow.common.ui.route

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
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

    companion object {
        fun NavController.navigateToRoute(route: SerializableUserFlowRoute) =
            when(route) {
                is UserFlowScreenRoute -> navigate(
                    route = route,
                    clearNavGraphBackStack = (route is UserFlowScreenRoute.ClearUserFlowBackStack)
                )
                is UserFlowNavGraphRoute -> navigate(route)
            }

        /**
         * Navigates to given route.
         * @param clearNavGraphBackStack: If set to true, will clear the back stack
         * of the nav graph that the route is in before navigating, so that after navigation
         * this route is the only route on the nav graph's back stack.
         * Note that other nav graphs (including parent nav graphs) will remain unchanged.
         */
        private fun NavController.navigate(
            route: UserFlowScreenRoute,
            clearNavGraphBackStack: Boolean = false
        ) {
            navigate(route) {
                if (clearNavGraphBackStack) {
                    popUpTo(route = graph.findStartDestination().route!!) { inclusive = true }
                }

            }
        }
    }
}