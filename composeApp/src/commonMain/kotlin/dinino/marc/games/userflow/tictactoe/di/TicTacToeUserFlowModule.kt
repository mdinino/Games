package dinino.marc.games.userflow.tictactoe.di

import dinino.marc.games.userflow.tictactoe.ui.screen.game.TicTacToeGameViewModel
import dinino.marc.games.userflow.tictactoe.ui.screen.game.dialog.TicTacToePauseGameViewModel
import dinino.marc.games.userflow.tictactoe.ui.screen.gameover.TicTacToeGameOverViewModel
import dinino.marc.games.userflow.tictactoe.ui.screen.selectneworresumegame.TicTacToeSelectNewOrResumeGameViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val ticTacToeUserFlowModule = module {
    single { TicTacToeUserFlowProviders() }
    viewModelOf(::TicTacToeSelectNewOrResumeGameViewModel)
    viewModelOf(::TicTacToePauseGameViewModel)
    viewModel { (newGame: Boolean) ->
        TicTacToeGameViewModel(
            newGame = newGame
        )
    }
    viewModelOf(::TicTacToeGameOverViewModel)
}