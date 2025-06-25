package dinino.marc.games.userflow.common.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

/**
 * ALl implementing classes should be marked with @Serializable
 */
sealed interface SerializableUserFlowRoute{
    interface UserFlowScreenRoute: SerializableUserFlowRoute {

        @Composable fun Screen()

        /**
         * A marker interface that denotes:
         * When navigating to here clear the backstack, the user cannot move back.
         */
        interface ClearBackStack
    }

    interface UserFlowNavGraphRoute: SerializableUserFlowRoute {

        @Composable fun Navigation()

        /**
         * The first route screen of the NavGraph
         */
        val landingScreenRoute: UserFlowScreenRoute

        /**
         * Other routes (either screen or NavGraphs) in the NavGraph
         */
        val otherRoutes: List<SerializableUserFlowRoute>
    }
}