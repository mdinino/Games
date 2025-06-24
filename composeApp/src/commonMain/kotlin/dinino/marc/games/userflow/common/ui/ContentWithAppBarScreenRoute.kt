package dinino.marc.games.userflow.common.ui

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import dinino.marc.games.app.di.AppProviders
import dinino.marc.games.userflow.common.ui.SerializableUserFlowRoute.UserFlowScreenRoute
import org.koin.mp.KoinPlatform.getKoin

class ContentWithAppBarScreenRoute(
    private val localizedTitle: @Composable ()->String,
    private val navHostController: @Composable ()-> NavHostController = appNavHostController(),
    private val content: @Composable (Modifier)->Unit
) : UserFlowScreenRoute {

    @Suppress("ComposableNaming")
    @Composable
    override fun invoke(modifier: Modifier) {
        ContentWithAppBar(
            modifier = modifier,
            localizedTitle = localizedTitle(),
            navHostController = navHostController()
        ) { innerPadding ->
            content(Modifier.padding(innerPadding))
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
                        localizedTitle = localizedTitle,
                        navHostController = navHostController
                    )
                },
                content = content
            )
        }

        private fun appNavHostController() =
            getKoin().get<AppProviders>().navHostControllerProvider
    }
}