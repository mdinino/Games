package dinino.marc.games.userflow.common.data

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

class RoomPersistentEndpoint<SERIALIZABLE_TYPE: Any>(
    private val coroutineContext: CoroutineContext,
    private val mutex: Mutex = Mutex()
) : GameRepository.Endpoint.Persistent<SERIALIZABLE_TYPE> {

    constructor(
        coroutineScope: CoroutineScope = CoroutineScope(Dispatchers.Default),
        mutex: Mutex = Mutex()
    ) : this(
        coroutineContext = coroutineScope.coroutineContext,
        mutex = mutex
    )

    override suspend fun getItem(): SERIALIZABLE_TYPE? {
        TODO("Not yet implemented")
    }

    override suspend fun setItemIfDifferent(item: SERIALIZABLE_TYPE?) {
        TODO("Not yet implemented")
    }

    private suspend fun <T> lockAndRun(action: ()->T): T =
        withContext(coroutineContext) {
            mutex.withLock {
                action()
            }
        }
}