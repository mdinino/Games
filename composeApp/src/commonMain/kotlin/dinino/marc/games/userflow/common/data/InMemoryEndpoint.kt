package dinino.marc.games.userflow.common.data

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

class InMemoryEndpoint<T: Any>(
    initial: List<RepositoryEntry<T>> = emptyList(),
    private val coroutineContext: CoroutineContext =
        CoroutineScope(Dispatchers.Default).coroutineContext,
    private val mutex: Mutex =
        Mutex()
) : Repository.Endpoint<T> {

    private var entries: List<RepositoryEntry<T>> = initial

    override suspend fun getEntries() =
        lockAndRun { entries }

    override suspend fun setEntries(entries: List<RepositoryEntry<T>>) =
        lockAndRun { this.entries = entries }

    override suspend fun clearEntries() =
        lockAndRun { this.entries = emptyList() }

    private suspend fun <R> lockAndRun(block : suspend ()->R): R =
        withContext(coroutineContext) {
            mutex.withLock {
                block()
            }
        }
}