package dinino.marc.games.userflow.common.ui.route

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import dinino.marc.games.userflow.common.di.UserFlowProviders
import dinino.marc.games.userflow.common.ui.layout.ActionBarOneTimeEvent
import dinino.marc.games.userflow.common.ui.layout.ContentWithActionBar
import kotlinx.coroutines.flow.Flow
import kotlinx.serialization.Serializable

@Serializable
abstract class ContentWithActionBarScreenRoute() : SerializableUserFlowRoute.UserFlowScreenRoute {

    protected abstract val localizedTitleProvider: UserFlowProviders.LocalizedNameProvider

    protected open val showMenuIcon: Boolean
        get() = false

    override val dialogs: List<SerializableUserFlowRoute.UserFlowDialogRoute>
        get() = emptyList()

    @Composable
    protected abstract fun Content(
        modifier: Modifier,
        navHostController: NavHostController,
        menuSelectedOneTimeEvent: Flow<ActionBarOneTimeEvent.MenuSelected>
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