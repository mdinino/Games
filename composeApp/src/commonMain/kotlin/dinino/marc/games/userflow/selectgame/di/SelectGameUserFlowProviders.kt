package dinino.marc.games.userflow.selectgame.di

import dinino.marc.games.userflow.common.di.UserFlowProviders

class SelectGameUserFlowProviders(
    override val localizedNameProvider: UserFlowProviders.LocalizedNameProvider,
    override val snackbarControllerProvider: UserFlowProviders.SnackbarControllerProvider
): UserFlowProviders
