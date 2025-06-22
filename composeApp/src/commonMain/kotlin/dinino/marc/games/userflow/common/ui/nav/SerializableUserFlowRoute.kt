package dinino.marc.games.userflow.common.ui.nav

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import dinino.marc.games.userflow.common.ui.SnackbarController


typealias ComposableFun = @Composable ()->Unit

/**
 * ALl implementing classes should be marked with @Serializable
 */
sealed interface SerializableUserFlowRoute {

    val composableCreator: @Composable (NavController, SnackbarController) -> ComposableFun

    interface UserFlowScreenRoute: SerializableUserFlowRoute {
        /**
         * Generates the composable version of this UserFlowScreenRoute
         */
        override val composableCreator: @Composable (NavController, SnackbarController) -> ComposableFun

        /**
         * A marker interface that denotes:
         * When navigating to here clear the backstack, the user cannot move back.
         */
        interface ClearBackStack
    }

    interface UserFlowNavGraphRoute: SerializableUserFlowRoute {
        /**
         * Generates the composable version of this NavGraph
         */
        override val composableCreator: @Composable (NavController, SnackbarController) -> ComposableFun

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