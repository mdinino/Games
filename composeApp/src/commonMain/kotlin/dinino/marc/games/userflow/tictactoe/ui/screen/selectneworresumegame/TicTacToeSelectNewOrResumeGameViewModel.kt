package dinino.marc.games.userflow.tictactoe.ui.screen.selectneworresumegame

import dinino.marc.games.userflow.common.data.Repository
import dinino.marc.games.userflow.common.ui.route.SerializableUserFlowRoute
import dinino.marc.games.userflow.common.ui.screen.selectneworresumegame.AbstractSelectNewOrResumeGameViewModel
import dinino.marc.games.userflow.common.ui.screen.selectneworresumegame.SelectNewOrResumeGameOneTimeEvent
import dinino.marc.games.userflow.tictactoe.data.TicTacToeGame

class TicTacToeSelectNewOrResumeGameViewModel(
    repository: Repository<TicTacToeGame>
): AbstractSelectNewOrResumeGameViewModel
<TicTacToeGame, TicTacToeSelectNewOrResumeGameState, SelectNewOrResumeGameOneTimeEvent>(
    repository = repository,
    stateFactory = { TicTacToeSelectNewOrResumeGameState(isSelectResumeGameAvailable = it) },
    navigateToNewGameEventFactory = { SelectNewGame },
    navigateToResumeGameEventFactory = { SelectResumeGame },
)

private data object SelectNewGame : SelectNewOrResumeGameOneTimeEvent.Navigate {
    override val route: SerializableUserFlowRoute
        get() = TODO("Not yet implemented")
}

private data object SelectResumeGame : SelectNewOrResumeGameOneTimeEvent.Navigate {
    override val route: SerializableUserFlowRoute
        get() = TODO("Not yet implemented")
}
