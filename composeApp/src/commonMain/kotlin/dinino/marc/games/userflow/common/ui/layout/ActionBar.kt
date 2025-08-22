package dinino.marc.games.userflow.common.ui.layout

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowBackIos
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import dinino.marc.games.platform.PlatformManager
import dinino.marc.games.platform.PlatformType
import games.composeapp.generated.resources.Res
import games.composeapp.generated.resources.back_button
import games.composeapp.generated.resources.menu
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.koinInject

sealed interface ActionBarOneTimeEvent
data object BackSelected: ActionBarOneTimeEvent
data object MenuSelected: ActionBarOneTimeEvent

sealed interface ShowIconState
data object NotShown: ShowIconState

sealed interface Shown: ShowIconState
data object ShownEnabled: Shown
data object ShownDisabled: Shown

/**
 * A top bar inspired by the traditional Android action bar, with a back button, title, and menu button.
 */
@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun ActionBar(
    modifier: Modifier = Modifier,
    localizedTitle: String? = "Lorem Ipsum",
    showBackIcon: ShowIconState = ShownEnabled,
    showMenuIcon: ShowIconState = ShownEnabled,
    sendEventScope: CoroutineScope = rememberCoroutineScope(),
    actionBarOneTimeEvents: Channel<ActionBarOneTimeEvent> = Channel()
) {
    fun sendActionBarEvent(event: ActionBarOneTimeEvent) {
        sendEventScope.launch {
            actionBarOneTimeEvents.send(event)
        }
    }

    fun sendOnBackSelected() {
        if (showBackIcon is ShownEnabled) {
            sendActionBarEvent(BackSelected)
        }
    }

    fun sendOnMenuSelected() {
        if (showMenuIcon is ShownEnabled) {
            sendActionBarEvent(MenuSelected)
        }
    }

    val navigationIcon: @Composable ()->Unit = {
        when(showBackIcon) {
            is NotShown -> {}
            is ShownDisabled -> {
                IconButton(
                    onClick = { },
                    enabled = false
                ) {
                    Icon(
                        imageVector = platformSpecificNavigationVector(),
                        contentDescription = stringResource(Res.string.back_button)
                    )
                }
            }
            is ShownEnabled -> {
                IconButton(
                    onClick = { sendOnBackSelected() },
                    enabled = true,
                ) {
                    Icon(
                        imageVector = platformSpecificNavigationVector(),
                        contentDescription = stringResource(Res.string.back_button)
                    )
                }
            }
        }
    }

    val menuIcon: @Composable RowScope.()->Unit = {
        when(showMenuIcon) {
            is NotShown -> {}
            is ShownDisabled -> {
                IconButton(
                    onClick = { },
                    enabled = false
                ) {
                    Icon(
                        imageVector = Icons.Filled.Menu,
                        contentDescription = stringResource(Res.string.menu)
                    )
                }
            }
            is ShownEnabled -> {
                IconButton(
                    onClick = { sendOnMenuSelected() },
                    enabled = true
                ) {
                    Icon(
                        imageVector = Icons.Filled.Menu,
                        contentDescription = stringResource(Res.string.menu)
                    )
                }
            }
        }
    }

    CenterAlignedTopAppBar(
        modifier = modifier,
        colors = TopAppBarDefaults.mediumTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.background
        ),
        title = localizedTitle.toLocalizedTitleComposable(),
        navigationIcon = navigationIcon,
        actions = menuIcon
    )
}

@Composable
private fun String?.toLocalizedTitleComposable(): @Composable ()->Unit = {
    when(this) {
        null -> {}
        else -> Text(text = this)
    }
}

@Composable
private fun platformSpecificNavigationVector(
    platformType: PlatformType = koinInject<PlatformManager>().platformType
) = when(platformType) {
    is PlatformType.IOS -> Icons.AutoMirrored.Filled.ArrowBackIos
    else -> Icons.AutoMirrored.Filled.ArrowBack
}

