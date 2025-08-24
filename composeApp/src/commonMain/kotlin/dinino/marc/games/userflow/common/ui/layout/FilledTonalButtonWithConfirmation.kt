package dinino.marc.games.userflow.common.ui.layout

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ButtonElevation
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp
import games.composeapp.generated.resources.Res
import games.composeapp.generated.resources.confirm
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun FilledTonalButtonWithConfirmation(
    onConfirmed: ()->Unit = {},
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    shape: Shape = ButtonDefaults.filledTonalShape,
    content: @Composable RowScope.() -> Unit
) {
    var readyForConfirmation by rememberSaveable { mutableStateOf(false) }

    Row(
        modifier = modifier
            .defaultMinSize(
                minWidth = ButtonDefaults.MinWidth,
                minHeight = ButtonDefaults.MinHeight
            ).clip(shape),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        FilledTonalButton(
            onClick = { readyForConfirmation = true },
            modifier = Modifier
                .padding(0.dp)
                .fillMaxHeight()
                .weight(1f, fill = true),
            shape = RectangleShape,
            contentPadding = PaddingValues(all = 0.dp),
            elevation = null,
            enabled = enabled,
            content = content
        )

        FilledTonalButton(
            onClick = { onConfirmed() },
            modifier = Modifier
                .padding(0.dp)
                .fillMaxHeight()
                .wrapContentWidth(),
            shape = RectangleShape,
            contentPadding = PaddingValues(all = 0.dp),
            elevation = null,
            enabled = enabled && readyForConfirmation,
        ) {
            Icon(
                modifier = Modifier
                    .padding(0.dp)
                    .wrapContentWidth()
                    .fillMaxHeight(),
                imageVector = Icons.Default.Check,
                contentDescription = stringResource(Res.string.confirm)
            )
        }
    }
}