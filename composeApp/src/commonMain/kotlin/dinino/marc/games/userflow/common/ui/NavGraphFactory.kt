package dinino.marc.games.userflow.common.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import dinino.marc.games.userflow.common.ui.SnackbarController.Companion.ObserveEffect

class NavGraphFactory: @Composable (NavController, SnackbarController) -> ComposableFun {

    @Composable
    override fun invoke(
        navController: NavController,
        snackbarController: SnackbarController
    ): ComposableFun = {
        NavigationGraph(navController, snackbarController)
    }

    @Composable
    private fun NavigationGraph(
        navController: NavController,
        snackbarController: SnackbarController
    ) {
        val snackbarHostState = remember { SnackbarHostState() }
        snackbarController.events.ObserveEffect(snackbarHostState = snackbarHostState)

        Scaffold(
            snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
            modifier = Modifier.Companion.fillMaxSize()
        ) { innerPadding ->
            NavHost(
                navController = navController,
                startDestination = ScreenA,
                modifier = Modifier.padding(innerPadding)
            ) {
                composable<ScreenA> {
                    ScreenA(onNavigate = {
                        navController.navigate(ScreenB)
                    })
                }
                composable<ScreenB> {
                    Box(
                        modifier = Modifier.Companion
                            .fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = "Screen B")
                    }
                }
            }
        }
    }
}