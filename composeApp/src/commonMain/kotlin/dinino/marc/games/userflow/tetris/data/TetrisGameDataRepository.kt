package dinino.marc.games.userflow.tetris.data

import dinino.marc.games.serialization.DefaultJsonConverter
import dinino.marc.games.serialization.JsonConverter
import dinino.marc.games.userflow.common.data.DefaultJsonLocalDatabaseEndpoint
import dinino.marc.games.userflow.common.data.Repository
import dinino.marc.games.userflow.common.data.SerializableJsonRepository

class TetrisGameDataRepository(
    queries: TetrisGameEntityQueries,
    jsonConverter: JsonConverter<TetrisGameData> = DefaultJsonConverter(TetrisGameData.serializer())
): Repository<TetrisGameData> by SerializableJsonRepository(
    jsonLocalDatabaseEndpoint = DefaultJsonLocalDatabaseEndpoint(
        jsonConverter = jsonConverter,
        getAllUuids = { queries.getAllUuids().executeAsList() },
        getItemByUuid = { uuid -> queries.getJsonByUuid(uuid).executeAsList()?.firstOrNull() },
        upsertItemByUuid = { uuid, json -> queries.upsert(uuid, json) },
        clearAll = { queries.clearAll() }
    )
)