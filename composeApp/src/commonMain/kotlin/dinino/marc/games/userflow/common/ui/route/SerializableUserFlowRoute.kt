package dinino.marc.games.userflow.common.ui.route

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import dinino.marc.games.DataType

/**
 * ALl implementing classes should be marked with @Serializable
 */
sealed interface SerializableUserFlowRoute {
    interface UserFlowScreenRoute: SerializableUserFlowRoute {

        @Composable
        fun Screen(modifier: Modifier, navHostController: NavHostController)

        interface ClearBackStackUpToHere
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
                is UserFlowScreenRoute -> navigateToUserFlowScreenRoute(route)
                is UserFlowNavGraphRoute -> navigateToUserFlowNavGraphRoute(route)
            }

        private fun NavController.navigateToUserFlowScreenRoute(route: UserFlowScreenRoute) =
            navigate(route) {
                if (route is UserFlowScreenRoute.ClearBackStackUpToHere) {
                    popUpTo(route) { inclusive = false }
                }
            }

        private fun NavController.navigateToUserFlowNavGraphRoute(route: UserFlowNavGraphRoute) =
            route.landingScreenRoute.let { finalDestination ->
                navigate(finalDestination) {
                    if (finalDestination is UserFlowScreenRoute.ClearBackStackUpToHere) {
                        popUpTo(finalDestination) { inclusive = false }
                    }
                }
            }
    }
}