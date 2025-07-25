package dinino.marc.games.userflow.common.data

import dinino.marc.games.coroutine.CoroutineCriticalSection
import dinino.marc.games.serialization.JsonConverter
import dinino.marc.games.serialization.JsonString

/**
 * A Repository that converts data to/from JSON before getting/settings items.
 */
@Suppress("FunctionName")
fun <T: Any> SerializableJsonRepository(
    jsonLocalDatabaseEndpoint: JsonEndpoint<T>,
    otherEndpoints: List<JsonEndpoint<T>> = emptyList()
): Repository<T> =
    DefaultRepository(
        remoteEndpoints = listOf(jsonLocalDatabaseEndpoint) + otherEndpoints
    )

interface JsonEndpoint<T: Any>: Repository.Endpoint<T> {
    val jsonConverter: JsonConverter<T>
    val getAllUuids: suspend ()->List<String>
    val getItemByUuid: suspend (uuid: String)-> JsonString?
    val upsertItemByUuid: suspend (uuid: String, item: JsonString)->Unit
    val clearAll: suspend ()->Unit
}

class DefaultJsonLocalDatabaseEndpoint<T: Any> (
    override val jsonConverter: JsonConverter<T>,
    override val getAllUuids: suspend ()->List<String>,
    override val getItemByUuid: suspend (uuid: String)->JsonString?,
    override val upsertItemByUuid: suspend (uuid: String, item: JsonString)->Unit,
    override val clearAll: suspend ()->Unit,
    private val cs: CoroutineCriticalSection = CoroutineCriticalSection()
): JsonEndpoint<T> {
    override suspend fun getEntries(): List<RepositoryEntry<T>> =
        cs.lockAndRun {
            getAllUuids.invoke()
                .mapNotNull { uuid ->
                    RepositoryEntry(
                        uuid = uuid,
                        item = getItemByUuid.invoke(uuid)?.deserialize() ?: return@mapNotNull null
                    )
                }
        }

    override suspend fun setEntries(entries: List<RepositoryEntry<T>>) =
        cs.lockAndRunNonCancelable {
            clearAll.invoke()
            entries.forEach { entry ->
                upsertItemByUuid(
                    entry.uuid,
                    entry.item
                        .serialize()
                )
            }
        }

    override suspend fun clearEntries() =
        cs.lockAndRunNonCancelable { clearAll.invoke() }

    private fun JsonString.deserialize(): T =
        jsonConverter.convertFromJson(this)

    private fun T.serialize(): JsonString =
        jsonConverter.convertToJson(this)
}

