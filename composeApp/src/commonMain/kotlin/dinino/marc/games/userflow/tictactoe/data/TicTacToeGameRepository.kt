package dinino.marc.games.userflow.tictactoe.data

import dinino.marc.games.query.toList
import dinino.marc.games.userflow.common.data.DefaultJsonConverter
import dinino.marc.games.userflow.common.data.DefaultJsonLocalDatabaseEndpoint
import dinino.marc.games.userflow.common.data.JsonConverter
import dinino.marc.games.userflow.common.data.Repository
import dinino.marc.games.userflow.common.data.SerializableJsonRepository

class TicTacToeGameRepository(
    queries: TicTacToeGameEntityQueries,
    jsonConverter: JsonConverter<TicTacToeGame> = DefaultJsonConverter(TicTacToeGame.serializer())
): Repository<TicTacToeGame> by SerializableJsonRepository(
        jsonLocalDatabaseEndpoint = DefaultJsonLocalDatabaseEndpoint(
            jsonConverter = jsonConverter,
            getAllUuids = { queries.getAllUuids().toList() },
            getItemByUuid = { uuid -> queries.getJsonByUuid(uuid).toList().firstOrNull() },
            upsertItemByUuid = { uuid, json -> queries.upsert(uuid, json) },
            clearAll = { queries.clearAll() }
        )
)