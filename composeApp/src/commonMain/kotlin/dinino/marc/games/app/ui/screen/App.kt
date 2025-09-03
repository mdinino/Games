package dinino.marc.games.app.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import dinino.marc.games.app.ui.theme.GamesTheme
import dinino.marc.games.app.ui.theme.sizes.sizes
import dinino.marc.games.userflow.common.ui.route.SerializableUserFlowRoute.UserFlowNavGraphRoute
import dinino.marc.games.userflow.selectgame.ui.SelectGameNavGraphRoute
import org.jetbrains.compose.ui.tooling.preview.Preview


@Composable
@Preview
fun App(landingUserFlow: UserFlowNavGraphRoute = SelectGameNavGraphRoute) {
    GamesTheme {
        val navHostController = rememberNavController()
        Scaffold(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background)
                .safeContentPadding()
                .fillMaxSize()

        ){ innerPadding ->
            landingUserFlow.Navigation(
                modifier = Modifier.padding(
                    /*
                     * This is the best padding I have seen for all platforms (including safeContentPadding)
                     */
                    all = MaterialTheme.sizes.paddings.medium
                ),
                navHostController = navHostController
            )
        }
    }
}