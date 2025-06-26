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
import dinino.marc.games.userflow.common.ui.nav.SerializableUserFlowRoute.UserFlowNavGraphRoute
import dinino.marc.games.userflow.selectgame.ui.nav.SelectGameNavGraphRoute
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
                modifier = Modifier.padding(innerPadding),
                navHostController = navHostController
            )
        }
    }
}