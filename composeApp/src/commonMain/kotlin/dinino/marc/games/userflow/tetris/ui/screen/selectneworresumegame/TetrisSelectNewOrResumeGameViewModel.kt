package dinino.marc.games.userflow.tetris.ui.screen.selectneworresumegame

import dinino.marc.games.userflow.common.data.Repository
import dinino.marc.games.userflow.common.ui.route.ScreenRouteEvent
import dinino.marc.games.userflow.common.ui.screen.selectneworresumegame.SelectNewOrResumeGameViewModel
import dinino.marc.games.userflow.common.ui.screen.selectneworresumegame.SelectNewOrResumeGameOneTimeEvent
import dinino.marc.games.userflow.tetris.data.TetrisGame
import dinino.marc.games.userflow.tetris.ui.screen.gameover.TetrisGameOverRoute

class TetrisSelectNewOrResumeGameViewModel(
    repository: Repository<TetrisGame>
): SelectNewOrResumeGameViewModel
    <TetrisGame, TetrisSelectNewOrResumeGameState, SelectNewOrResumeGameOneTimeEvent>(
    repository = repository,
    stateFactory = { TetrisSelectNewOrResumeGameState(isSelectResumeGameAvailable = it) },
    navigateToNewGameEventFactory = { SelectNewGame },
    navigateToResumeGameEventFactory = { SelectResumeGame },
)

private data object SelectNewGame : SelectNewOrResumeGameOneTimeEvent.Navigate {
    override val routeEvent get() = ScreenRouteEvent(TetrisGameOverRoute)
}

private data object SelectResumeGame : SelectNewOrResumeGameOneTimeEvent.Navigate {
    override val routeEvent get() = ScreenRouteEvent(TetrisGameOverRoute)
}