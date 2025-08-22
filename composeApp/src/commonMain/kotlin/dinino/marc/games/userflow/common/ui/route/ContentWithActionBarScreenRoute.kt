package dinino.marc.games.userflow.common.ui.route

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import dinino.marc.games.userflow.common.di.UserFlowProviders
import dinino.marc.games.userflow.common.ui.layout.ContentWithActionBar
import dinino.marc.games.userflow.common.ui.layout.MenuSelected
import dinino.marc.games.userflow.common.ui.layout.NotShown
import kotlinx.coroutines.flow.Flow
import kotlinx.serialization.Serializable

@Serializable
abstract class ContentWithActionBarScreenRoute() : SerializableUserFlowRoute.UserFlowScreenRoute {

    protected abstract val localizedTitleProvider: UserFlowProviders.LocalizedNameProvider

    protected open val showMenuIcon: MutableState?
        get() = defaultShowMenuIconState

    @Composable
    protected abstract fun Content(
        modifier: Modifier,
        navHostController: NavHostController,
        menuSelectedOneTimeEvent: Flow<MenuSelected>
    )

    @Composable
    override fun Screen(
        modifier: Modifier,
        navHostController: NavHostController,
    ) {
        ContentWithActionBar(
            modifier = modifier,
            localizedTitle = localizedTitleProvider.provide(),
            showMenuIcon = showMenuIcon,
            navHostController = navHostController,
        ) { innerPadding, menuSelectedOneTimeEvent,->
            Content(
                Modifier
                    .padding(innerPadding)
                    .fillMaxSize(),
                navHostController,
                menuSelectedOneTimeEvent
            )
        }
    }
}

private val defaultShowMenuIconState = mutableStateOf(NotShown)