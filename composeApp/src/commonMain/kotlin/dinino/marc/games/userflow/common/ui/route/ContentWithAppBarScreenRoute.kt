package dinino.marc.games.userflow.common.ui.route

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import dinino.marc.games.userflow.common.di.UserFlowProviders
import dinino.marc.games.userflow.common.ui.layout.ActionBar
import kotlinx.serialization.Serializable

@Serializable
abstract class
ContentWithAppBarScreenRoute() : SerializableUserFlowRoute.UserFlowScreenRoute {
    protected abstract val localizedTitleProvider: UserFlowProviders.LocalizedNameProvider

    @Composable
    protected abstract fun Content(modifier: Modifier, navHostController: NavHostController)

    @Composable
    override fun Screen(modifier: Modifier, navHostController: NavHostController) {
        ContentWithAppBar(
            localizedTitle = localizedTitleProvider.provide(),
            navHostController = navHostController
        ) { innerPadding ->
            Content(
                Modifier
                    .padding(innerPadding)
                    .fillMaxSize(),
               navHostController
            )
        }
    }

    companion object {
        @Composable
        private fun ContentWithAppBar(
            modifier: Modifier = Modifier,
            localizedTitle: String,
            navHostController: NavHostController,
            content: @Composable (PaddingValues) -> Unit
        ) {
            Scaffold(
                modifier = modifier,
                topBar = {
                    ActionBar(
                        modifier = modifier,
                        localizedTitle = localizedTitle,
                        navHostController = navHostController
                    )
                },
                content = content
            )
        }
    }
}