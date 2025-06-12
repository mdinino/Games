package dinino.marc.games.ui.screen.selectgames

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.LayoutCoordinates
import androidx.compose.ui.layout.onPlaced
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
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
        GameButtonsColumn(
            modifier = Modifier
                .safeContentPadding()
                .fillMaxSize()
                .wrapContentWidth(),
            onTicTacToeSelected =onTicTacToeSelected,
            onTetrisSelected = onTetrisSelected)
    }
}

@Preview
@Composable
fun GameButtonsColumn(
    modifier: Modifier = Modifier,
    onTicTacToeSelected : ()->Unit = {},
    onTetrisSelected : ()->Unit = {}
) {
    val density: Density = LocalDensity.current
    val measuredWidths = remember{
        mutableStateOf<Map<String, Dp?>>(emptyMap())
    }

    fun buttonModifier(id: String) =
        when(measuredWidths[id]) {
            null -> Modifier.wrapContentWidth()
                .onPlaced {
                    measuredWidths[id] = with(density) { it.size.width.toDp() }
                }
            else -> Modifier.width(measuredWidths.maxSafe())
        }

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
    ) {
        Button(
            onClick = onTicTacToeSelected,
            modifier = buttonModifier("TicTacToe"),
        ) { Text(stringResource(Res.string.game_tictactoe)) }
        Button(
            onClick = onTetrisSelected,
            modifier = buttonModifier("Tetris")
        ) { Text(stringResource(Res.string.game_tetris)) }
    }
}

private fun MutableState<Map<String, Dp?>>.maxSafe(): Dp =
    max() ?: 0.dp
private fun MutableState<Map<String, Dp?>>.max(): Dp? {
    var max: Dp? = null
    for (value in this.value.values) {
        if (value == null) continue
        else if (max == null) max = value
        else if (value > max) max = value
    }
    return max
}

private operator fun MutableState<Map<String, Dp?>>.get(key: String): Dp? =
    this.value[key]

private operator fun MutableState<Map<String, Dp?>>.set(key: String, value: Dp) =
    mutate { this[key] = value }

private fun MutableState<Map<String, Dp?>>.mutate(mutator: MutableMap<String, Dp?>.()->Unit) {
    this.value = value
        .toMutableMap()
        .also { it.mutator() }
        .toMap()
}

