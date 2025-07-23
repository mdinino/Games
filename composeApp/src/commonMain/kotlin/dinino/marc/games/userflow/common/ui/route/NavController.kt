package dinino.marc.games.userflow.common.ui.route

import androidx.annotation.MainThread
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavOptionsBuilder
import dinino.marc.games.serialization.DefaultToJsonStringConverter
import dinino.marc.games.serialization.ToJsonStringConverter
import dinino.marc.games.userflow.common.ui.route.SerializableUserFlowRoute.UserFlowNavGraphRoute
import dinino.marc.games.userflow.common.ui.route.SerializableUserFlowRoute.UserFlowScreenRoute
import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.serializer

/**
 * Navigate to a screen at this nav-graph level.
 */
@MainThread
fun NavController.navigateTo(route: UserFlowScreenRoute) =
    navigate(route) {
        if (route is UserFlowScreenRoute.ClearUserFlowBackStack) {
            popSubGraph()
        }
    }

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
    route: T,
    routeJsonConverter: ToJsonStringConverter<T> =
        DefaultToJsonStringConverter(
            serializer = T::class.serializer()
        ),
    forceToLadingScreenRoute: Boolean = true
) {
    val serializedRoute: String = routeJsonConverter.convertToJson(route)
    var success: Boolean
    var currentBackStackEntry: NavBackStackEntry?

    do {
        success = navigateUp()
        currentBackStackEntry = this.currentBackStackEntry
    } while(
        success
        && currentBackStackEntry?.parentRoute != serializedRoute
    )

    if (!success) return
    if (forceToLadingScreenRoute) {
        navigateTo(route.landingScreenRoute)
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