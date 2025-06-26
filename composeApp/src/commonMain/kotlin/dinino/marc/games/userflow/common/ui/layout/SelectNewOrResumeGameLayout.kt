package dinino.marc.games.userflow.common.ui.layout

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import games.composeapp.generated.resources.Res
import games.composeapp.generated.resources.new_game
import games.composeapp.generated.resources.resume_game
import org.jetbrains.compose.resources.stringResource

@Composable
fun SelectNewOrResumeGameLayout(
    modifier: Modifier = Modifier,
    navHostController: NavHostController,
    onNewGameOrNullIfDisabled : (()->Unit)? = {},
    onResumeGameOrNullIfDisabled : (()->Unit)? = null,
) {
    AlignWidthsColumnLayout(modifier = modifier) {
        listOf(
            @Composable {
                Button(
                    modifier = Modifier.fillMaxWidth(),
                    enabled = onNewGameOrNullIfDisabled != null,
                    onClick = onNewGameOrNullIfDisabled ?: {}
                ) {
                    Text(stringResource(Res.string.new_game))
                }
            },
            @Composable {
                Button(
                    modifier = Modifier.fillMaxWidth(),
                    enabled = onResumeGameOrNullIfDisabled != null,
                    onClick = onResumeGameOrNullIfDisabled ?: {}
                ) {
                    Text(stringResource(Res.string.resume_game))
                }
            }
        )
    }
}