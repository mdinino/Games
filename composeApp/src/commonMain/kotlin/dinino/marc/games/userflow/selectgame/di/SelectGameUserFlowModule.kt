package dinino.marc.games.userflow.selectgame.di

import dinino.marc.games.common.ui.SnackbarController
import dinino.marc.games.userflow.selectgame.ui.SelectGameViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.core.qualifier.Qualifier
import org.koin.core.qualifier.named
import org.koin.dsl.module

val selectGameUserFLowModule = module {
    single<SnackbarController>(selectGameSnackbarControllerQualifier) { SnackbarController() }
    viewModel { SelectGameViewModel() }
}

val selectGameSnackbarControllerQualifier: Qualifier
    get() = named("selectGameSnackbarControllerQualifier")