package dinino.marc.games.userflow.selectgame.di

import androidx.compose.runtime.Composable
import dinino.marc.games.userflow.common.di.UserFlowProviders
import dinino.marc.games.userflow.common.ui.SnackbarController

class SelectGameUserFlowProviders(
    override val localizedNameProvider: @Composable (() -> String),
    override val snackbarControllerProvider: () -> SnackbarController
): UserFlowProviders