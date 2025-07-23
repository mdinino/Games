package dinino.marc.games.userflow.tictactoe.ui.screen.selectneworresumegame

import dinino.marc.games.userflow.common.data.Repository
import dinino.marc.games.userflow.common.ui.route.ScreenRouteEvent
import dinino.marc.games.userflow.common.ui.screen.selectneworresumegame.SelectNewOrResumeGameViewModel
import dinino.marc.games.userflow.common.ui.screen.selectneworresumegame.SelectNewOrResumeGameOneTimeEvent
import dinino.marc.games.userflow.tictactoe.data.TicTacToeGame
import dinino.marc.games.userflow.tictactoe.ui.screen.gameover.TicTacToeGameOverRoute

class TicTacToeSelectNewOrResumeGameViewModel(
    repository: Repository<TicTacToeGame>
): SelectNewOrResumeGameViewModel
<TicTacToeGame, TicTacToeSelectNewOrResumeGameState, SelectNewOrResumeGameOneTimeEvent>(
    repository = repository,
    stateFactory = { TicTacToeSelectNewOrResumeGameState(isSelectResumeGameAvailable = it) },
    navigateToNewGameEventFactory = { SelectNewGame },
    navigateToResumeGameEventFactory = { SelectResumeGame },
)

private data object SelectNewGame : SelectNewOrResumeGameOneTimeEvent.Navigate {
    override val routeEvent get() = ScreenRouteEvent(TicTacToeGameOverRoute)
}

private data object SelectResumeGame : SelectNewOrResumeGameOneTimeEvent.Navigate {
    override val routeEvent get() = ScreenRouteEvent(TicTacToeGameOverRoute)
}
