package dinino.marc.games.userflow.common.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import androidx.navigation.toRoute
import dinino.marc.games.app.di.AppProviders
import dinino.marc.games.userflow.common.ui.SerializableUserFlowRoute.UserFlowScreenRoute
import dinino.marc.games.userflow.common.ui.SerializableUserFlowRoute.UserFlowNavGraphRoute
import dinino.marc.games.userflow.common.ui.SnackbarController.Companion.ObserveEffect
import org.koin.mp.KoinPlatform.getKoin
import kotlin.reflect.KClass
import kotlin.reflect.cast

class UserFlowNavGraphRouteImpl(
    override val landingScreenRoute: UserFlowScreenRoute,
    override val otherRoutes: List<SerializableUserFlowRoute> = emptyList(),
    private val snackbarController: @Composable ()->SnackbarController,
    private val navHostController: @Composable ()->NavHostController = appNavHostController(),
): UserFlowNavGraphRoute {


    @Suppress("ComposableNaming")
    @Composable
    override fun invoke(modifier: Modifier) {
        TODO("Not yet implemented")
    }

    companion object {

        @Composable
        private fun NavGraphWithSnackbarController(
            modifier: Modifier = Modifier,
            landingScreenRoute: UserFlowScreenRoute,
            otherRoutes: List<SerializableUserFlowRoute> = emptyList(),
            snackbarController: SnackbarController,
            navHostController: NavHostController
        ) {
            val snackbarHostState = remember { SnackbarHostState() }
            snackbarController.events.ObserveEffect(snackbarHostState = snackbarHostState)

            Scaffold(
                modifier = modifier,
                snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
            ) { innerPadding ->
                NavHost(
                    navController = navHostController,
                    startDestination = landingScreenRoute,
                    modifier = Modifier.padding(innerPadding),
                ) {
                    (otherRoutes + landingScreenRoute)
                        .forEach { route: SerializableUserFlowRoute ->
                            composable {  }
                        }
                }
            }
        }

        @Composable
        private inline fun <reified T: SerializableUserFlowRoute> NavGraphBuilder.
            SerializableUserFlowRouteComposable(
                navHostController: NavHostController,
                routeClass: KClass<T>
        ) {
                val x = T::class.

            when(T::class) {
                is UserFlowNavGraphRoute::class -> ()
                else
            }
        }

        @Composable
        private inline fun <reified T: UserFlowNavGraphRoute> NavGraphBuilder.
            UserFlowNavGraphRouteComposable(
                navHostController: NavHostController,
                routeClass: KClass<T>
        ) {

        }

        @Composable
        private inline fun <reified T: UserFlowScreenRoute> NavGraphBuilder.
            UserFlowScreenRouteComposable(
                navHostController: NavHostController,
                routeClass: KClass<T>
        ) {
            composable(route = routeClass) { backStackEntry ->
                val route: T = backStackEntry.toRoute<T>()
                route(Modifier)
                if (route is UserFlowScreenRoute.ClearBackStack) {
                    navHostController.popBackStack(route = routeClass, inclusive = false)
                }
            }
        }

        private fun appNavHostController() =
            getKoin().get<AppProviders>().navHostControllerProvider
    }
}