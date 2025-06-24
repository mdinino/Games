package dinino.marc.games.app.di

import androidx.navigation.compose.rememberNavController
import org.koin.dsl.module

val appModule = module {
    single {
        AppProviders(navHostControllerProvider = { rememberNavController() })
    }
}