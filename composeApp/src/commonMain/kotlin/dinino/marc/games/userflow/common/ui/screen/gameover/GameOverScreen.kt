package dinino.marc.games.userflow.common.ui.screen.gameover

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavHostController
import dinino.marc.games.app.ui.theme.spacing
import dinino.marc.games.userflow.common.ui.ObserveOneTimeEventEffect
import dinino.marc.games.userflow.common.ui.layout.AlignWidthsColumnLayout
import dinino.marc.games.userflow.common.ui.route.SerializableUserFlowRoute.Companion.navigateToRoute
import games.composeapp.generated.resources.Res
import games.composeapp.generated.resources.game_over_screen_new_game
import games.composeapp.generated.resources.game_over_screen_different_game
import games.composeapp.generated.resources.game_over_screen_subtitle
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun <STATE: GameOverState,
     ONE_TIME_EVENT: GameOverOneTimeEvent,
     VM: GameOverViewModel<STATE, ONE_TIME_EVENT>
> GameOverScreen(
    modifier: Modifier = Modifier,
    navHostController: NavHostController,
    vm: VM
) {
    val state = vm.state.collectAsState()

    val onSelectNewGame: (()->Unit)? =
        when(state.value.isSelectStartNewGameAvailable) {
            true -> { { vm.selectNewGame() } }
            else -> null
        }

    val onSelectDifferentGame: (()->Unit)? =
        when(state.value.isSelectDifferentGameAvailable) {
            true -> { { vm.selectDifferentGame() } }
            else -> null
        }

    GameOverScreen(
        modifier = modifier,
        navHostController = navHostController,
        oneTimeEvents = vm.oneTimeEvents,
        onSelectNewGameOrNullIfDisabled = onSelectNewGame,
        onDifferentGameGameOrNullIfDisabled = onSelectDifferentGame
    )
}

@Composable
fun <ONE_TIME_EVENT: GameOverOneTimeEvent>
        GameOverScreen(
            modifier: Modifier = Modifier,
            navHostController: NavHostController,
            oneTimeEvents: Flow<ONE_TIME_EVENT> = emptyFlow(),
            onSelectNewGameOrNullIfDisabled : (()->Unit)? = {},
            onDifferentGameGameOrNullIfDisabled : (()->Unit)? = null
) {
    oneTimeEvents.ObserveEffect(
        navHostController = navHostController
    )

    GameOverLayout(
        modifier = modifier,
        onSelectNewGameOrNullIfDisabled = onSelectNewGameOrNullIfDisabled,
        onSelectDifferentGameOrNullIfDisabled = onDifferentGameGameOrNullIfDisabled
    )
}

@Composable
private fun <ONE_TIME_EVENT: GameOverOneTimeEvent>
        Flow<ONE_TIME_EVENT>.ObserveEffect(
            navHostController: NavHostController
) {
    ObserveOneTimeEventEffect(oneTimeEvents = this) { oneTimeEvent ->
        when(oneTimeEvent) {
            is GameOverOneTimeEvent.Navigate -> {
                navHostController.navigateToRoute(oneTimeEvent.route)
            }
        }
    }
}

@Composable
@Preview
fun GameOverLayout(
    modifier: Modifier = Modifier,
    onSelectNewGameOrNullIfDisabled : (()->Unit)? = {},
    onSelectDifferentGameOrNullIfDisabled : (()->Unit)? = {}
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            textAlign = TextAlign.Center,
            text = stringResource(Res.string.game_over_screen_subtitle)
        )

        Spacer(modifier = Modifier.height(MaterialTheme.spacing.medium))

        AlignWidthsColumnLayout(modifier = modifier) {
            listOf(
                @Composable {
                    Button(
                        enabled = onSelectNewGameOrNullIfDisabled != null,
                        onClick = onSelectNewGameOrNullIfDisabled ?: {}
                    ) {
                        Text(stringResource(Res.string.game_over_screen_new_game))
                    }
                },
                @Composable {
                    Button(
                        enabled = onSelectDifferentGameOrNullIfDisabled != null,
                        onClick = onSelectDifferentGameOrNullIfDisabled  ?: {}
                    ) {
                        Text(stringResource(Res.string.game_over_screen_different_game))
                    }
                }
            )
        }
    }
}
