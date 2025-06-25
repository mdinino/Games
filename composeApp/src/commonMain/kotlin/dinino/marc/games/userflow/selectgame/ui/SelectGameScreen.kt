package dinino.marc.games.userflow.selectgame.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import dinino.marc.games.app.di.AppProviders
import dinino.marc.games.userflow.common.ui.ObserveOneTimeEventEffect
import dinino.marc.games.userflow.common.ui.SnackbarController
import dinino.marc.games.userflow.selectgame.di.SelectGameUserFlowProviders
import games.composeapp.generated.resources.Res
import games.composeapp.generated.resources.userflow_tetris
import games.composeapp.generated.resources.userflow_tictactoe
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.koinInject
import org.koin.compose.viewmodel.koinViewModel
import org.koin.mp.KoinPlatform.getKoin

@Composable
fun SelectGameScreen(
    modifier: Modifier = Modifier,
    viewModel: SelectGameViewModel = koinViewModel()
) {
    SelectGameScreen(
        modifier = modifier,
        oneTimeEvents = viewModel.oneTimeEvents,
        onTetrisSelected = {  viewModel.navigateToTetrisFLow() },
        onTicTacToeSelected = {  viewModel.navigateToTicTacToeFlow() }
    )
}

@Preview
@Composable
private fun SelectGameScreen(
    modifier: Modifier = Modifier,
    navHostController: NavHostController = defaultNavHostController,
    snackbarController: SnackbarController = defaultSnackbarController,
    oneTimeEvents: Flow<SelectGameViewModel.OneTimeEvent> = emptyFlow(),
    onTicTacToeSelected: ()->Unit = {},
    onTetrisSelected: ()->Unit = {},
) {
    oneTimeEvents.ObserveEffect(
        navHostController = navHostController,
        snackbarController = snackbarController
    )

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
    ) {
        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = onTicTacToeSelected
        ) {
            Text(stringResource(Res.string.userflow_tictactoe))
        }
        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = onTetrisSelected
        ) {
            Text(stringResource(Res.string.userflow_tetris))
        }
    }
}

@Composable
private fun Flow<SelectGameViewModel.OneTimeEvent>.ObserveEffect(
    navHostController: NavHostController = defaultNavHostController,
    snackbarController: SnackbarController = defaultSnackbarController
) = ObserveOneTimeEventEffect(this) { event ->
        when(event) {
            is SelectGameViewModel.OneTimeEvent.Error ->
                snackbarController.sendSnackbarEvent(event.asSnackbarEvent())
            is SelectGameViewModel.OneTimeEvent.Navigate.NavigateToTetrisFlow ->
                TODO()
            is SelectGameViewModel.OneTimeEvent.Navigate.NavigateToTicTacToeFlow ->
               TODO()
        }
}

private fun SelectGameViewModel.OneTimeEvent.Error.asSnackbarEvent() =
    SnackbarController.SnackbarEvent(localizedMessage = localizedMessage)

@get:Composable
private val defaultNavHostController: NavHostController
    get() = koinInject<AppProviders>().navHostControllerProvider()

private val defaultSnackbarController: SnackbarController
    get() = getKoin().get<SelectGameUserFlowProviders>() .snackbarControllerProvider()
