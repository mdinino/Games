package dinino.marc.games.userflow.tictactoe.ui.screen.selectneworresumegame

import androidx.lifecycle.ViewModel
import dinino.marc.games.userflow.common.data.Repository
import dinino.marc.games.userflow.common.ui.onetimeevent.DefaultNavigateOneTimeEvent
import dinino.marc.games.userflow.common.ui.selectneworresumegame.DefaultSelectNewOrResumeGameViewModel
import dinino.marc.games.userflow.common.ui.selectneworresumegame.SelectNewOrResumeGameOneTimeEvent
import dinino.marc.games.userflow.common.ui.selectneworresumegame.SelectNewOrResumeGameViewModel
import dinino.marc.games.userflow.tictactoe.data.TicTacToeGame
import dinino.marc.games.userflow.tictactoe.ui.TicTacToeNavGraphRoute

class TicTacToeSelectNewOrResumeGameViewModel(
    repository: Repository<TicTacToeGame>
): SelectNewOrResumeGameViewModel<TicTacToeSelectNewOrResumeGameState, SelectNewOrResumeGameOneTimeEvent>
by DefaultSelectNewOrResumeGameViewModel(
    repository = repository,
    stateFactory = { TicTacToeSelectNewOrResumeGameState(isSelectResumeGameAvailable = it) },
    selectNewGameEventFactory = { object : SelectNewOrResumeGameOneTimeEvent.Navigate
        by DefaultNavigateOneTimeEvent(TicTacToeNavGraphRoute){} },
    selectResumeGameEventFactory = { object : SelectNewOrResumeGameOneTimeEvent.Navigate
        by DefaultNavigateOneTimeEvent(TicTacToeNavGraphRoute){} },
), ViewModel()