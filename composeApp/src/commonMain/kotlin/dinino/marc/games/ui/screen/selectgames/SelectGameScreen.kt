package dinino.marc.games.ui.screen.selectgames

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import dinino.marc.games.ui.screen.GamesSnackbarController
import dinino.marc.games.ui.screen.ObserveOneTimeEventEffects.ObserveOneTimeEventsOrNullEffect
import games.composeapp.generated.resources.Res
import games.composeapp.generated.resources.game_tetris
import games.composeapp.generated.resources.game_tictactoe
import games.composeapp.generated.resources.select_game
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ReceiveChannel
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun SelectGameScreenRoot(vm: SelectGameViewModel = koinViewModel()) {
    SelectGameScreen(
        onTetrisSelected = { vm.navigate(SelectGameViewModel.Action.Navigate.NavigateToTetris) },
        onTicTacToeSelected = { vm.navigate(SelectGameViewModel.Action.Navigate.NavigateToTicTacToe) },
        errors = vm.errors
    )
}

@Composable
@Preview
fun SelectGameScreen(
    onTicTacToeSelected: ()->Unit = {},
    onTetrisSelected: ()->Unit = {},
    errors: ReceiveChannel<SideEffect.Error> = Channel()
) {
    ObserveOneTimeEventsOrNullEffect(errors) { error ->
        if (error != null) {
            GamesSnackbarController
                .instance
                .SendSnackbarEvent(
                    GamesSnackbarController
                        .SnackbarEvent(error.localizedMessage)
                )
        }

    }

    Column(
        modifier = Modifier
            .safeContentPadding()
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(stringResource(Res.string.select_game))
        GameButtons(onTicTacToeSelected, onTetrisSelected)
    }
}

@Composable
@Preview
private fun GameButtons(
    onTicTacToeSelected : ()->Unit = {},
    onTetrisSelected : ()->Unit = {}
) {
    Column(
        modifier = Modifier
            .safeContentPadding()
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
    ) {
        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = onTicTacToeSelected
        ) { Text(stringResource(Res.string.game_tictactoe)) }
        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = onTetrisSelected
        ) { Text(stringResource(Res.string.game_tetris)) }
    }
}