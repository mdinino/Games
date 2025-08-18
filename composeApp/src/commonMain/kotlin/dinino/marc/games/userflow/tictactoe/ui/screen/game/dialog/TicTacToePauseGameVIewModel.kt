package dinino.marc.games.userflow.tictactoe.ui.screen.game.dialog

import dinino.marc.games.userflow.common.data.GamePlayData
import dinino.marc.games.userflow.common.data.Repository
import dinino.marc.games.userflow.common.ui.screen.dialog.pause.PauseGameViewModel
import dinino.marc.games.userflow.tictactoe.data.TicTacToeGameData
import dinino.marc.games.userflow.tictactoe.di.TicTacToeUserFlowProviders
import org.koin.mp.KoinPlatform

class TicTacToePauseGameViewModel(
    repository: Repository<TicTacToeGameData> = defaultRepository,
    defaultGameData: ()->TicTacToeGameData = { TicTacToeGameData() },
) : PauseGameViewModel<TicTacToeGameData>(
    repository = repository,
    defaultGameData = defaultGameData,
    setGameUnPaused = { it.copy(playData = GamePlayData.Normal()) },
    setUserInitiatedGameOver = { it.copy(playData = GamePlayData.GameOver()) }
)

private val defaultRepository
    get() = KoinPlatform.getKoin()
        .get<TicTacToeUserFlowProviders>()
        .repositoryProvider
        .provide()