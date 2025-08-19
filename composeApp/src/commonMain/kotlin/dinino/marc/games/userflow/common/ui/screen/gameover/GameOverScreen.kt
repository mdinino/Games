package dinino.marc.games.userflow.common.ui.screen.gameover

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavHostController
import dinino.marc.games.app.ui.theme.sizes.sizes
import dinino.marc.games.userflow.common.ui.ObserveOneTimeEventEffect
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
     VM: GameOverViewModel<STATE>
> GameOverScreen(
    modifier: Modifier = Modifier,
    navHostController: NavHostController,
    vm: VM,
    oneTimeEventHandler: (navHostController: NavHostController, oneTimeEvent: GameOverOneTimeEvent)->Unit,
) {
    val state = vm.state.collectAsState()

    val onSelectNewGame: (()->Unit)? =
        when(state.value.isSelectStartNewGameAvailable) {
            true -> { { vm.selectStartNewGame() } }
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
        oneTimeEventHandler = oneTimeEventHandler,
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
            oneTimeEventHandler: (navHostController: NavHostController, oneTimeEvent: ONE_TIME_EVENT)->Unit,
            onSelectNewGameOrNullIfDisabled : (()->Unit)? = {},
            onDifferentGameGameOrNullIfDisabled : (()->Unit)? = null
) {
    ObserveOneTimeEventEffect(oneTimeEvents = oneTimeEvents) { oneTimeEvent ->
        oneTimeEventHandler.invoke(navHostController, oneTimeEvent)
    }

    GameOverLayout(
        modifier = modifier,
        onSelectNewGameOrNullIfDisabled = onSelectNewGameOrNullIfDisabled,
        onSelectDifferentGameOrNullIfDisabled = onDifferentGameGameOrNullIfDisabled
    )
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

        Spacer(
            modifier = Modifier.height(MaterialTheme.sizes.spacings.medium)
        )

        OutlinedButton(
            modifier = Modifier
                .width(MaterialTheme.sizes.buttons.medium.width)
                .height(MaterialTheme.sizes.buttons.medium.height),
            enabled = onSelectNewGameOrNullIfDisabled != null,
            onClick = onSelectNewGameOrNullIfDisabled ?: {}
        ) {
            Text(stringResource(Res.string.game_over_screen_new_game))
        }

        Spacer(modifier = Modifier.height(MaterialTheme.sizes.spacings.extraSmall))

        OutlinedButton(
            modifier = Modifier
                .width(MaterialTheme.sizes.buttons.medium.width)
                .height(MaterialTheme.sizes.buttons.medium.height),
            enabled = onSelectDifferentGameOrNullIfDisabled != null,
            onClick = onSelectDifferentGameOrNullIfDisabled  ?: {}
        ) {
            Text(stringResource(Res.string.game_over_screen_different_game))
        }
    }
}
