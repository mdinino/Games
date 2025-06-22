package dinino.marc.games.userflow.common.ui.nav

import dinino.marc.games.userflow.common.ui.SnackbarController
import dinino.marc.games.userflow.common.ui.nav.SerializableUserFlowRoute.UserFlowNavGraphRoute
import org.koin.core.module.Module

/**
 * A definition for a user-flow. Ensures commonalities/symmetries between different flows.
 */
interface UserFlow {
    /**
     * To register dependencies with Koin DI.
     */
    val koinModule: Module

    /**
     * Every user-flow has a dedicated snackbar controller to show messages, errors, etc.
     * Each flow's snackbar controller controller is active in any screen that is part of that flow.
     */
    val snackbarController: SnackbarController

    /**
     * Nav graph of this user-flow
     */
    val navGraph: UserFlowNavGraphRoute
}