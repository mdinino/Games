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
import dinino.marc.games.userflow.common.ui.layout.ActionBarEvent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable

@Serializable
abstract class ContentWithAppBarScreenRoute() : SerializableUserFlowRoute.UserFlowScreenRoute {

    protected abstract val localizedTitleProvider: UserFlowProviders.LocalizedNameProvider
    protected open val showMenuIcon: Boolean
        get() = false

    protected val actionBarOneTimeEvent: ReceiveChannel<ActionBarEvent.MenuSelected>
        get() = _actionBarOneTimeEventEvent

    @Composable
    protected abstract fun Content(
        modifier: Modifier,
        navHostController: NavHostController
    )

    @Composable
    override fun Screen(
        modifier: Modifier,
        navHostController: NavHostController,
    ) {
        ContentWithAppBar(
            localizedTitle = localizedTitleProvider.provide(),
            navHostController = navHostController,
            showMenuIcon = showMenuIcon,
            onActionBarMenuSelected = { event -> sendActionBarOneTimeEventEvent(event) }
        ) { innerPadding ->
            Content(
                Modifier
                    .padding(innerPadding)
                    .fillMaxSize(),
               navHostController
            )
        }
    }

    private fun sendActionBarOneTimeEventEvent(event: ActionBarEvent.MenuSelected) {
        CoroutineScope(Dispatchers.Default).launch {
            _actionBarOneTimeEventEvent.send(event)
        }
    }

    companion object {
        @Composable
        private fun ContentWithAppBar(
            modifier: Modifier = Modifier,
            localizedTitle: String,
            navHostController: NavHostController,
            showMenuIcon: Boolean,
            onActionBarMenuSelected: (event: ActionBarEvent.MenuSelected) -> Unit,
            content: @Composable (PaddingValues) -> Unit
        ) {
            Scaffold(
                modifier = modifier,
                topBar = {
                    ActionBar(
                        modifier = modifier,
                        localizedTitle = localizedTitle,
                        navHostController = navHostController,
                        showMenuIcon = showMenuIcon,
                        onMenuSelected = onActionBarMenuSelected
                    )
                },
                content = content
            )
        }

        private val _actionBarOneTimeEventEvent = Channel<ActionBarEvent.MenuSelected>()
    }
}