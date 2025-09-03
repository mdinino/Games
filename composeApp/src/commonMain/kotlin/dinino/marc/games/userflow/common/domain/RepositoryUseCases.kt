package dinino.marc.games.userflow.common.domain

import dinino.marc.games.userflow.common.data.Repository
import dinino.marc.games.userflow.common.data.RepositoryEntry
import dinino.marc.games.userflow.common.data.SyncStatus
import kotlinx.coroutines.flow.StateFlow

interface RepositoryUseCases<in REPOSITORY: Repository<T>, T: Any> {
    val hasEntry: HasEntry<REPOSITORY, T>
    val getLatestStatus: GetLatestStatus<REPOSITORY, T>
    val getStatus: GetStatus<REPOSITORY, T>
    val upsertLatestItemIfDifferent: UpsertLatestItemIfDifferent<REPOSITORY, T>
    val clearEntries: ClearEntries<REPOSITORY, T>

    interface HasEntry<in REPOSITORY: Repository<T>, T: Any>: ()->StateFlow<Boolean>

    interface GetStatus<in REPOSITORY: Repository<T>, T: Any>: ()->StateFlow<SyncStatus<T>>

    interface GetLatestStatus<in REPOSITORY: Repository<T>, T: Any>: ()->StateFlow<T?>

    interface UpsertLatestItemIfDifferent<in REPOSITORY: Repository<T>, T: Any>: suspend (T)->RepositoryEntry<T>

    interface ClearEntries<in REPOSITORY: Repository<T>, T: Any>: suspend ()->Unit
}



