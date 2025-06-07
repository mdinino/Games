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
import androidx.lifecycle.viewmodel.compose.viewModel
import games.composeapp.generated.resources.Res
import games.composeapp.generated.resources.game_tetris
import games.composeapp.generated.resources.game_tictactoe
import games.composeapp.generated.resources.select_game
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun SelectGameScreenRoot(vm: SelectGamesViewModel = viewModel()) {
    SelectGameScreen()
}

@Composable
@Preview
fun SelectGameScreen() {
    Column(
        modifier = Modifier
            .safeContentPadding()
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(stringResource(Res.string.select_game))
        GameButtons()
    }
}

@Composable
@Preview
private fun GameButtons() {
    Column(
        modifier = Modifier
            .safeContentPadding()
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
    ) {
        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = {  }
        ) { Text(stringResource(Res.string.game_tictactoe)) }
        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = {  }
        ) { Text(stringResource(Res.string.game_tetris)) }
    }
}