package dinino.marc.games.app.di

import androidx.compose.runtime.Composable
import androidx.navigation.NavController

class AppProviders(
    /**
     * The same nav controller is used between all screen in the app
     * and therefore should be stored at the app level
     */
    val navControllerProvider: @Composable ()->NavController
)