package dinino.marc.games.userflow.common.ui.layout

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun FilledButtonWithConfirmation(
    modifier: Modifier = Modifier,
    text: String = "Lorem Ipsum",
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        FilledTonalButton(
            shape = RectangleShape,
            onClick = {}
        ) {
            Text(text)
        }

        FilledTonalIconButton(
            onClick = {}
        ) {
            //Icon(
             //   imageVector = Icons.Filled.Favorite, // Use a Material Design icon (e.g., a heart)
              //  contentDescription = "Favorite" // Provide an accessibility description
            //)
        }
    }
}