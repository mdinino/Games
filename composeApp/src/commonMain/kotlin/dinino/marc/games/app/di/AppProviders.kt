package dinino.marc.games.app.di

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController

class AppProviders(
    /**
     * The same nav host controller is used between all screen in the app
     * and therefore should be stored at the app level
     */
    val navHostControllerProvider: @Composable ()-> NavHostController
)