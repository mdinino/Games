package dinino.marc.games.userflow.common.ui.route

import dinino.marc.games.userflow.common.di.UserFlowProviders

class GameUserFlowNavGraphRouteImpl(
    selectNewOrResumeGameRoute: SerializableUserFlowRoute.UserFlowScreenRoute,
    gameOverRoute: SerializableUserFlowRoute.UserFlowScreenRoute,
    private val snackbarControllerProvider: UserFlowProviders.SnackbarControllerProvider,
): SerializableUserFlowRoute.UserFlowNavGraphRoute by UserFlowNavGraphRouteImpl(
    landingScreenRoute = selectNewOrResumeGameRoute,
    otherRoutes = listOf(gameOverRoute),
    snackbarControllerProvider = snackbarControllerProvider
)