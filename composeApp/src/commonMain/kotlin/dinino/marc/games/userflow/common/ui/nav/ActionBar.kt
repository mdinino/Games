package dinino.marc.games.userflow.common.ui.nav

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
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
import games.composeapp.generated.resources.Res
import games.composeapp.generated.resources.back_button
import games.composeapp.generated.resources.menu
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview

/**
 * A top bar inspired by the traditional Android action bar, with a back button, title, and menu button.
 * @param localizedTitle: Title string or null if should not be shown.
 * @param showBackIcon: Set to true if a an auto-mirrored back icon should be shown at the start of the bar..
 * @param onBackClicked: The action to take when the user clicks on the back icon.
 * @param showMenuIcon: Set to true if an auto-mirrored menu icon should be sows at the end of the bar.
 * @param onMenuClicked: The action to take when the menu icon is clicked.
 */
@Composable
@Preview
@OptIn(ExperimentalMaterial3Api::class)
fun ActionBar(
    modifier: Modifier = Modifier,
    localizedTitle: String? = "Lorem Ipsum",
    showBackIcon: Boolean = true,
    onBackClicked: ()->Unit = {},
    showMenuIcon: Boolean = true,
    onMenuClicked: ()->Unit = {}
) {
    val title: @Composable ()->Unit = {
        when(localizedTitle) {
            null -> {}
            else -> Text(text = localizedTitle)
        }
    }

    val navigationIcon: @Composable ()->Unit = {
        when(showBackIcon) {
            false -> {}
            true -> {
                IconButton(onClick = onBackClicked) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
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
                IconButton(onClick = onMenuClicked) {
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
        title = title,
        navigationIcon = navigationIcon,
        actions = menuIcon
    )
}

@Composable
fun ActionBar(
    modifier: Modifier = Modifier,
    localizedTitle: String? = "Lorem Ipsum",
    navHostController: NavHostController,
    showMenuIcon: Boolean = true,
    onMenuClicked: ()->Unit = {}
) {
    val showBackIcon = navHostController.previousBackStackEntry != null
    val onBackClicked: ()->Unit = {
        when(showBackIcon) {
            false -> {}
            true -> { navHostController.popBackStack() }
        }
    }

    ActionBar(
        modifier = modifier,
        localizedTitle = localizedTitle,
        showBackIcon = showBackIcon,
        onBackClicked = onBackClicked,
        showMenuIcon = showMenuIcon,
        onMenuClicked = onMenuClicked
    )
}

@Composable
fun ActionBar(
    modifier: Modifier = Modifier,
    localizedTitle: String? = "Lorem Ipsum",
    navHostController: NavHostController,
    onMenuClicked: (()->Unit)? = null
) {
   ActionBar(
       modifier = modifier,
       localizedTitle = localizedTitle,
       navHostController = navHostController,
       showMenuIcon = onMenuClicked != null,
       onMenuClicked = onMenuClicked ?: {}
   )
}