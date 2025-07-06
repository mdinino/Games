package dinino.marc.games.userflow.common.data

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalTime::class)
class DefaultRepository<T: Any>(
    override val autoSync: Boolean =
        true,
    override val localCache: Repository.Endpoint<T> =
        InMemoryEndpoint(),
    override val remoteEndpoints: List<Repository.Endpoint<T>>,
    private val coroutineContext: CoroutineContext =
        CoroutineScope(Dispatchers.Default).coroutineContext,
    private val mutex: Mutex = Mutex(),
    private val _status: MutableStateFlow<SyncStatus<T>> =
        MutableStateFlow(SyncStatus.NotSynced())
): Repository<T> {

    constructor(
        autoSync: Boolean = true,
        localCache: Repository.Endpoint<T> = InMemoryEndpoint(),
        getItemsFromDatabase: suspend ()->List<RepositoryEntry<T>>,
        setItemsToDatabase: suspend (List<RepositoryEntry<T>>)->Unit,
    ) : this(
        autoSync = autoSync,
        localCache = localCache,
        remoteEndpoints = listOf(
            LocalDatabaseEndpoint(
                doGetItems = getItemsFromDatabase,
                doSetItems = setItemsToDatabase
            )
        )
    )

    override val status = _status.asStateFlow()

    init {
        remoteEndpoints.requireIsNotEmpty()

        if (autoSync) {
            CoroutineScope(Dispatchers.Default)
                .launch { syncFromRemotesToLocal() }
        }
    }

    override suspend fun syncFromLocalToRemotes() =
        lockAndRun { syncFromLocalToRemotesInternal() }

    private suspend fun syncFromLocalToRemotesInternal() {
        try {
            notifySyncing()
            val entries = localCache.getEntries()
            remoteEndpoints.runInParallel { setEntries(entries) }
            notifySynced(entries)
        } catch (t: Throwable) {
            notifyNotSynced(t)
        }
    }

    override suspend fun syncFromRemotesToLocal() =
        lockAndRun { syncFromRemotesToLocalInternal() }

    private suspend fun syncFromRemotesToLocalInternal() {
        try {
            notifySyncing()
            val entries = remoteEndpoints.runInParallel { getEntries() }
            localCache.setEntries(entries)
            notifySynced(entries)
        } catch (t: Throwable) {
            notifyNotSynced(t)
        }
    }

    override suspend fun setEntriesIfDifferent(entries: List<RepositoryEntry<T>>) =
        lockAndRun { setEntriesIfDifferentInternal(entries) }

    private suspend fun setEntriesIfDifferentInternal(entries: List<RepositoryEntry<T>>) {
        val currentEntries  = _status.value.lastSuccessfulSync?.entries
        if (entries == currentEntries) return

        localCache.setEntries(entries)
        if (autoSync) {
            syncFromLocalToRemotesInternal()
        }
    }

    private suspend fun notifySyncing() {
        val currentStatus = _status.value
        val newStatus = SyncStatus.Syncing(
            lastSuccessfulSync = currentStatus.lastSuccessfulSync
        )
        _status.emit(newStatus)
    }

    private suspend fun notifySynced(entries: List<RepositoryEntry<T>>) {
        val newStatus = SyncStatus.Synced(
            lastSuccessfulSync = LastSuccessfulSync(
                entries = entries
            )
        )
        _status.emit(newStatus)
    }

    private suspend fun notifyNotSynced(reason: Throwable) {
        val currentStatus = _status.value
        val newStatus = SyncStatus.NotSynced(
            lastSuccessfulSync = currentStatus.lastSuccessfulSync,
            reason = reason
        )
        _status.emit(newStatus)
    }

    private suspend fun <R> lockAndRun(block : suspend ()->R): R =
        withContext(coroutineContext) {
            mutex.withLock {
                block()
            }
        }

    private suspend fun <R> List<Repository.Endpoint<T>>.runInParallel(
        block: suspend Repository.Endpoint<T>.() ->R
    ): R {
        requireIsNotEmpty()

        val suspendables: List<suspend CoroutineScope.() -> R> =
            map { endpoint -> { endpoint.block() } }

        return suspendables
            .runInParallel()
            .reduceIdentical()
    }

    private fun List<Repository.Endpoint<T>>.requireIsNotEmpty() =
        require(isNotEmpty()) { "Repository endpoints cannot be empty" }

    private fun <R> List<R>.reduceIdentical(): R =
        this.reduce { accumulator: R, element: R ->
            require(accumulator == element) { "Items not identical" }
            accumulator
        }
}

private class InMemoryEndpoint<T: Any>(
    initial: List<RepositoryEntry<T>> =
        emptyList(),
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

    private suspend fun <R> lockAndRun(block : suspend ()->R): R =
        withContext(coroutineContext) {
            mutex.withLock {
                block()
            }
        }
}

private class LocalDatabaseEndpoint<T: Any>(
    private val doGetItems: suspend ()->List<RepositoryEntry<T>>,
    private val doSetItems: suspend (List<RepositoryEntry<T>>)->Unit,
    private val coroutineContext: CoroutineContext =
        CoroutineScope(Dispatchers.Default).coroutineContext,
    private val mutex: Mutex =
        Mutex()
): Repository.Endpoint<T> {

    override suspend fun getEntries(): List<RepositoryEntry<T>> =
        lockAndRun { doGetItems() }

    override suspend fun setEntries(entries: List<RepositoryEntry<T>>) =
        lockAndRun { doSetItems(entries) }

    private suspend fun <R> lockAndRun(block : suspend ()->R): R =
        withContext(coroutineContext) {
            mutex.withLock {
                block()
            }
        }
}

/**
 * Runs a list of coroutine execution blocks in parallel, waits for all of them to complete,
 * then returns a list of the return values.
 * THis function will fail as soon as a single exception is thrown, and this function will
 * throw that exception
 */
private suspend fun <R> List<suspend CoroutineScope.() -> R>.runInParallel(): List<R> {
    val asyncs = mutableListOf<Deferred<R>>()
    forEach {
        coroutineScope {
            asyncs.add(async { it() })
        }
    }
    return asyncs.awaitAll()
}
