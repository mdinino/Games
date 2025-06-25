package dinino.marc.games.userflow.common.ui

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import dinino.marc.games.app.di.AppProviders
import dinino.marc.games.userflow.common.di.UserFlowProviders
import dinino.marc.games.userflow.common.ui.SerializableUserFlowRoute.UserFlowScreenRoute
import org.koin.mp.KoinPlatform.getKoin

class ContentWithAppBarScreenRoute(
    private val localizedTitleProvider: UserFlowProviders.LocalizedNameProvider,
    private val navHostControllerProvider: AppProviders.NavHostControllerProvider =
        getKoin().get<AppProviders>().navHostControllerProvider,
    private val content: @Composable (Modifier)->Unit
) : UserFlowScreenRoute {

    @Composable
    override fun Screen(modifier: Modifier) {
        ContentWithAppBar(
            modifier = modifier,
            localizedTitle = localizedTitleProvider.provide(),
            navHostController = navHostControllerProvider.provide()
        ) { innerPadding ->
            content(Modifier.padding(innerPadding).fillMaxSize())
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