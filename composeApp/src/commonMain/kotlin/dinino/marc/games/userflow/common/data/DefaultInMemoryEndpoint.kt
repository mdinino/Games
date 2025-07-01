package dinino.marc.games.userflow.common.data

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

class DefaultInMemoryEndpoint<SERIALIZABLE_TYPE: Any>(
    private val initial: SERIALIZABLE_TYPE? = null,
    private val coroutineContext: CoroutineContext,
    private val mutex: Mutex = Mutex()
) : GameRepository.Endpoint.InMemory<SERIALIZABLE_TYPE> {

    constructor(
        initial: SERIALIZABLE_TYPE? = null,
        coroutineScope: CoroutineScope = CoroutineScope(Dispatchers.Default),
        mutex: Mutex = Mutex()
    ) : this(
        initial = initial,
        coroutineContext = coroutineScope.coroutineContext,
        mutex = mutex
    )

    private var item: SERIALIZABLE_TYPE? = initial

    override suspend fun getItem() =
        lockAndRun { item }

    override suspend fun setItemIfDifferent(item: SERIALIZABLE_TYPE?) =
        lockAndRun {
            if (this.item != item) this.item = item
        }

    private suspend fun <T> lockAndRun(action: ()->T): T =
        withContext(coroutineContext) {
            mutex.withLock {
                action()
            }
        }
}