package dinino.marc.games.userflow.tetris.data

import dinino.marc.games.query.toList
import dinino.marc.games.serialization.DefaultJsonConverter
import dinino.marc.games.serialization.JsonConverter
import dinino.marc.games.userflow.common.data.DefaultJsonLocalDatabaseEndpoint
import dinino.marc.games.userflow.common.data.Repository
import dinino.marc.games.userflow.common.data.SerializableJsonRepository

class TetrisGameRepository(
    queries: TetrisGameEntityQueries,
    jsonConverter: JsonConverter<TetrisGame> = DefaultJsonConverter(TetrisGame.serializer())
): Repository<TetrisGame> by SerializableJsonRepository(
    jsonLocalDatabaseEndpoint = DefaultJsonLocalDatabaseEndpoint(
        jsonConverter = jsonConverter,
        getAllUuids = { queries.getAllUuids().toList() },
        getItemByUuid = { uuid -> queries.getJsonByUuid(uuid).toList().firstOrNull() },
        upsertItemByUuid = { uuid, json -> queries.upsert(uuid, json) },
        clearAll = { queries.clearAll() }
    )
)