package dinino.marc.games.userflow.common.di

import dinino.marc.games.userflow.common.data.Repository

interface GameUserFlowProviders<GAME: Any>: UserFlowProviders {
    val repositoryProvider: RepositoryProvider<GAME>

    interface RepositoryProvider<GAME: Any> {
        fun provide(): Repository<GAME>
    }
}