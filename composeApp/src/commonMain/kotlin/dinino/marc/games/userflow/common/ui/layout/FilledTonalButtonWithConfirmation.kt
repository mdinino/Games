package dinino.marc.games.userflow.common.ui.layout

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import dinino.marc.games.app.ui.theme.sizes.Paddings
import dinino.marc.games.app.ui.theme.sizes.sizes
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
    contentPadding: PaddingValues = ButtonDefaults.ContentPadding,
    content: @Composable RowScope.() -> Unit
) {
    var readyForConfirmation by rememberSaveable { mutableStateOf(false) }

    Row(
        modifier = modifier
            .defaultMinSize(
                minWidth = ButtonDefaults.MinWidth,
                minHeight = ButtonDefaults.MinHeight
            )
            .clip(shape),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        FilledTonalButton(
            onClick = { readyForConfirmation = true },
            modifier = Modifier
                .fillMaxHeight()
                .weight(1f, fill = true),
            shape = RectangleShape,
            contentPadding = contentPadding.startPaddingValues(),
            elevation = null,
            enabled = enabled,
            content = content
        )

        Spacer(
            modifier = Modifier
                .width(MaterialTheme.sizes.spacings.extraTiny)
                .fillMaxHeight()
        )

        FilledTonalButton(
            onClick = { onConfirmed() },
            modifier = Modifier
                .fillMaxHeight()
                .wrapContentWidth(),
            shape = RectangleShape,
            contentPadding = contentPadding.endPaddingValues(),
            elevation = null,
            enabled = enabled && readyForConfirmation,
        ) {
            Icon(
                modifier = Modifier
                    .wrapContentWidth()
                    .fillMaxHeight(),
                imageVector = Icons.Default.Check,
                contentDescription = stringResource(Res.string.confirm)
            )
        }
    }
}

@Composable
private fun PaddingValues.startPaddingValues() =
    PaddingValues(
        start = calculateStartPadding(LocalLayoutDirection.current),
        top = calculateTopPadding(),
        end = calculateEndPadding(LocalLayoutDirection.current).half,
        bottom = calculateBottomPadding(),
    )

@Composable
private fun PaddingValues.endPaddingValues() =
    PaddingValues(
        start = calculateStartPadding(LocalLayoutDirection.current).half,
        top = calculateTopPadding(),
        end = calculateEndPadding(LocalLayoutDirection.current),
        bottom = calculateBottomPadding(),
    )

private val Dp.half: Dp
    get() = (value / 2F).dp
