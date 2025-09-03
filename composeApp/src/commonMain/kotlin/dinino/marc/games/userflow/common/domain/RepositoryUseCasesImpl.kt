package dinino.marc.games.userflow.common.domain

import dinino.marc.games.userflow.common.data.Repository
import dinino.marc.games.userflow.common.data.Repository.Companion.lastestItem

class RepositoryUseCasesImpl<in REPOSITORY: Repository<T>, T: Any>(
    override val clearEntries: RepositoryUseCases.ClearEntries<REPOSITORY, T>,
    override val getLatestStatus: RepositoryUseCases.GetLatestStatus<REPOSITORY, T>,
    override val getStatus: RepositoryUseCases.GetStatus<REPOSITORY, T>,
    override val upsertLatestItemIfDifferent: RepositoryUseCases.UpsertLatestItemIfDifferent<REPOSITORY, T>
): RepositoryUseCases<REPOSITORY, T> {

    constructor(repository: REPOSITORY)
            : this(
        clearEntries = ClearEntriesUseCase(repository),
        getLatestStatus = GetLatestStatusUseCase(repository),
        getStatus = GetStatusUseCase(repository),
        upsertLatestItemIfDifferent = UpsertLatestItemIfDifferentUseCase(repository)
    )

    companion object {
        class GetStatusUseCase<in REPOSITORY: Repository<T>, T: Any>(
            private val repository: REPOSITORY
        ) : RepositoryUseCases.GetStatus<REPOSITORY, T> {
            override fun invoke() = repository.status
        }

        class GetLatestStatusUseCase<in REPOSITORY: Repository<T>, T: Any>(
            private val repository: REPOSITORY,
        ) : RepositoryUseCases.GetLatestStatus<REPOSITORY, T>  {
            override fun invoke() = repository.lastestItem
        }

        class UpsertLatestItemIfDifferentUseCase<in REPOSITORY: Repository<T>, T: Any>(
            private val repository: REPOSITORY
        ) : RepositoryUseCases.UpsertLatestItemIfDifferent<REPOSITORY, T> {
            override suspend fun invoke(item: T) = repository.upsertLatestItemIfDifferent(item)
        }

        class ClearEntriesUseCase<in REPOSITORY: Repository<T>, T: Any>(
            private val repository: REPOSITORY
        ) : RepositoryUseCases.ClearEntries<REPOSITORY, T> {
            override suspend fun invoke() = repository.clearEntries()
        }
    }
}

