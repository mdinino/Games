package dinino.marc.games.userflow.selectgame.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import dinino.marc.games.userflow.common.di.UserFlowProviders
import dinino.marc.games.userflow.common.ui.layout.MenuSelected
import dinino.marc.games.userflow.common.ui.route.ContentWithActionBarScreenRoute
import dinino.marc.games.userflow.common.ui.route.SerializableUserFlowRoute
import dinino.marc.games.userflow.selectgame.di.SelectGameUserFlowProviders
import kotlinx.coroutines.flow.Flow
import kotlinx.serialization.Serializable
import org.koin.mp.KoinPlatform

@Serializable
data object SelectGameScreenRoute : ContentWithActionBarScreenRoute(),
    SerializableUserFlowRoute.UserFlowScreenRoute.ClearUserFlowBackStack {

    override val localizedTitleProvider: UserFlowProviders.LocalizedNameProvider
        get() = KoinPlatform.getKoin()
            .get<SelectGameUserFlowProviders>().localizedNameProvider

    @Composable
    override fun Content(
        modifier: Modifier,
        navHostController: NavHostController,
        menuSelectedOneTimeEvent: Flow<MenuSelected>
    ) = SelectGameScreen(
            modifier = modifier,
            navHostController = navHostController
        )
    }