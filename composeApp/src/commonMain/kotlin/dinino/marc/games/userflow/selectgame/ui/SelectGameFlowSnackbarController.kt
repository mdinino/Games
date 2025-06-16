package dinino.marc.games.userflow.selectgame.ui

import dinino.marc.games.app.ui.SnackbarController
import dinino.marc.games.app.ui.SnackbarControllerImpl
import org.koin.core.qualifier.Qualifier

/**
 * Every user flow has a dedicated snackbar controller that is dedicated to its lifecycle.
 * In other words, the snackbar controller is active in any screen that is part of the user flow,
 * but is not active when not in that specific user flow.
 * This is the controller dedicated to the Select Game user flow
 */
class SelectGameFlowSnackbarController(
    delegate: SnackbarController = SnackbarControllerImpl()
): SnackbarController by delegate