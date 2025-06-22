package dinino.marc.games.userflow.selectgame.ui.nav

import dinino.marc.games.userflow.common.ui.SnackbarController
import dinino.marc.games.userflow.common.ui.nav.SerializableUserFlowRoute.UserFlowNavGraphRoute
import dinino.marc.games.userflow.common.ui.nav.SerializableUserFlowRoute.UserFlowScreenRoute
import dinino.marc.games.userflow.common.ui.nav.UserFlow
import dinino.marc.games.userflow.selectgame.di.selectGameSnackbarControllerQualifier
import dinino.marc.games.userflow.selectgame.di.selectGameUserFLowModule
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

object SelectGameUserFlow: UserFlow, KoinComponent {
    override val koinModule = selectGameUserFLowModule

    override val snackbarController
        by inject<SnackbarController>(selectGameSnackbarControllerQualifier)

    override val navGraph = object : UserFlowNavGraphRoute {
        override val landingScreenRoute = SelectGameScreenRoute
        override val otherRoutes: List<UserFlowScreenRoute> = emptyList()
    }
}