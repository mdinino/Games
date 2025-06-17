package dinino.marc.games.userflow.common

import androidx.compose.runtime.Composable
import dinino.marc.games.common.ui.SnackbarController
import org.koin.core.module.Module

/**
 * A definition for a user-flow. Ensures commonalities/symmetries between different flows.
 */
interface UserFlow {
    /**
     * To register dependencies with Koin DI
     */
    val koinModule: Module

    /**
     * Every user-flow has a dedicated snackbar controller to show messages, errors, etc.
     * Each flow's snackbar controller controller is active in any screen that is part of that flow.
     */
    @get:Composable
    val dedicatedSnackbarController: SnackbarController
}