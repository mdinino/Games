package dinino.marc.games.userflow.common.ui.route

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import dinino.marc.games.userflow.common.di.UserFlowProviders
import dinino.marc.games.userflow.common.ui.route.SerializableUserFlowRoute
import dinino.marc.games.userflow.common.ui.layout.ActionBar

class ContentWithAppBarScreenRoute(
    private val localizedTitleProvider: UserFlowProviders.LocalizedNameProvider,
    private val content: @Composable (Modifier, NavHostController)->Unit
) : SerializableUserFlowRoute.UserFlowScreenRoute {

   @Composable
   override fun Screen(modifier: Modifier, navHostController: NavHostController) {
       ContentWithAppBar(
           localizedTitle = localizedTitleProvider.provide(),
           navHostController = navHostController
       ) { innerPadding ->
           content(
               Modifier.Companion
                       .padding(innerPadding)
                       .fillMaxSize(),
               navHostController
           )
       }
   }

   companion object {
       @Composable
       private fun ContentWithAppBar(
           modifier: Modifier = Modifier.Companion,
           localizedTitle: String,
           navHostController: NavHostController,
           content: @Composable (PaddingValues) -> Unit
       ) {
           Scaffold(
               modifier = modifier,
               topBar = {
                   ActionBar(
                       modifier = modifier,
                       localizedTitle = localizedTitle,
                       navHostController = navHostController
                   )
               },
               content = content
           )
       }
   }
}