package dinino.marc.games.userflow.tetris.ui.screen.selectneworresumegame

import androidx.lifecycle.ViewModel
import dinino.marc.games.userflow.common.data.Repository
import dinino.marc.games.userflow.common.ui.route.SerializableUserFlowRoute
import dinino.marc.games.userflow.common.ui.selectneworresumegame.DefaultSelectNewOrResumeGameViewModel
import dinino.marc.games.userflow.common.ui.selectneworresumegame.SelectNewOrResumeGameOneTimeEvent
import dinino.marc.games.userflow.common.ui.selectneworresumegame.SelectNewOrResumeGameViewModel
import dinino.marc.games.userflow.tetris.data.TetrisGame

class TetrisSelectNewOrResumeGameViewModel(
    repository: Repository<TetrisGame>
): SelectNewOrResumeGameViewModel<TetrisSelectNewOrResumeGameState, SelectNewOrResumeGameOneTimeEvent>
by DefaultSelectNewOrResumeGameViewModel(
    repository = repository,
    stateFactory = { TetrisSelectNewOrResumeGameState(isSelectResumeGameAvailable = it) },
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