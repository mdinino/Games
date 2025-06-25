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
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import dinino.marc.games.app.di.AppProviders
import games.composeapp.generated.resources.Res
import games.composeapp.generated.resources.back_button
import games.composeapp.generated.resources.menu
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.koinInject

@Composable
fun ActionBar(
    modifier: Modifier = Modifier,
    localizedTitle: String?,
    navHostController: NavHostController =
        koinInject<AppProviders>().navHostControllerProvider.provide(),
    menuAction: (@Composable () -> Unit)? = null
) = ActionBar(
    modifier = modifier,
    localizedTitle = localizedTitle,
    navigateBackAction = navHostController.asNavigateBackAction(),
    menuAction = menuAction
)

@Composable
@Preview
private fun ActionBarPreview(
    modifier: Modifier = Modifier,
    localizedTitle: String = "Lorem Ipsum",
) = ActionBar(
    modifier = modifier,
    localizedTitle = localizedTitle,
    navigateBackAction = {},
    menuAction = {}
)

/**
 * A top bar inspired by the traditional Android action bar, with a back button, title, and menu button.
 * @param localizedTitle: Title string or null if should not be shown.
 * @param navigateBackAction: The action to take when the back icon is selected, or null if should not be shown.
 * @param menuAction: The action to take when the menu icon is selected, or null if should not be shown.
 */
@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun ActionBar(
    modifier: Modifier = Modifier,
    localizedTitle: String?,
    navigateBackAction: (@Composable () -> Unit)?,
    menuAction: (@Composable () -> Unit)?
) {
    CenterAlignedTopAppBar(
        modifier = modifier,
        colors = TopAppBarDefaults.mediumTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.background
        ),
        title = localizedTitle.asTitle(),
        navigationIcon = navigateBackAction.asNavigateIcon(),
        actions = menuAction.asActions()
    )
}

private fun NavHostController.asNavigateBackAction(): (@Composable () -> Unit)? {
    if (!canGoBack()) return null
    return { navigateUp() }
}

private fun NavController.canGoBack(): Boolean =
    previousBackStackEntry != null

private fun String?.asTitle(): @Composable ()->Unit {
    if (this == null) return {}
    return { Text(text = this) }
}

private fun (@Composable ()->Unit)?.asNavigateIcon(): @Composable ()-> Unit {
    if (this == null) return {}
    return {
        IconButton(onClick = { this } ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = stringResource(Res.string.back_button)
            )
        }
    }
}

@Composable
private fun (@Composable ()->Unit)?.asActions(): @Composable RowScope.()-> Unit {
    if (this == null) return {}
    return {
        IconButton(onClick = { this@asActions }) {
            Icon(
                imageVector = Icons.Filled.Menu,
                contentDescription = stringResource(Res.string.menu)
            )
        }
    }
}