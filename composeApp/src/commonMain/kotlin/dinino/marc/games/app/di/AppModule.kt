package dinino.marc.games.app.di

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import org.koin.dsl.module

val appModule = module {
    single {
        AppProviders(navHostControllerProvider = object : AppProviders.NavHostControllerProvider {
            @Composable override fun provide() = rememberNavController()
        })
    }
}