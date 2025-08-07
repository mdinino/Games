package dinino.marc.games.userflow.common.ui.route

import dinino.marc.games.userflow.common.di.UserFlowProviders
import kotlinx.serialization.Serializable

@Serializable
abstract class GameUserFlowNavGraphRouteImpl(): UserFlowNavGraphRoute() {
    protected abstract val selectNewOrResumeGameRoute: SelectNewOrResumeGameRoute
    protected abstract val gameRoute: GameRoute
    protected abstract val gameOverRoute: GameOverRoute

    final override val landingScreenRoute: SerializableUserFlowRoute.UserFlowScreenRoute
        get() = selectNewOrResumeGameRoute

    final override val otherRoutes: List<SerializableUserFlowRoute> = listOf(gameRoute, gameOverRoute)
}

interface SelectNewOrResumeGameRoute: SerializableUserFlowRoute.UserFlowScreenRoute,
    SerializableUserFlowRoute.UserFlowScreenRoute.ClearUserFlowBackStack

interface GameRoute: SerializableUserFlowRoute.UserFlowScreenRoute {
    val newGame: Boolean
}

interface GameOverRoute: SerializableUserFlowRoute.UserFlowScreenRoute