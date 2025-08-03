package dinino.marc.games.userflow.common.ui.route

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
import dinino.marc.games.userflow.common.di.UserFlowProviders
import dinino.marc.games.userflow.common.ui.SnackbarController
import dinino.marc.games.userflow.common.ui.SnackbarController.Companion.ObserveEffect

class UserFlowNavGraphRouteImpl(
    override val landingScreenRoute: SerializableUserFlowRoute.UserFlowScreenRoute,
    override val otherRoutes: List<SerializableUserFlowRoute> = emptyList(),
    private val snackbarControllerProvider: UserFlowProviders.SnackbarControllerProvider,
): SerializableUserFlowRoute.UserFlowNavGraphRoute {

    @Composable
    override fun Navigation(modifier: Modifier, navHostController: NavHostController) {
        NavGraphWithSnackbarController(
            modifier = modifier,
            landingScreenRoute = landingScreenRoute,
            otherRoutes = otherRoutes,
            snackbarController = snackbarControllerProvider.provide(),
            navHostController = navHostController
        )
    }

    companion object {
        @Composable
        private fun NavGraphWithSnackbarController(
            modifier: Modifier = Modifier,
            landingScreenRoute: SerializableUserFlowRoute.UserFlowScreenRoute,
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
                    serializableUserFlowRouteComposable(
                        navHostController = navHostController,
                        route = landingScreenRoute
                    )

                    otherRoutes
                        .forEach { route: SerializableUserFlowRoute ->
                            serializableUserFlowRouteComposable(
                                navHostController = navHostController,
                                route = route
                            )
                        }
                }
            }
        }


        private fun <T: SerializableUserFlowRoute> NavGraphBuilder.
            serializableUserFlowRouteComposable(
            navHostController: NavHostController,
            route: T
        ) {
            when(route) {
                is SerializableUserFlowRoute.UserFlowScreenRoute ->
                    userFlowScreenRouteComposable<SerializableUserFlowRoute.UserFlowScreenRoute>(
                        navHostController = navHostController,
                        route = route
                    )
                is SerializableUserFlowRoute.UserFlowNavGraphRoute ->
                    userFlowNavGraphRouteComposable<SerializableUserFlowRoute.UserFlowNavGraphRoute>(
                        navHostController = navHostController,
                        route = route
                    )

            }
        }

        private fun <T: SerializableUserFlowRoute.UserFlowNavGraphRoute> NavGraphBuilder.
            userFlowNavGraphRouteComposable(
            navHostController: NavHostController,
            route: T
        ) {
            navigation(
                route = route::class,
                startDestination = route.landingScreenRoute::class
            ) {
                serializableUserFlowRouteComposable(
                    navHostController = navHostController,
                    route = route.landingScreenRoute
                )

                route.otherRoutes
                    .forEach {
                        serializableUserFlowRouteComposable(
                            navHostController = navHostController,
                            route = it
                        )
                    }
            }
        }

        private fun <T: SerializableUserFlowRoute.UserFlowScreenRoute> NavGraphBuilder.
            userFlowScreenRouteComposable(
            navHostController: NavHostController,
            route: T
        ) {
            composable(route = route::class) { backStackEntry ->
                backStackEntry.toRoute<T>(route::class).Screen(Modifier.Companion, navHostController)
            }
        }
    }
}