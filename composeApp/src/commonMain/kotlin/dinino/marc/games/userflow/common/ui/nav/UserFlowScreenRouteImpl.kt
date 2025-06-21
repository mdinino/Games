package dinino.marc.games.userflow.common.ui.nav

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import dinino.marc.games.userflow.common.ui.ActionBar

abstract class UserFlowScreenRouteImpl: SerializableUserFlowRoute.UserFlowScreenRoute {

    @Composable
    final override fun Composable(
        modifier: Modifier,
        localizedFlowName: String,
        navController: NavController
    ) {
        Scaffold(
            modifier = modifier,
            topBar = {
                ActionBar(
                    localizedTitle = localizedFlowName,
                    navController = navController
                )
            }
        ) {
            ContentsSansActionBar()
        }
    }

    @Composable
    protected fun ContentsSansActionBar() {

    }
}