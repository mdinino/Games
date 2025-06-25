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
import dinino.marc.games.userflow.selectgame.di.SelectGameUserFlowProviders.SnackbarControllerProvider
import org.koin.mp.KoinPlatform.getKoin

class UserFlowNavGraphRouteImpl(
    override val landingScreenRoute: UserFlowScreenRoute,
    override val otherRoutes: List<SerializableUserFlowRoute> = emptyList(),
    private val snackbarController: SnackbarControllerProvider,
    private val navHostController: @Composable ()->NavHostController = appNavHostController(),
): UserFlowNavGraphRoute {


    @Suppress("ComposableNaming")
    @Composable
    override fun invoke(modifier: Modifier) {
        NavGraphWithSnackbarController(
            modifier = modifier,
            landingScreenRoute = landingScreenRoute,
            otherRoutes = otherRoutes,
            snackbarController = snackbarController(),
            navHostController = navHostController()
        )
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
                            serializableUserFlowRouteComposable<SerializableUserFlowRoute>(
                                navHostController = navHostController,
                                route = route
                            )
                        }
                }
            }
        }

        private fun <T: SerializableUserFlowRoute> NavGraphBuilder.
            serializableUserFlowRouteComposable(navHostController: NavHostController, route: T) {
            when(route) {
                is UserFlowScreenRoute ->
                    userFlowScreenRouteComposable<UserFlowScreenRoute>(
                        navHostController = navHostController,
                        route = route
                    )
                is UserFlowNavGraphRoute ->
                    userFlowNavGraphRouteComposable<UserFlowNavGraphRoute>(
                        navHostController = navHostController,
                        route = route
                    )

            }
        }

        private fun <T: UserFlowNavGraphRoute> NavGraphBuilder.
            userFlowNavGraphRouteComposable(navHostController: NavHostController, route: T) {
            navigation(
                route = route::class,
                startDestination = route.landingScreenRoute::class
            ) {
                route
                    .otherRoutes
                    .forEach {
                        serializableUserFlowRouteComposable(
                            navHostController = navHostController,
                            route = it
                        )
                    }
            }
        }

        private fun <T: UserFlowScreenRoute> NavGraphBuilder.
            userFlowScreenRouteComposable(navHostController: NavHostController, route: T) {
            composable(route = route::class) { backStackEntry ->
                backStackEntry.toRoute<T>(route::class)(Modifier)
                if (route is UserFlowScreenRoute.ClearBackStack) {
                    navHostController.popBackStack(route = route::class, inclusive = false)
                }
            }
        }

        private fun appNavHostController() =
            getKoin().get<AppProviders>().navHostControllerProvider
    }
}