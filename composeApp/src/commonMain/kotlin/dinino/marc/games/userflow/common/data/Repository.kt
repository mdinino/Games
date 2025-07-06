package dinino.marc.games.userflow.common.data

import kotlinx.coroutines.flow.StateFlow
import kotlin.uuid.ExperimentalUuidApi

@OptIn(ExperimentalUuidApi::class)
interface Repository<T: Any> {
    val status: StateFlow<SyncStatus<T>>


    val localCache: Endpoint<T>
    val remoteEndpoints: List<Endpoint<T>>

    val autoSync: Boolean
    suspend fun syncFromRemotesToLocal()
    suspend fun syncFromLocalToRemotes()

    suspend fun setEntriesIfDifferent(entries: List<RepositoryEntry<T>>)
    suspend fun clearEntries() = setEntriesIfDifferent(emptyList())

    sealed interface Endpoint<T: Any> {
        suspend fun getEntries(): List<RepositoryEntry<T>>
        suspend fun setEntries(entries: List<RepositoryEntry<T>>)
    }
}