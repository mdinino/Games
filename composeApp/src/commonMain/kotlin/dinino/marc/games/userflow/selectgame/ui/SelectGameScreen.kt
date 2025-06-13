package dinino.marc.games.userflow.selectgame.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import dinino.marc.games.ObserveOneTimeEffectEffect
import games.composeapp.generated.resources.Res
import games.composeapp.generated.resources.game_tetris
import games.composeapp.generated.resources.game_tictactoe
import games.composeapp.generated.resources.select_game
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.koinInject
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun SelectGameScreenRoot(vm: SelectGameViewModel = koinViewModel()) {
    SelectGameScreen(
        onTetrisSelected = { vm.navigateToTetrisFLow() },
        onTicTacToeSelected = { vm.navigateToTicTacToeFlow() },
        oneTimeEvents = vm.oneTimeEvents
    )
}

@Composable
@Preview
fun SelectGameScreen(
    modifier: Modifier = Modifier.fillMaxSize(),
    onTicTacToeSelected: ()->Unit = {},
    onTetrisSelected: ()->Unit = {},
    oneTimeEvents: Flow<SelectGameViewModel.OneTimeEvent> = emptyFlow(),
) {
    val snackbarController = koinInject<SelectGameFlowSnackbarController>()
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    ObserveOneTimeEffectEffect(oneTimeEvents) { event ->
        when(event) {
            is SelectGameViewModel.OneTimeEvent.Error ->
                snackbarController.sendSnackbarEvent(Ev)
            SelectGameViewModel.OneTimeEvent.Navigate.NavigateToTetrisFlow -> TODO()
            SelectGameViewModel.OneTimeEvent.Navigate.NavigateToTicTacToeFlow -> TODO()
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost( snackbarHostState) },
        modifier = modifier
    ) { innerPadding ->
        Column(modifier = Modifier.fillMaxSize()) {
            Text(
                textAlign = TextAlign.Center,
                text = stringResource(Res.string.select_game)
            )
            GameButtonsColumn(
                modifier = Modifier.fillMaxWidth(),
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
            Text(stringResource(Res.string.game_tictactoe))
        }
        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = onTetrisSelected
        ) {
            Text(stringResource(Res.string.game_tetris))
        }
    }
}
