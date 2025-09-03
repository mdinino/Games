package dinino.marc.games.userflow.common.domain

import dinino.marc.games.userflow.common.data.Repository
import dinino.marc.games.userflow.common.data.Repository.Companion.hasEntry
import dinino.marc.games.userflow.common.data.Repository.Companion.lastestItem

class GameUseCasesImpl<in REPOSITORY: Repository<T>, T: Any>(
    override val hasEntry: GameUseCases.HasEntry<REPOSITORY, T>,
    override val clearEntries: GameUseCases.ClearEntries<REPOSITORY, T>,
    override val getLatestStatus: GameUseCases.GetLatestStatus<REPOSITORY, T>,
    override val getStatus: GameUseCases.GetStatus<REPOSITORY, T>,
    override val upsertLatestItemIfDifferent: GameUseCases.UpsertLatestItemIfDifferent<REPOSITORY, T>
): GameUseCases<REPOSITORY, T> {

    constructor(repository: REPOSITORY)
            : this(
        hasEntry = HasEntryUseCase(repository),
        clearEntries = ClearEntriesUseCase(repository),
        getLatestStatus = GetLatestStatusUseCase(repository),
        getStatus = GetStatusUseCase(repository),
        upsertLatestItemIfDifferent = UpsertLatestItemIfDifferentUseCase(repository)
    )

    companion object {
        class HasEntryUseCase<in REPOSITORY: Repository<T>, T: Any>(
            private val repository: REPOSITORY
        ) : GameUseCases.HasEntry<REPOSITORY, T> {
            override fun invoke() = repository.hasEntry
        }

        class GetStatusUseCase<in REPOSITORY: Repository<T>, T: Any>(
            private val repository: REPOSITORY
        ) : GameUseCases.GetStatus<REPOSITORY, T> {
            override fun invoke() = repository.status
        }

        class GetLatestStatusUseCase<in REPOSITORY: Repository<T>, T: Any>(
            private val repository: REPOSITORY,
        ) : GameUseCases.GetLatestStatus<REPOSITORY, T>  {
            override fun invoke() = repository.lastestItem
        }

        class UpsertLatestItemIfDifferentUseCase<in REPOSITORY: Repository<T>, T: Any>(
            private val repository: REPOSITORY
        ) : GameUseCases.UpsertLatestItemIfDifferent<REPOSITORY, T> {
            override suspend fun invoke(item: T) = repository.upsertLatestItemIfDifferent(item)
        }

        class ClearEntriesUseCase<in REPOSITORY: Repository<T>, T: Any>(
            private val repository: REPOSITORY
        ) : GameUseCases.ClearEntries<REPOSITORY, T> {
            override suspend fun invoke() = repository.clearEntries()
        }
    }
}

