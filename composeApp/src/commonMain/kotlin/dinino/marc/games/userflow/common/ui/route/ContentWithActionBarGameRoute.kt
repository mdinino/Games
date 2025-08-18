package dinino.marc.games.userflow.common.ui.route

import kotlinx.serialization.Serializable

@Serializable
abstract class ContentWithActionBarGameRoute() : ContentWithActionBarScreenRoute(),
    GameUserFlowNavGraphRoute.GameRoute
{
    final override val showMenuIcon: Boolean
        get() = true

    final override val dialogs: List<SerializableUserFlowRoute.UserFlowDialogRoute>
        get() = listOf(pauseDialogRoute)
}