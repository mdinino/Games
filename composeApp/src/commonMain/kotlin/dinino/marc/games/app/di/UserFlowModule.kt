package dinino.marc.games.app.di

import org.koin.core.module.Module
import org.koin.core.qualifier.Qualifier

interface UserFlowModule {
    /**
     * The Koin DI module for all the dependencies in this user flow.
     */
    val koinModule: Module

    /**
     * Every user flow has a dedicated snackbar controller that is dedicated to its lifecycle.
     * In other words, the snackbar controller is active in any screen that is part of the user flow,
     * but is not active when not in that specific user flow.
     * This value is the Koin qualifier that is used to access it.
     */
    val snackbarControllerQualifier: Qualifier
}