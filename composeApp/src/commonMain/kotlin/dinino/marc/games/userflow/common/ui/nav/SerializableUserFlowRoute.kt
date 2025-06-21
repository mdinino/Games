package dinino.marc.games.userflow.common.ui.nav

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController



/**
 * ALl implementing classes should be marked with @Serializable
 */
sealed interface SerializableUserFlowRoute {

    @Composable
    fun Composable(
        modifier: Modifier = Modifier,
        localizedFlowName: String,
        navController: NavController
    )

    interface UserFlowNavGraphRoute: SerializableUserFlowRoute

    interface UserFlowScreenRoute: SerializableUserFlowRoute {
        /**
         * A marker interface that denotes:
         * When navigating to here clear the backstack, the user cannot move back.
         */
        interface ClearBackStack

    }
}