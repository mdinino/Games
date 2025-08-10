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
import androidx.navigation.NavHostController
import dinino.marc.games.platform.PlatformManager
import dinino.marc.games.platform.PlatformType
import dinino.marc.games.userflow.common.ui.ObserveOneTimeEventEffect
import games.composeapp.generated.resources.Res
import games.composeapp.generated.resources.back_button
import games.composeapp.generated.resources.menu
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.koinInject

@Serializable
sealed interface ActionBarEvent {
    @Serializable data object BackSelected: ActionBarEvent
    @Serializable data object MenuSelected: ActionBarEvent
}

/**
 * A top bar inspired by the traditional Android action bar, with a back button, title, and menu button.
 * @param localizedTitle: Title string or null if should not be shown.
 * @param showBackIcon: Set to true if a an auto-mirrored back icon should be shown at the start of the bar.
 * @param showMenuIcon: Set to true if an auto-mirrored menu icon should be shown at the end of the bar.
 * @param events: A channel send/receive ActionBar Events
 * @see ActionBarEvent
 */
@Composable
@Preview
@OptIn(ExperimentalMaterial3Api::class)
fun ActionBar(
    modifier: Modifier = Modifier,
    localizedTitle: String? = "Lorem Ipsum",
    showBackIcon: Boolean = true,
    showMenuIcon: Boolean = true,
    events: Channel<ActionBarEvent> = Channel(),
    eventsSendScope: CoroutineScope = rememberCoroutineScope()
) {
    fun sendEvent(event: ActionBarEvent) {
        eventsSendScope.launch {
            events.send(event)
        }
    }

    val navigationIcon: @Composable ()->Unit = {
        when(showBackIcon) {
            false -> {}
            true -> {
                IconButton(onClick = { sendEvent(ActionBarEvent.BackSelected) }) {
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
            false -> {}
            true -> {
                IconButton(onClick = { sendEvent(ActionBarEvent.MenuSelected) }) {
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
fun ActionBar(
    modifier: Modifier = Modifier,
    localizedTitle: String? = null,
    showBackIcon: Boolean = true,
    showMenuIcon: Boolean = true,
    onBackSelected: (event: ActionBarEvent.BackSelected) -> Unit = {},
    onMenuSelected: (event: ActionBarEvent.MenuSelected) -> Unit = {},
) {
    val events = Channel<ActionBarEvent>()

    ObserveOneTimeEventEffect(oneTimeEvents = events.receiveAsFlow()) { event ->
        when(event) {
            is ActionBarEvent.BackSelected -> onBackSelected(event)
            is ActionBarEvent.MenuSelected -> onMenuSelected(event)
        }
    }

    ActionBar(
        modifier = modifier,
        localizedTitle = localizedTitle,
        showBackIcon = showBackIcon,
        showMenuIcon = showMenuIcon,
        events = events
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

@Composable
fun ActionBar(
    modifier: Modifier = Modifier,
    localizedTitle: String? = "Lorem Ipsum",
    navHostController: NavHostController,
    showMenuIcon: Boolean,
    onMenuSelected: (ActionBarEvent.MenuSelected)->Unit
) {
    val showBackIcon = navHostController.previousBackStackEntry != null

    val onBackSelected = { backSelected: ActionBarEvent.BackSelected ->
        when(showBackIcon) {
            false -> {}
            true -> {
                if (navHostController.previousBackStackEntry != null) {
                    navHostController.popBackStack()
                }
            }
        }
    }

    ActionBar(
        modifier = modifier,
        localizedTitle = localizedTitle,
        showBackIcon = showBackIcon,
        onBackSelected = onBackSelected,
        showMenuIcon = showMenuIcon,
        onMenuSelected = onMenuSelected
    )
}