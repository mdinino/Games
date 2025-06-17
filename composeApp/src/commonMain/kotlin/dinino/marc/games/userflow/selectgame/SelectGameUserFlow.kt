package dinino.marc.games.userflow.selectgame

import androidx.compose.runtime.Composable
import dinino.marc.games.common.ui.SnackbarController
import dinino.marc.games.userflow.common.UserFlow
import dinino.marc.games.userflow.selectgame.di.selectGameSnackbarControllerQualifier
import dinino.marc.games.userflow.selectgame.di.selectGameUserFLowModule
import org.koin.compose.koinInject

data object SelectGameUserFlow: UserFlow {
    override val koinModule
        get() = selectGameUserFLowModule

    @get:Composable
    override val dedicatedSnackbarController
        get() = koinInject<SnackbarController>(selectGameSnackbarControllerQualifier)
}