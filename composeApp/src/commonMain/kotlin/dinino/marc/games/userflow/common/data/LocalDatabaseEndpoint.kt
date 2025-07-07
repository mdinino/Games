package dinino.marc.games.userflow.common.data

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

class LocalDatabaseEndpoint<T: Any>(
    private val doGetEntries: suspend ()->List<RepositoryEntry<T>>,
    private val doSetEnty: suspend (RepositoryEntry<T>)->Unit,
    private val doClearEntries: suspend ()->Unit,
    private val coroutineContext: CoroutineContext =
        CoroutineScope(Dispatchers.Default).coroutineContext,
    private val mutex: Mutex =
        Mutex()
): Repository.Endpoint<T> {

    override suspend fun getEntries(): List<RepositoryEntry<T>> =
        lockAndRun { doGetEntries() }

    override suspend fun setEntries(entries: List<RepositoryEntry<T>>) =
        lockAndRun {
            doClearEntries()
            entries.forEach { entry ->
                doSetEnty(entry)
            }
        }

    override suspend fun clearEntries() =
        lockAndRun { doClearEntries() }

    private suspend fun <R> lockAndRun(block : suspend ()->R): R =
        withContext(coroutineContext) {
            mutex.withLock {
                block()
            }
        }
}