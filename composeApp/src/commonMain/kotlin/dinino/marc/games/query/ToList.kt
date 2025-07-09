package dinino.marc.games.query

import app.cash.sqldelight.Query
import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.last

suspend fun <T : Any> Query<T>.toList(): List<T> =
    asFlow()
        .mapToList(Dispatchers.Default)
        .last()