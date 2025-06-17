package dinino.marc.games.userflow.common.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import games.composeapp.generated.resources.Res
import games.composeapp.generated.resources.resume_game
import games.composeapp.generated.resources.start_game
import games.composeapp.generated.resources.userflow_tictactoe
import org.jetbrains.compose.resources.stringResource


@Composable
fun StartOrResumeGameScreen(
    modifier: Modifier = Modifier,
    gameName: String = stringResource(Res.string.userflow_tictactoe),
    onStartGameClickedOrNullIfDisabled: (()->Unit)? = {},
    onResumeGameClickedOrNullIfDisabled: (()->Unit)? = null
) {
    Column(modifier = modifier) {
        Text(
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            text = gameName
        )
        StartOrResumeButtonsColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            onStartGameClickedOrNullIfDisabled = onStartGameClickedOrNullIfDisabled,
            onResumeGameClickedOrNullIfDisabled = onResumeGameClickedOrNullIfDisabled
        )
    }
}

@Composable
private fun StartOrResumeButtonsColumn(
    modifier: Modifier = Modifier,
    verticalArrangement: Arrangement.Vertical = Arrangement.Center,
    onStartGameClickedOrNullIfDisabled: (()->Unit)? = {},
    onResumeGameClickedOrNullIfDisabled: (()->Unit)? = null
) {
    Column(
        modifier = modifier,
        verticalArrangement = verticalArrangement
    ) {
        Button(
            modifier = Modifier.fillMaxWidth(),
            enabled = (onStartGameClickedOrNullIfDisabled != null),
            onClick = onStartGameClickedOrNullIfDisabled ?: {}
        ) {
            Text(stringResource(Res.string.start_game))
        }
        Button(
            modifier = Modifier.fillMaxWidth(),
            enabled = (onResumeGameClickedOrNullIfDisabled != null),
            onClick = onResumeGameClickedOrNullIfDisabled ?: {}
        ) {
            Text(stringResource(Res.string.resume_game))
        }
    }
}