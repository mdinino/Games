package dinino.marc.games.userflow.common.ui.route

import androidx.annotation.MainThread
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavOptionsBuilder
import dinino.marc.games.userflow.common.ui.route.SerializableUserFlowRoute.UserFlowNavGraphRoute
import dinino.marc.games.userflow.common.ui.route.SerializableUserFlowRoute.UserFlowScreenRoute
import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.serializer
import kotlin.reflect.KClass

/**
 * Navigate to a screen at this nav-graph level, adding it to the backstack
 */
@MainThread
fun NavController.navigateForwardTo(route: UserFlowScreenRoute) =
    navigate(route) {
        if (route is UserFlowScreenRoute.ClearUserFlowBackStack) {
            popSubGraph()
        }
    }

/**
 * Pops the backstack until the route class is reached
 */
@MainThread
fun NavController.navigateBackTo(routeClass: KClass<out UserFlowScreenRoute>) =
    popBackStack(route = routeClass, inclusive = false)

/**
 * Adds a nav graph then moves down the stack to that sub-nav-graph
 */
@MainThread
fun NavController.navigateDownTo(route: UserFlowNavGraphRoute) =
    navigate(route)

/**
 * Navigates up up to parent nav graphs until the nav graph is route is found.
 */
@OptIn(InternalSerializationApi::class)
@MainThread
inline fun <reified T: UserFlowNavGraphRoute> NavController.navigateUpTo(
    navGraphRoute: T,
    forceToLadingScreenRoute: Boolean = true
) {
    // this is how the navigate library converts from a class to a route name
    val navGraphRouteName = T::class.serializer().descriptor.serialName

    do { if (!navigateUp()) return }
    while(currentBackStackEntry?.parentRoute != navGraphRouteName)

    if (forceToLadingScreenRoute) {
        navigateForwardTo(navGraphRoute.landingScreenRoute)
    }
}

/**
 * Pops all backstack entries associated with the given nav graph before navigation is complete.
 * Stops as soon as the current backstack's parent is different than the given one.
 */
@MainThread
context(controller: NavController)
private fun NavOptionsBuilder.popSubGraph() {
    popUpTo(route = controller.firstRouteOfSubNavGraph ?: return) {
        inclusive = true
    }
}

private val NavController.firstRouteOfSubNavGraph: String?
    @MainThread get() {
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
    @MainThread get() = destination.route!!

// public because of navigateUpTo()
val NavBackStackEntry.parentRoute: String?
    @MainThread get() = destination.parent?.route