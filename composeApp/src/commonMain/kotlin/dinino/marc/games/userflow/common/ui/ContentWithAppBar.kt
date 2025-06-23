package dinino.marc.games.userflow.common.ui


import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import dinino.marc.games.app.di.AppProviders
import org.koin.compose.koinInject

@Composable
fun ContentWithAppBar(
    modifier: Modifier = Modifier,
    localizedTitle: String,
    navController: NavController = koinInject<AppProviders>().navControllerProvider(),
    content: @Composable (PaddingValues) -> Unit
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            ActionBar(
                localizedTitle = localizedTitle,
                navController = navController
            )
        },
        content = content
    )
}