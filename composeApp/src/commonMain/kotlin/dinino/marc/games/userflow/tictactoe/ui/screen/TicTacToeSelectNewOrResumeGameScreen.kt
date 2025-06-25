package dinino.marc.games.userflow.tictactoe.ui.screen

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import dinino.marc.games.app.di.AppProviders
import dinino.marc.games.userflow.common.ui.layout.SelectNewOrResumeGameLayout
import dinino.marc.games.userflow.common.ui.nav.ObserveOneTimeEventEffect
import dinino.marc.games.userflow.common.ui.nav.SnackbarController
import dinino.marc.games.userflow.tictactoe.di.TicTacToeUserFlowProviders
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import org.koin.compose.koinInject

@Composable
fun TicTacToeSelectNewOrResumeGameScreen(
    modifier: Modifier = Modifier,
    navHostController: NavHostController = defaultNavHostController,
    snackbarController: SnackbarController = defaultSnackbarController,
    oneTimeEvents: Flow<Any> = emptyFlow(),
    onNewGameOrNullIfDisabled : (()->Unit)? = {},
    onResumeGameOrNullIfDisabled : (()->Unit)? = null,
) {
    oneTimeEvents.ObserveEffect(
        navHostController = navHostController,
        snackbarController = snackbarController
    )

    SelectNewOrResumeGameLayout(
        modifier = modifier,
        onNewGameOrNullIfDisabled = onNewGameOrNullIfDisabled,
        onResumeGameOrNullIfDisabled = onResumeGameOrNullIfDisabled
    )
}

@Composable
private fun Flow<Any>.ObserveEffect(
    navHostController: NavHostController = defaultNavHostController,
    snackbarController: SnackbarController = defaultSnackbarController
) = ObserveOneTimeEventEffect(this) { event ->

}

@get:Composable
private val defaultNavHostController
    get() = koinInject<AppProviders>().navHostControllerProvider.provide()

@get:Composable
private val defaultSnackbarController
    get() = koinInject<TicTacToeUserFlowProviders>().snackbarControllerProvider.provide()