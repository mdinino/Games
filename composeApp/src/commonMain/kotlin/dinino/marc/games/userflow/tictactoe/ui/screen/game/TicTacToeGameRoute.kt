package dinino.marc.games.userflow.tictactoe.ui.screen.game

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import dinino.marc.games.userflow.common.ui.layout.ActionBarOneTimeEvent
import dinino.marc.games.userflow.common.ui.route.ContentWithActionBarScreenRoute
import dinino.marc.games.userflow.common.ui.route.GameUserFlowNavGraphRoute
import dinino.marc.games.userflow.tictactoe.di.TicTacToeUserFlowProviders
import kotlinx.coroutines.flow.Flow
import kotlinx.serialization.Serializable
import org.koin.mp.KoinPlatform

@Serializable
data object TicTacToeGameRoute : ContentWithActionBarScreenRoute(),
    GameUserFlowNavGraphRoute.GameRoute {

    override val localizedTitleProvider
        get() = KoinPlatform.getKoin()
            .get<TicTacToeUserFlowProviders>().localizedNameProvider

    override val showMenuIcon: Boolean
        get() = true

    @Composable
    override fun Content(
        modifier: Modifier,
        navHostController: NavHostController,
        menuSelectedOneTimeEvent: Flow<ActionBarOneTimeEvent.MenuSelected>
    ) =
        TicTacToeGameScreen(
            modifier = modifier,
            navHostController = navHostController,
            menuSelectedOneTimeEvent = menuSelectedOneTimeEvent
        )
}
