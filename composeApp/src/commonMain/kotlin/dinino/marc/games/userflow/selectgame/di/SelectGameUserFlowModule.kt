package dinino.marc.games.userflow.selectgame.di

import dinino.marc.games.app.ui.SnackbarController
import dinino.marc.games.userflow.selectgame.ui.SelectGameViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.core.qualifier.Qualifier
import org.koin.core.qualifier.named
import org.koin.dsl.module

val selectGameUserFLowModule = module {
    single<SnackbarController>(selectGameSnackbarControllerQualifier) { SnackbarController() }
    viewModel { SelectGameViewModel() }
}

/**
 * The Select Game user-flow has a snackbar controller dedicated to its lifecycle.
 * In other words, the snackbar controller is active in any screen that is part of that flow,
 * but is not active when not in that flow.
 * This is the Koin qualifier dedicated used to access the singleton.
 */
val selectGameSnackbarControllerQualifier: Qualifier
    get() = named("selectGameSnackbarControllerQualifier")