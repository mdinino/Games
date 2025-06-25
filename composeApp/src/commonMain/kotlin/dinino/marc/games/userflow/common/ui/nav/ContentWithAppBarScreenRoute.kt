package dinino.marc.games.userflow.common.ui.nav

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import dinino.marc.games.app.di.AppProviders
import dinino.marc.games.userflow.common.di.UserFlowProviders
import org.koin.mp.KoinPlatform

class ContentWithAppBarScreenRoute(
    private val localizedTitleProvider: UserFlowProviders.LocalizedNameProvider,
    private val navHostControllerProvider: AppProviders.NavHostControllerProvider =
        KoinPlatform.getKoin().get<AppProviders>().navHostControllerProvider,
    private val content: @Composable (Modifier)->Unit
) : SerializableUserFlowRoute.UserFlowScreenRoute {

    @Composable
    override fun Screen(modifier: Modifier) {
        ContentWithAppBar(
            localizedTitle = localizedTitleProvider.provide(),
            navHostController = navHostControllerProvider.provide()
        ) { innerPadding ->
            content(Modifier.Companion.padding(innerPadding).fillMaxSize())
        }
    }

    companion object {
        @Composable
        private fun ContentWithAppBar(
            modifier: Modifier = Modifier.Companion,
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