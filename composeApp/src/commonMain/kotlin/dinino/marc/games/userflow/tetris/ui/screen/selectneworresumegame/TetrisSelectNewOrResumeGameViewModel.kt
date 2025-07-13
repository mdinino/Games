package dinino.marc.games.userflow.tetris.ui.screen.selectneworresumegame

import androidx.lifecycle.ViewModel
import dinino.marc.games.userflow.common.data.Repository
import dinino.marc.games.userflow.common.ui.onetimeevent.DefaultNavigateOneTimeEvent
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
    selectNewGameEventFactory = { object : SelectNewOrResumeGameOneTimeEvent.Navigate
        by DefaultNavigateOneTimeEvent(TetrisNavGraphRoute){} },
    selectResumeGameEventFactory = { object : SelectNewOrResumeGameOneTimeEvent.Navigate
        by DefaultNavigateOneTimeEvent(TetrisNavGraphRoute){} },
), ViewModel()