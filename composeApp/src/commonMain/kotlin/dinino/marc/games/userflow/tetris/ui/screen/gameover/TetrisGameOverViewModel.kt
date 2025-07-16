package dinino.marc.games.userflow.tetris.ui.screen.gameover

import dinino.marc.games.userflow.common.ui.route.SerializableUserFlowRoute
import dinino.marc.games.userflow.common.ui.screen.gameover.GameOverOneTimeEvent
import dinino.marc.games.userflow.common.ui.screen.gameover.GameOverViewModel
import dinino.marc.games.userflow.selectgame.ui.SelectGameScreenRoute

class TetrisGameOverViewModel(
    state: TetrisGameOverState = TetrisGameOverState()
): GameOverViewModel<TetrisGameOverState, GameOverOneTimeEvent>(
        initialState = state,
        navigateToNewGameEventFactory = { NewGame },
        navigateToDifferentGameEventFactory = { DifferentGame }
)

private data object NewGame : GameOverOneTimeEvent.Navigate {
    override val route: SerializableUserFlowRoute
        get() = TODO("Not yet implemented")
}

private data object DifferentGame : GameOverOneTimeEvent.Navigate {
    override val route: SerializableUserFlowRoute
        get() = SelectGameScreenRoute
}
