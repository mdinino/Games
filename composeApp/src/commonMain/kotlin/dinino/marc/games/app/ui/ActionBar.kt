package dinino.marc.games.app.ui

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import games.composeapp.generated.resources.Res
import games.composeapp.generated.resources.arrow_back_24dp
import games.composeapp.generated.resources.back_button
import games.composeapp.generated.resources.menu
import games.composeapp.generated.resources.menu_24dp
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.resources.vectorResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun ActionBar(
    modifier: Modifier = Modifier,
    localizedTitle: String?,
    navController: NavController,
    menuAction: (@Composable () -> Unit)? = null
) = ActionBar(
    modifier = modifier,
    localizedTitle = localizedTitle,
    navigateBackAction = navController.asNavigateBackAction(),
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
    TopAppBar(
        modifier = modifier,
        colors = TopAppBarDefaults.mediumTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        ),
        title = localizedTitle.asTitle(),
        navigationIcon = navigateBackAction.asNavigateIcon(),
        actions = menuAction.asActions()
    )
}

private fun NavController.asNavigateBackAction(): (@Composable () -> Unit)? {
    if (!canGoBack()) return null
    return { navigateUp() }
}

private fun NavController.canGoBack(): Boolean =
    previousBackStackEntry != null

private fun String?.asTitle(): @Composable ()->Unit {
    if (this == null) return {}
    return { Text(this) }
}

private fun (@Composable ()->Unit)?.asNavigateIcon(): @Composable ()-> Unit {
    if (this == null) return {}
    return {
        IconButton(onClick = { this } ) {
            Icon(
                imageVector = vectorResource(Res.drawable.arrow_back_24dp),
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
                imageVector = vectorResource(Res.drawable.menu_24dp),
                contentDescription = stringResource(Res.string.menu)
            )
        }
    }
}