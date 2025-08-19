package dinino.marc.games.userflow.common.ui.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.flow.MutableStateFlow


@Composable
fun ScreenWithDialog(
    screen: Screen,
    dialog: Dialog,
    isDialogVisible: MutableStateFlow<Boolean>
) {
    val showDialog by isDialogVisible.collectAsStateWithLifecycle()
    screen.content()
    if (showDialog) {
        Dialog(
            onDismissRequest = { isDialogVisible.value = false },
            properties = dialog.properties,
            content = dialog.content
        )
    }
}

class Screen(val content: @Composable ()->Unit)

class Dialog(
    val properties: DialogProperties = DialogProperties(),
    val content: @Composable ()->Unit
)

