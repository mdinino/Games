package dinino.marc.games.userflow.common.ui.layout

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import games.composeapp.generated.resources.Res
import games.composeapp.generated.resources.new_game
import games.composeapp.generated.resources.resume_game
import org.jetbrains.compose.resources.stringResource

@Composable
fun SelectNewOrResumeGameLayout(
    modifier: Modifier = Modifier,
    onNewGameOrNullIfDisabled : (()->Unit)? = {},
    onResumeGameOrNullIfDisabled : (()->Unit)? = null,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
    ) {
        Button(
            modifier = Modifier.fillMaxWidth(),
            enabled = onNewGameOrNullIfDisabled != null,
            onClick = onNewGameOrNullIfDisabled ?: {}
        ) {
            Text(stringResource(Res.string.new_game))
        }
        Button(
            modifier = Modifier.fillMaxWidth(),
            enabled = onResumeGameOrNullIfDisabled != null,
            onClick = onResumeGameOrNullIfDisabled ?: {}
        ) {
            Text(stringResource(Res.string.resume_game))
        }
    }
}