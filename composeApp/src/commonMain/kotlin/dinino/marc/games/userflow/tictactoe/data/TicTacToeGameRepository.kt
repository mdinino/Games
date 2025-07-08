package dinino.marc.games.userflow.tictactoe.data

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import dinino.marc.games.Database
import dinino.marc.games.database.TicTacToeGameEntityQueries
import dinino.marc.games.userflow.common.data.DefaultJsonConverter
import dinino.marc.games.userflow.common.data.DefaultJsonLocalDatabaseEndpoint
import dinino.marc.games.userflow.common.data.JsonConverter
import dinino.marc.games.userflow.common.data.Repository
import dinino.marc.games.userflow.common.data.SerializableJsonRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.last

class TicTacToeGameRepository(
    queries: TicTacToeGameEntityQueries,
    jsonConverter: JsonConverter<TicTacToeGame> = DefaultJsonConverter(TicTacToeGame.serializer())
): Repository<TicTacToeGame> by SerializableJsonRepository(
        jsonLocalDatabaseEndpoint = DefaultJsonLocalDatabaseEndpoint(
            jsonConverter = jsonConverter,
            getAllUuids = { queries.getAllUuids().asFlow().mapToList(Dispatchers.Default).last() },
            getItemByUuid = { uuid -> queries.getJsonByUuid(uuid).asFlow().mapToList(Dispatchers.Default).last().first() },
            upsertItemByUuid = { uuid, json -> queries.upsert(uuid, json) },
            clearAll = { queries.clearAll() }
        )
    ) { constructor(database: Database): this(database.ticTacToeGameEntityQueries) }