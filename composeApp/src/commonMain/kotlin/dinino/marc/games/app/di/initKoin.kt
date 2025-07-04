package dinino.marc.games.app.di

import dinino.marc.games.userflow.selectgame.di.selectGameUserFlowModule
import dinino.marc.games.userflow.tictactoe.di.ticTacToeUserFLowModule
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration

fun initKoin(config: KoinAppDeclaration? = null) {
    startKoin {
        config?.invoke(this)
        modules(appModule,
            selectGameUserFlowModule,
            ticTacToeUserFLowModule
        )
    }
}