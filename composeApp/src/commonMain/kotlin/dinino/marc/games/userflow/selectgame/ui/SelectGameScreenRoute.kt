package dinino.marc.games.userflow.selectgame.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import dinino.marc.games.userflow.common.ui.ContentWithAppBar
import dinino.marc.games.userflow.common.ui.SerializableUserFlowRoute.UserFlowScreenRoute
import dinino.marc.games.userflow.common.ui.SerializableUserFlowRoute.UserFlowScreenRoute.ClearBackStack
import dinino.marc.games.userflow.selectgame.di.SelectGameUserFlowProviders
import kotlinx.serialization.Serializable
import org.koin.compose.koinInject

@Serializable
data object SelectGameScreenRoute: UserFlowScreenRoute, ClearBackStack {
    @Suppress("ComposableNaming")
    @Composable
    override fun invoke(modifier: Modifier) {
        ContentWithAppBar(
            modifier = modifier,
            localizedTitle = koinInject<SelectGameUserFlowProviders>().localizedNameProvider()
        ) { innerPadding ->
            SelectGameScreen(modifier = Modifier.padding(innerPadding))
        }
    }
}