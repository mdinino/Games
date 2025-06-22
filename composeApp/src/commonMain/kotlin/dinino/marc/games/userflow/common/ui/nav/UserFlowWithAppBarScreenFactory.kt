package dinino.marc.games.userflow.common.ui.nav


import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import dinino.marc.games.userflow.common.ui.ActionBar
import dinino.marc.games.userflow.common.ui.SnackbarController

class UserFlowWithAppBarScreenFactory(
    private val localizedUserFlowTitle: String,
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
        Scaffold(
            topBar = {
                ActionBar(
                    localizedTitle = localizedUserFlowTitle, navController = navController
                )
            }
        ) {
            screenContentsCreator(navController, snackbarController)
        }
    }
}