package dinino.marc.games.userflow.common.di

import androidx.compose.runtime.Composable
import dinino.marc.games.userflow.common.data.Repository
import dinino.marc.games.userflow.common.ui.nav.SnackbarController

interface UserFlowProviders {
    val localizedNameProvider: LocalizedNameProvider
    val snackbarControllerProvider: SnackbarControllerProvider

    /**
     * Every user-flow has a name associated with it that can be displayed to the user.
     */
    interface LocalizedNameProvider {
        @Composable fun provide(): String
    }

    /**
     * Every user-flow has a dedicated snackbar controller to show messages, errors, etc.
     * Each flow's snackbar controller controller is active in any screen that is part of that flow.
     */
    interface SnackbarControllerProvider {
        @Composable fun provide(): SnackbarController
    }
}