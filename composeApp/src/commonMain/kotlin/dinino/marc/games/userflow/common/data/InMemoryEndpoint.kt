package dinino.marc.games.userflow.common.data

import dinino.marc.games.coroutine.CoroutineCriticalSection
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

class InMemoryEndpoint<T: Any>(
    initial: List<RepositoryEntry<T>> = emptyList(),
    private val cs: CoroutineCriticalSection = CoroutineCriticalSection()
) : Repository.Endpoint<T> {
    private var entries: List<RepositoryEntry<T>> = initial

    override suspend fun getEntries() =
        cs.lockAndRun { entries }

    override suspend fun setEntries(entries: List<RepositoryEntry<T>>) =
        cs.lockAndRun { this.entries = entries }

    override suspend fun clearEntries() =
        cs.lockAndRun { this.entries = emptyList() }
}