package dinino.marc.games.userflow.common.ui.nav


import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import dinino.marc.games.userflow.common.ui.ActionBar
import dinino.marc.games.userflow.common.ui.SnackbarController

class ScreenWithAppBarFactory(
    private val localizedTitle: @Composable ()->String,
    private val screenContentsCreator: (
                navController: NavController,
                snackbarController: SnackbarController
            )->ComposableFun
): @Composable (NavController, SnackbarController) -> ComposableFun {

    @Composable
    override fun invoke(
        navController: NavController,
        snackbarController: SnackbarController
    ): ComposableFun = {
        ScreenContentsWithAppBar(navController, snackbarController)
    }

    @Composable
    private fun ScreenContentsWithAppBar(
        navController: NavController,
        snackbarController: SnackbarController
    ) {
        Scaffold(
            topBar = {
                ActionBar(
                    localizedTitle = localizedTitle(), navController = navController
                )
            }
        ) {
            screenContentsCreator(navController, snackbarController)
        }
    }
}