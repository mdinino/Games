package dinino.marc.games.userflow.common.di

import dinino.marc.games.userflow.common.data.Repository
import dinino.marc.games.userflow.common.domain.GameUseCases

interface GameUserFlowProviders<GAME_DATA: Any>: UserFlowProviders {
    val useCasesProvider: UseCasesProvider<GAME_DATA>

    interface UseCasesProvider<GAME_DATA: Any> {
        fun provide(): GameUseCases<Repository<GAME_DATA>, GAME_DATA>
    }
}