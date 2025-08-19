package dinino.marc.games.userflow.selectgame.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import dinino.marc.games.app.ui.theme.sizes.sizes
import dinino.marc.games.userflow.common.ui.ObserveOneTimeEventLayout
import dinino.marc.games.userflow.common.ui.SnackbarController
import dinino.marc.games.userflow.common.ui.route.navigateDownTo
import dinino.marc.games.userflow.selectgame.di.SelectGameUserFlowProviders
import dinino.marc.games.userflow.tetris.ui.TetrisNavGraphRoute
import dinino.marc.games.userflow.tictactoe.ui.TicTacToeNavGraphRoute
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
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = CenterHorizontally
        ) {
            OutlinedButton(
                modifier = Modifier
                    .width(MaterialTheme.sizes.buttons.medium.width)
                    .height(MaterialTheme.sizes.buttons.medium.height),
                onClick = onTicTacToeSelected
            ) { Text(stringResource(Res.string.userflow_tictactoe)) }

            Spacer(modifier = Modifier.height(MaterialTheme.sizes.spacings.extraSmall))

            OutlinedButton(
                modifier = Modifier
                    .width(MaterialTheme.sizes.buttons.medium.width)
                    .height(MaterialTheme.sizes.buttons.medium.height),
                onClick = onTetrisSelected
            ) { Text(stringResource(Res.string.userflow_tetris)) }
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
            navHostController.navigateDownTo(TicTacToeNavGraphRoute)
        is SelectGameViewModel.OneTimeEvent.Navigate.NavigateToTetrisFlow ->
            navHostController.navigateDownTo(TetrisNavGraphRoute)
    }
}

private fun SelectGameViewModel.OneTimeEvent.Error.asSnackbarEvent() =
    SnackbarController.SnackbarEvent(localizedMessage = localizedMessage)

@get:Composable
private val defaultSnackbarController
    get() = koinInject<SelectGameUserFlowProviders>().snackbarControllerProvider.provide()
