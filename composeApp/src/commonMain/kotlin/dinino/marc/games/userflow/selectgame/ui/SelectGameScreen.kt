package dinino.marc.games.userflow.selectgame.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import dinino.marc.games.userflow.common.ui.ObserveOneTimeEventEffect
import dinino.marc.games.userflow.common.ui.SnackbarController
import games.composeapp.generated.resources.Res
import games.composeapp.generated.resources.userflow_tetris
import games.composeapp.generated.resources.userflow_tictactoe
import kotlinx.coroutines.flow.Flow
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun SelectGameScreenRoot(
    navController: NavController,
    snackbarController: SnackbarController,
    vm: SelectGameViewModel = koinViewModel()
) {
    SelectGameScreen(
        navController = navController,
        snackbarController = snackbarController,
        oneTimeEvents = vm.oneTimeEvents,
        onTetrisSelected = { vm.navigateToTetrisFLow() },
        onTicTacToeSelected = { vm.navigateToTicTacToeFlow() }
    )
}

@Composable
fun SelectGameScreen(
    navController: NavController,
    snackbarController: SnackbarController,
    oneTimeEvents: Flow<SelectGameViewModel.OneTimeEvent>,
    onTicTacToeSelected: ()->Unit,
    onTetrisSelected: ()->Unit,
) {
    oneTimeEvents.ObserveEffect(
        navController = navController,
        snackbarController = snackbarController
    )

    Column(
        modifier = Modifier.fillMaxWidth(),
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
    navController: NavController,
    snackbarController: SnackbarController
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
