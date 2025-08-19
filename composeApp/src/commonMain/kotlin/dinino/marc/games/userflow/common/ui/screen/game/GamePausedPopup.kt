package dinino.marc.games.userflow.common.ui.screen.game

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.PopupProperties
import dinino.marc.games.app.ui.theme.sizes.sizes
import dinino.marc.games.userflow.common.ui.screen.Popup
import dinino.marc.games.userflow.common.ui.screen.Screen
import dinino.marc.games.userflow.common.ui.screen.ScreenWithPopup
import games.composeapp.generated.resources.Res
import games.composeapp.generated.resources.pause_popup_end_game
import games.composeapp.generated.resources.pause_popup_restart_game
import games.composeapp.generated.resources.pause_popup_resume_game
import games.composeapp.generated.resources.pause_popup_title
import kotlinx.coroutines.flow.MutableStateFlow
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun ScreenWithGamePausedPopup(
    isPopupVisible: MutableStateFlow<Boolean>,
    screenContent: @Composable ()->Unit
) = ScreenWithPopup(
        screen = Screen(screenContent),
        popup = Popup(
            properties = PopupProperties(
                focusable = true
            ),
            content = { MinimalDialog() }
        ),
        isPopupVisible = isPopupVisible
    )

@Composable
@Preview
fun GamePausedDialog(
    modifier: Modifier = Modifier.wrapContentSize(),
    onResumeGameSelected: ()->Unit = {},
    onRestartGameSelected: ()->Unit = {},
    onEndGameSelected: ()->Unit = {},
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            textAlign = TextAlign.Center,
            text = stringResource(Res.string.pause_popup_title)
        )

        Spacer(
            modifier = Modifier.height(MaterialTheme.sizes.spacings.medium)
        )

        OutlinedButton(
            modifier = Modifier
                .width(MaterialTheme.sizes.buttons.medium.width)
                .height(MaterialTheme.sizes.buttons.medium.height),
            onClick = onResumeGameSelected
        ) {
            Text(stringResource(Res.string.pause_popup_resume_game))
        }

        Spacer(modifier = Modifier.height(MaterialTheme.sizes.spacings.extraSmall))

        OutlinedButton(
            modifier = Modifier
                .width(MaterialTheme.sizes.buttons.medium.width)
                .height(MaterialTheme.sizes.buttons.medium.height),
            onClick = onRestartGameSelected
        ) {
            Text(stringResource(Res.string.pause_popup_restart_game))
        }

        Spacer(modifier = Modifier.height(MaterialTheme.sizes.spacings.extraSmall))

        OutlinedButton(
            modifier = Modifier
                .width(MaterialTheme.sizes.buttons.medium.width)
                .height(MaterialTheme.sizes.buttons.medium.height),
            onClick = onEndGameSelected
        ) {
            Text(stringResource(Res.string.pause_popup_end_game))
        }
    }
}

@Composable
fun MinimalDialog() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .padding(16.dp),
        shape = RoundedCornerShape(16.dp),
    ) {
        Text(
            text = "This is a minimal dialog",
            modifier = Modifier
                .fillMaxSize()
                .wrapContentSize(Alignment.Center),
            textAlign = TextAlign.Center,
        )
    }
}