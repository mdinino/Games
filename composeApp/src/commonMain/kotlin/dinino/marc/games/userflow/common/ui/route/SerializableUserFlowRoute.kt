package dinino.marc.games.userflow.common.ui.route

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavOptionsBuilder
import androidx.savedstate.SavedState

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
            when (route) {
                is UserFlowScreenRoute -> navigateToUserFlowScreenRoute(route)
                is UserFlowNavGraphRoute -> navigateToUserFlowNavGraphRoute(route)
            }

        private fun NavController.navigateToUserFlowScreenRoute(route: UserFlowScreenRoute) =
            navigate(route) {
                if (route is UserFlowScreenRoute.ClearUserFlowBackStack) {
                    popSubGraph()
                }
            }

        private fun NavController.navigateToUserFlowNavGraphRoute(route: UserFlowNavGraphRoute) {
            addOnDestinationChangedListener(
                object : NavController.OnDestinationChangedListener {
                    override fun onDestinationChanged(
                        controller: NavController,
                        destination: NavDestination,
                        arguments: SavedState?
                    ) {
                        controller.removeOnDestinationChangedListener(this)
                        if (route.landingScreenRoute is UserFlowScreenRoute.ClearUserFlowBackStack) {
                            popSubGraph()
                        }
                    }
                }
            )
            navigate(route.landingScreenRoute)
        }


        /**
         * Pops all backstack entries associated with the given nav graph before navigation is complete.
         * Stops as soon as the current backstack's parent is different than the given one.
         */
        context(controller: NavController)
        private fun NavOptionsBuilder.popSubGraph() {
            popUpTo(route = controller.firstRouteOfSubNavGraph ?: return) {
                inclusive = true
            }
        }

        private fun NavController.popSubGraph() {
            popBackStack(
                route = firstRouteOfSubNavGraph ?: return,
                inclusive = true
            )
        }

        private val NavController.firstRouteOfSubNavGraph: String?
            get() {
                val currentBackStackEntry = this.currentBackStackEntry ?: return null
                val currentParentRoute = currentBackStackEntry.parentRoute ?: return null

                val backStackEntries = visibleEntries.value

                var lastRouteOfSubGraph: String? = null
                for (backStackEntry in backStackEntries.reversed()) {
                    if (backStackEntry.parentRoute != currentParentRoute) break
                    lastRouteOfSubGraph = backStackEntry.route
                }

                return lastRouteOfSubGraph
            }
        private val NavBackStackEntry.route: String
            get() = destination.route!!

        private val NavBackStackEntry.parentRoute: String?
            get() = destination.parent?.route
    }
}