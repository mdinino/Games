package dinino.marc.games.userflow.common.ui.screen.selectneworresumegame

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import dinino.marc.games.userflow.common.ui.ObserveOneTimeEventEffect
import dinino.marc.games.userflow.common.ui.layout.AlignWidthsColumnLayout
import dinino.marc.games.userflow.common.ui.route.SerializableUserFlowRoute.Companion.navigateToRoute
import games.composeapp.generated.resources.Res
import games.composeapp.generated.resources.select_new_or_resume_game_screen_new_game
import games.composeapp.generated.resources.select_new_or_resume_game_screen_resume_game
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun <GAME: Any,
     STATE: SelectNewOrResumeGameState,
     ONE_TIME_EVENT: SelectNewOrResumeGameOneTimeEvent,
     VM: SelectNewOrResumeGameViewModel<GAME, STATE, ONE_TIME_EVENT>
> SelectNewOrResumeGameScreen(
    modifier: Modifier = Modifier,
    navHostController: NavHostController,
    vm: VM
) {
    val state = vm.selectNewOrResumeGameState.collectAsState()

    val onSelectNewGame: (()->Unit)? =
        when(state.value.isSelectNewGameAvailable) {
            true -> { { vm.selectNewGame() } }
            else -> null
        }

    val onSelectResumeGame: (()->Unit)? =
        when(state.value.isSelectResumeGameAvailable) {
            true -> { { vm.selectResumeGame() } }
            else -> null
        }

    SelectNewOrResumeGameScreen(
        modifier = modifier,
        navHostController = navHostController,
        oneTimeEvents = vm.oneTimeEvents,
        onSelectNewGameOrNullIfDisabled = onSelectNewGame,
        onSelectResumeGameOrNullIfDisabled = onSelectResumeGame
    )
}

@Composable
fun <ONE_TIME_EVENT: SelectNewOrResumeGameOneTimeEvent>
        SelectNewOrResumeGameScreen(
            modifier: Modifier = Modifier,
            navHostController: NavHostController,
            oneTimeEvents: Flow<ONE_TIME_EVENT> = emptyFlow(),
            onSelectNewGameOrNullIfDisabled : (()->Unit)? = {},
            onSelectResumeGameOrNullIfDisabled : (()->Unit)? = null
) {
    oneTimeEvents.ObserveEffect(
        navHostController = navHostController
    )

    SelectNewOrResumeGameLayout(
        modifier = modifier,
        onSelectNewGameOrNullIfDisabled = onSelectNewGameOrNullIfDisabled,
        onSelectResumeGameOrNullIfDisabled = onSelectResumeGameOrNullIfDisabled
    )
}

@Composable
private fun <ONE_TIME_EVENT: SelectNewOrResumeGameOneTimeEvent>
        Flow<ONE_TIME_EVENT>.ObserveEffect(
            navHostController: NavHostController
) {
    ObserveOneTimeEventEffect(oneTimeEvents = this) { oneTimeEvent ->
        when(oneTimeEvent) {
            is SelectNewOrResumeGameOneTimeEvent.Navigate -> {
                navHostController.navigateToRoute(oneTimeEvent.route)
            }
        }
    }
}

@Composable
@Preview
fun SelectNewOrResumeGameLayout(
    modifier: Modifier = Modifier,
    onSelectNewGameOrNullIfDisabled : (()->Unit)? = {},
    onSelectResumeGameOrNullIfDisabled : (()->Unit)? = {},
) {
    AlignWidthsColumnLayout(modifier = modifier) {
        listOf(
            @Composable {
                Button(
                    enabled = onSelectNewGameOrNullIfDisabled != null,
                    onClick = onSelectNewGameOrNullIfDisabled ?: {}
                ) {
                    Text(stringResource(Res.string.select_new_or_resume_game_screen_new_game))
                }
            },
            @Composable {
                Button(
                    enabled = onSelectResumeGameOrNullIfDisabled != null,
                    onClick = onSelectResumeGameOrNullIfDisabled ?: {}
                ) {
                    Text(stringResource(Res.string.select_new_or_resume_game_screen_resume_game))
                }
            }
        )
    }
}
