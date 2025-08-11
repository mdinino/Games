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
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import dinino.marc.games.platform.PlatformManager
import dinino.marc.games.platform.PlatformType
import games.composeapp.generated.resources.Res
import games.composeapp.generated.resources.back_button
import games.composeapp.generated.resources.menu
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.koinInject

/**
 * A top bar inspired by the traditional Android action bar, with a back button, title, and menu button.
 * @param localizedTitle: Title string or null if should not be shown.
 * @param showBackIcon: Set to true if a an auto-mirrored back icon should be shown at the start of the bar.
 * @param onBackSelected: Called when the back icon is selected.
 * @param showMenuIcon: Set to true if an auto-mirrored menu icon should be shown at the end of the bar.
 * @param onMenuSelected: Called when the menu icon is selected
 */
@Composable
@Preview
@OptIn(ExperimentalMaterial3Api::class)
fun ActionBar(
    modifier: Modifier = Modifier,
    localizedTitle: String? = "Lorem Ipsum",
    showBackIcon: Boolean = true,
    onBackSelected: ()->Unit = {},
    showMenuIcon: Boolean = true,
    onMenuSelected: ()->Unit
) {
    val navigationIcon: @Composable ()->Unit = {
        when(showBackIcon) {
            false -> {}
            true -> {
                IconButton(onClick = onBackSelected) {
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
                IconButton(onClick = onMenuSelected) {
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
    localizedTitle: String? = "Lorem Ipsum",
    navHostController: NavHostController,
    showMenuIcon: Boolean,
    onMenuSelected: ()->Unit
) {
    val showBackIcon = navHostController.previousBackStackEntry != null

    val onBackSelected = {
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

