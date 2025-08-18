package dinino.marc.games.userflow.tetris.ui.screen.game.dialog

import dinino.marc.games.userflow.common.data.GamePlayData
import dinino.marc.games.userflow.common.data.Repository
import dinino.marc.games.userflow.common.ui.screen.dialog.pause.PauseGameViewModel
import dinino.marc.games.userflow.tetris.data.TetrisGameData
import dinino.marc.games.userflow.tetris.di.TetrisUserFlowProviders
import org.koin.mp.KoinPlatform

class TetrisPauseGameViewModel(
    repository: Repository<TetrisGameData> = defaultRepository,
    defaultGameData: ()->TetrisGameData = { TetrisGameData() },
) : PauseGameViewModel<TetrisGameData>(
    repository = repository,
    defaultGameData = defaultGameData,
    setGameUnPaused = { it.copy(playData = GamePlayData.Normal()) },
    setUserInitiatedGameOver = { it.copy(playData = GamePlayData.GameOver()) }
)

private val defaultRepository
    get() = KoinPlatform.getKoin()
        .get<TetrisUserFlowProviders>()
        .repositoryProvider
        .provide()