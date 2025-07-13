package dinino.marc.games.userflow.tictactoe.ui.screen.selectneworresumegame

import androidx.lifecycle.ViewModel
import dinino.marc.games.userflow.common.data.Repository
import dinino.marc.games.userflow.common.ui.route.SerializableUserFlowRoute
import dinino.marc.games.userflow.common.ui.selectneworresumegame.DefaultSelectNewOrResumeGameViewModel
import dinino.marc.games.userflow.common.ui.selectneworresumegame.SelectNewOrResumeGameOneTimeEvent
import dinino.marc.games.userflow.common.ui.selectneworresumegame.SelectNewOrResumeGameViewModel
import dinino.marc.games.userflow.tictactoe.data.TicTacToeGame

class TicTacToeSelectNewOrResumeGameViewModel(
    repository: Repository<TicTacToeGame>
): SelectNewOrResumeGameViewModel<TicTacToeSelectNewOrResumeGameState, SelectNewOrResumeGameOneTimeEvent>
by DefaultSelectNewOrResumeGameViewModel(
    repository = repository,
    stateFactory = { TicTacToeSelectNewOrResumeGameState(isSelectResumeGameAvailable = it) },
    selectNewGameEventFactory = { SelectNewGame },
    selectResumeGameEventFactory = { SelectResumeGame },
), ViewModel()

private data object SelectNewGame : SelectNewOrResumeGameOneTimeEvent.Navigate {
    override val route: SerializableUserFlowRoute
        get() = TODO("Not yet implemented")
}

private data object SelectResumeGame : SelectNewOrResumeGameOneTimeEvent.Navigate {
    override val route: SerializableUserFlowRoute
        get() = TODO("Not yet implemented")
}
