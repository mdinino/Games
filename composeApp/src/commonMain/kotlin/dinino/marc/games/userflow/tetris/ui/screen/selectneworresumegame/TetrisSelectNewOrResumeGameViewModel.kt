package dinino.marc.games.userflow.tetris.ui.screen.selectneworresumegame

import dinino.marc.games.userflow.common.data.Repository
import dinino.marc.games.userflow.common.ui.screen.selectneworresumegame.SelectNewOrResumeGameViewModel
import dinino.marc.games.userflow.tetris.data.TetrisGame

class TetrisSelectNewOrResumeGameViewModel(
    repository: Repository<TetrisGame>
): SelectNewOrResumeGameViewModel
    <TetrisGame, TetrisSelectNewOrResumeGameState>(
    repository = repository,
    stateFactory = { TetrisSelectNewOrResumeGameState(isSelectResumeGameAvailable = it) },
)