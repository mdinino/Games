package dinino.marc.games.userflow.selectgame.ui.screen

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import dinino.marc.games.userflow.common.ui.layout.AlignWidthsColumnLayout
import dinino.marc.games.userflow.common.ui.nav.ObserveOneTimeEventLayout
import dinino.marc.games.userflow.common.ui.nav.SerializableUserFlowRoute.Companion.navigateToRoute
import dinino.marc.games.userflow.common.ui.nav.SnackbarController
import dinino.marc.games.userflow.selectgame.di.SelectGameUserFlowProviders
import dinino.marc.games.userflow.tictactoe.ui.nav.TicTacToeNavGraphRoute
import games.composeapp.generated.resources.Res
import games.composeapp.generated.resources.userflow_tetris
import games.composeapp.generated.resources.userflow_tictactoe
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.koinInject
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun SelectGameScreen(
    modifier: Modifier = Modifier,
    navHostController: NavHostController,
    viewModel: SelectGameViewModel = koinViewModel()
) {
    SelectGameScreen(
        modifier = modifier,
        navHostController = navHostController,
        oneTimeEvents = viewModel.oneTimeEvents,
        onTetrisSelected = {  viewModel.navigateToTetrisFLow() },
        onTicTacToeSelected = {  viewModel.navigateToTicTacToeFlow() }
    )
}

@Preview
@Composable
private fun SelectGameScreen(
    modifier: Modifier = Modifier,
    navHostController: NavHostController,
    snackbarController: SnackbarController = defaultSnackbarController,
    oneTimeEvents: Flow<SelectGameViewModel.OneTimeEvent> = emptyFlow(),
    onTicTacToeSelected: ()->Unit = {},
    onTetrisSelected: ()->Unit = {},
) {
    ObserveOneTimeEventLayout(
        modifier = modifier,
        oneTimeEvents = oneTimeEvents,
        onOneTimeEvent = { event->
            handleOneTimeEvent(
                navHostController = navHostController,
                snackbarController = snackbarController,
                event = event
            )
        }
    ) {
        AlignWidthsColumnLayout(modifier = modifier) {
            listOf(
                @Composable {
                    Button(
                        modifier = Modifier.fillMaxWidth(),
                        onClick = onTicTacToeSelected
                    ) { Text(stringResource(Res.string.userflow_tictactoe)) }
                },
                @Composable {
                    Button(
                        modifier = Modifier.fillMaxWidth(),
                        onClick = onTetrisSelected
                    ) { Text(stringResource(Res.string.userflow_tetris)) }
                }
            )
        }
    }
}

private suspend fun <T> handleOneTimeEvent(
    navHostController: NavHostController,
    snackbarController: SnackbarController,
    event: T
) {
    when(event) {
        is SelectGameViewModel.OneTimeEvent.Error ->
            snackbarController.sendSnackbarEvent(event.asSnackbarEvent())
        is SelectGameViewModel.OneTimeEvent.Navigate.NavigateToTicTacToeFlow ->
            navHostController.navigateToRoute(TicTacToeNavGraphRoute)
        is SelectGameViewModel.OneTimeEvent.Navigate.NavigateToTetrisFlow ->
            TODO()
    }
}

private fun SelectGameViewModel.OneTimeEvent.Error.asSnackbarEvent() =
    SnackbarController.SnackbarEvent(localizedMessage = localizedMessage)

@get:Composable
private val defaultSnackbarController
    get() = koinInject<SelectGameUserFlowProviders>().snackbarControllerProvider.provide()
