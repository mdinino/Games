package dinino.marc.games.userflow.selectgame.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import dinino.marc.games.app.ui.ObserveOneTimeEventEffect
import dinino.marc.games.app.ui.SnackbarController
import dinino.marc.games.app.ui.SnackbarController.Companion.ObserveEffect
import dinino.marc.games.userflow.selectgame.di.selectGameSnackbarControllerQualifier
import games.composeapp.generated.resources.Res
import games.composeapp.generated.resources.userflow_select_game
import games.composeapp.generated.resources.userflow_tetris
import games.composeapp.generated.resources.userflow_tictactoe
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.koinInject
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun SelectGameScreenRoot(
    modifier: Modifier = Modifier,
    vm: SelectGameViewModel = koinViewModel()
) {
    SelectGameScreen(
        modifier = modifier,
        onTetrisSelected = { vm.navigateToTetrisFLow() },
        onTicTacToeSelected = { vm.navigateToTicTacToeFlow() },
        oneTimeEvents = vm.oneTimeEvents
    )
}

@Composable
@Preview
fun SelectGameScreen(
    modifier: Modifier = Modifier.fillMaxSize(),
    onTicTacToeSelected: ()->Unit ={},
    onTetrisSelected: ()->Unit = {},
    oneTimeEvents: Flow<SelectGameViewModel.OneTimeEvent> = emptyFlow(),
    userFlowSnackbarController: SnackbarController =
        koinInject(selectGameSnackbarControllerQualifier)
) {
    val snackbarHostState = remember { SnackbarHostState() }

    userFlowSnackbarController.events.ObserveEffect(snackbarHostState)
    oneTimeEvents.ObserveEffect(userFlowSnackbarController)

    Scaffold(
        snackbarHost = { SnackbarHost( snackbarHostState) },
        modifier = modifier
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                text = stringResource(Res.string.userflow_select_game)
            )
            GameButtonsColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                onTicTacToeSelected = onTicTacToeSelected,
                onTetrisSelected = onTetrisSelected)
        }
    }
}

@Composable
@Preview
private fun GameButtonsColumn(
    modifier: Modifier = Modifier,
    verticalArrangement: Arrangement.Vertical = Arrangement.Center,
    onTicTacToeSelected : ()->Unit = {},
    onTetrisSelected : ()->Unit = {}
) {
    Column(
        modifier = modifier,
        verticalArrangement = verticalArrangement,
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
    userFlowSnackbarController: SnackbarController =
        koinInject(selectGameSnackbarControllerQualifier)
) = ObserveOneTimeEventEffect(this) { event ->
        when(event) {
            is SelectGameViewModel.OneTimeEvent.Error ->
                userFlowSnackbarController.sendSnackbarEvent(event.asSnackbarEvent())

            SelectGameViewModel.OneTimeEvent.Navigate.NavigateToTetrisFlow -> TODO()

            SelectGameViewModel.OneTimeEvent.Navigate.NavigateToTicTacToeFlow -> TODO()
        }
}

private fun SelectGameViewModel.OneTimeEvent.Error.asSnackbarEvent() =
    SnackbarController.SnackbarEvent(localizedMessage = localizedMessage)
