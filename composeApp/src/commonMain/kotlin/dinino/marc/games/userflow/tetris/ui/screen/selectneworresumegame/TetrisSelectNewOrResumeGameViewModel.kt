package dinino.marc.games.userflow.tetris.ui.screen.selectneworresumegame

import dinino.marc.games.userflow.common.data.Repository
import dinino.marc.games.userflow.common.ui.route.SerializableUserFlowRoute
import dinino.marc.games.userflow.common.ui.screen.selectneworresumegame.AbstractSelectNewOrResumeGameViewModel
import dinino.marc.games.userflow.common.ui.screen.selectneworresumegame.SelectNewOrResumeGameOneTimeEvent
import dinino.marc.games.userflow.tetris.data.TetrisGame

class TetrisSelectNewOrResumeGameViewModel(
    repository: Repository<TetrisGame>
): AbstractSelectNewOrResumeGameViewModel
    <TetrisGame, TetrisSelectNewOrResumeGameState, SelectNewOrResumeGameOneTimeEvent>(
    repository = repository,
    stateFactory = { TetrisSelectNewOrResumeGameState(isSelectResumeGameAvailable = it) },
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