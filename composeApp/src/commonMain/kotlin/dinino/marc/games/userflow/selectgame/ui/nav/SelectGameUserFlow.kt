package dinino.marc.games.userflow.selectgame.ui.nav

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import dinino.marc.games.userflow.common.ui.SnackbarController
import dinino.marc.games.userflow.common.ui.SnackbarController.Companion.ObserveEffect
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
        override val landingRoute: UserFlowScreenRoute = SelectGameScreenRoute
        override val otherRoutes: List<UserFlowScreenRoute> = emptyList()
    }

    override val navGraphComposableBuilder: (UserFlowNavGraphRoute) -> @Composable (() -> Unit)
        get() = TODO("Not yet implemented")
}

@Composable
private fun UserFlowScaffold(
    modifier: Modifier = Modifier.Companion,
    content: @Composable (PaddingValues) -> Unit
) {
    val snackbarHostState = remember { SnackbarHostState() }
    snackbarController.events.ObserveEffect(snackbarHostState)

    Scaffold(
        modifier = modifier,
        snackbarHost = { SnackbarHost(snackbarHostState) },
        content = content
    )
}