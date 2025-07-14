package dinino.marc.games.userflow.common.data

import dinino.marc.games.stateflow.mapStateFlow
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

    /**
     * Puts this item at the tail of the list of repository entries.
     * If identical to the current one, returns and does nothing.
     *
     * @return the latest repository entry.
     * This may be the one just added or the one that was already there
     */
    suspend fun upsertLatestItemIfDifferent(item: T): RepositoryEntry<T>

    suspend fun setEntriesIfDifferent(entries: List<RepositoryEntry<T>>)
    suspend fun clearEntries()

    interface Endpoint<T: Any> {
        suspend fun getEntries(): List<RepositoryEntry<T>>
        suspend fun setEntries(entries: List<RepositoryEntry<T>>)
        suspend fun clearEntries()
    }

    companion object {
        val <T: Any> Repository<T>.lastestItem: StateFlow<T?>
            get() = status.mapStateFlow { it.lastSuccessfulSync?.entries?.last()?.item }

        val <T: Any> Repository<T>.hasEntry: StateFlow<Boolean>
            get() = lastestItem.mapStateFlow { it != null }
    }
}