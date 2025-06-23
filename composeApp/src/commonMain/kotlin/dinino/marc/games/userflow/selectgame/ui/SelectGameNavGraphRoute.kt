package dinino.marc.games.userflow.selectgame.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import dinino.marc.games.userflow.common.ui.SerializableUserFlowRoute
import dinino.marc.games.userflow.common.ui.SerializableUserFlowRoute.UserFlowNavGraphRoute
import kotlinx.serialization.Serializable

@Serializable
data object SelectGameNavGraphRoute : UserFlowNavGraphRoute {
    override val landingScreenRoute = SelectGameScreenRoute
    override val otherRoutes = emptyList<SerializableUserFlowRoute>()

    @Composable
    override fun invoke(modifier: Modifier) {
        TODO("Not yet implemented")
    }
}