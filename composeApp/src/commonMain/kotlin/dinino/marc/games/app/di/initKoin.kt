package dinino.marc.games.app.di

import dinino.marc.games.userflow.selectgame.di.selectGameUserFlowModule
import dinino.marc.games.userflow.tictactoe.di.ticTacToeUserFLowModule
import org.koin.core.context.startKoin

fun initKoin() {
    startKoin {
        modules(appModule,
            selectGameUserFlowModule,
            ticTacToeUserFLowModule
        )
    }
}