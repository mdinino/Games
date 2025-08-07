package dinino.marc.games.userflow.common.ui.route

import dinino.marc.games.userflow.common.di.UserFlowProviders

class GameUserFlowNavGraphRouteImpl(
    selectNewOrResumeGameRoute: SelectNewOrResumeGameRoute,
    gameRoute: GameRoute,
    gameOverRoute: GameOverRoute,
    private val snackbarControllerProvider: UserFlowProviders.SnackbarControllerProvider,
): SerializableUserFlowRoute.UserFlowNavGraphRoute by UserFlowNavGraphRouteImpl(
    landingScreenRoute = selectNewOrResumeGameRoute,
    otherRoutes = listOf(gameRoute, gameOverRoute),
    snackbarControllerProvider = snackbarControllerProvider
)

interface SelectNewOrResumeGameRoute: SerializableUserFlowRoute.UserFlowScreenRoute,
    SerializableUserFlowRoute.UserFlowScreenRoute.ClearUserFlowBackStack

interface GameRoute: SerializableUserFlowRoute.UserFlowScreenRoute {
    val newGame: Boolean
}

interface GameOverRoute: SerializableUserFlowRoute.UserFlowScreenRoute