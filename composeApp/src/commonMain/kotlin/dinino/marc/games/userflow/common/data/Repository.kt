package dinino.marc.games.userflow.common.data

import kotlinx.coroutines.flow.StateFlow
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
interface Repository<SERIALIZABLE_TYPE: Any> {
    val status: StateFlow<SyncStatus<SERIALIZABLE_TYPE>>

    suspend fun sync()


    suspend fun upsertLatestIfDifferent(item: SERIALIZABLE_TYPE): Uuid
    suspend fun setItemsIfDifferent(items: List<SERIALIZABLE_TYPE>)
    suspend fun clearItems() = setItemsIfDifferent(emptyList())

    sealed interface Endpoint<SERIALIZABLE_TYPE: Any> {
        suspend fun getItems(): List<SERIALIZABLE_TYPE>
        suspend fun setItems(items: List<SERIALIZABLE_TYPE>)

        interface InMemory<SERIALIZABLE_TYPE: Any>: Endpoint<SERIALIZABLE_TYPE>
        interface Persistent<SERIALIZABLE_TYPE: Any>: Endpoint<SERIALIZABLE_TYPE>
    }
}