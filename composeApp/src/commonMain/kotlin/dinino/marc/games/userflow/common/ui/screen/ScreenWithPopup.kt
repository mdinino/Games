package dinino.marc.games.userflow.common.ui.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.flow.MutableStateFlow


@Composable
fun ScreenWithPopup(
    screen: Screen,
    popup: Popup,
    isPopupVisible: MutableStateFlow<Boolean>
) {
    val showPopup by isPopupVisible.collectAsStateWithLifecycle()
    screen.content()
    if (showPopup) {
        Popup(
            alignment = Alignment.Center,
            onDismissRequest = { isPopupVisible.value = false },
            properties = popup.properties,
            content = popup.content
        )
    }
}

class Screen(val content: @Composable ()->Unit)

class Popup(
    val properties: PopupProperties = PopupProperties(),
    val content: @Composable ()->Unit
)

