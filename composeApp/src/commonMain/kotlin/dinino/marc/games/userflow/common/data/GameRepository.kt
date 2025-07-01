package dinino.marc.games.userflow.common.data

import kotlinx.coroutines.flow.StateFlow

interface GameRepository<SERIALIZABLE_TYPE: Any> {
    val status: StateFlow<SyncStatus<SERIALIZABLE_TYPE>>

    suspend fun syncFromRemote()
    suspend fun syncToRemote()

    suspend fun updateItemIfDifferent(new: SERIALIZABLE_TYPE)
    suspend fun clearItem()

    sealed interface Endpoint<SERIALIZABLE_TYPE: Any> {
        suspend fun getItem(): SERIALIZABLE_TYPE?
        suspend fun setItemIfDifferent(item: SERIALIZABLE_TYPE?)

        interface InMemory<SERIALIZABLE_TYPE: Any>: Endpoint<SERIALIZABLE_TYPE>
        interface Persistent<SERIALIZABLE_TYPE: Any>: Endpoint<SERIALIZABLE_TYPE>
    }
}