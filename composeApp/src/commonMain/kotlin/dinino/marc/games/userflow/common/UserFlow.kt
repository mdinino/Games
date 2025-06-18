package dinino.marc.games.userflow.common

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.material3.FabPosition
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScaffoldDefaults
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import dinino.marc.games.userflow.common.ui.SnackbarController
import dinino.marc.games.userflow.common.ui.SnackbarController.Companion.ObserveEffect
import dinino.marc.games.userflow.common.ui.SnackbarController.SnackbarEvent
import kotlinx.coroutines.flow.Flow
import org.koin.core.module.Module

/**
 * A definition for a user-flow. Ensures commonalities/symmetries between different flows.
 */
abstract class UserFlow {
    /**
     * To register dependencies with Koin DI.
     */
    abstract val koinModule: Module

    @Composable
    abstract fun NavGraph(
        modifier: Modifier = Modifier,
        navController: NavHostController = rememberNavController()
    )

    /**
     * Will be displayed in the navigation bar.
     */
    protected abstract suspend fun localizedName(): String

    /**
     * Every user-flow has a dedicated snackbar controller to show messages, errors, etc.
     * Each flow's snackbar controller controller is active in any screen that is part of that flow.
     */
    protected abstract val snackbarController: SnackbarController


    @Composable
    private fun UserFlowScaffold(
        modifier: Modifier = Modifier,
        content: @Composable (PaddingValues) -> Unit
    ) {
        val snackbarHostState = remember { SnackbarHostState() }
        snackbarController.events.ObserveEffect(snackbarHostState)

        Scaffold(
            modifier = modifier,
            snackbarHost = { SnackbarHost(snackbarHostState) },
            topBar = topBar,
            bottomBar = bottomBar,
            floatingActionButton = floatingActionButton,
            floatingActionButtonPosition = floatingActionButtonPosition,
            containerColor = containerColor,
            contentColor = contentColor,
            contentWindowInsets = contentWindowInsets,
            content = content
        )
    }
}