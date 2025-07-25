package dinino.marc.games.userflow.common.ui.screen.selectneworresumegame

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import dinino.marc.games.userflow.common.ui.ObserveOneTimeEventEffect
import dinino.marc.games.userflow.common.ui.layout.AlignWidthsColumnLayout
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
     VM: SelectNewOrResumeGameViewModel<GAME, STATE>
> SelectNewOrResumeGameScreen(
    modifier: Modifier = Modifier,
    navHostController: NavHostController,
    vm: VM,
    oneTimeEventHandler: (navHostController: NavHostController, event: SelectNewOrResumeGameOneTimeEvent)->Unit
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
        oneTimeEventHandler = oneTimeEventHandler,
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
            oneTimeEventHandler: (navHostController: NavHostController, event: ONE_TIME_EVENT)->Unit,
            onSelectNewGameOrNullIfDisabled : (()->Unit)? = {},
            onSelectResumeGameOrNullIfDisabled : (()->Unit)? = null
) {
    ObserveOneTimeEventEffect(oneTimeEvents = oneTimeEvents) { oneTimeEvent ->
        oneTimeEventHandler.invoke(navHostController, oneTimeEvent)
    }

    SelectNewOrResumeGameLayout(
        modifier = modifier,
        onSelectNewGameOrNullIfDisabled = onSelectNewGameOrNullIfDisabled,
        onSelectResumeGameOrNullIfDisabled = onSelectResumeGameOrNullIfDisabled
    )
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
