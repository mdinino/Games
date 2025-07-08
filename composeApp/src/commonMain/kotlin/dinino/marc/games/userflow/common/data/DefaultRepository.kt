package dinino.marc.games.userflow.common.data

import dinino.marc.games.coroutine.CoroutineCriticalSection
import dinino.marc.games.coroutine.runInParallel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlin.time.ExperimentalTime
import kotlin.uuid.ExperimentalUuidApi

@OptIn(ExperimentalTime::class)
class DefaultRepository<T: Any>(
    override val autoSync: Boolean = true,
    override val localCache: Repository.Endpoint<T> = InMemoryEndpoint(),
    override val remoteEndpoints: List<Repository.Endpoint<T>>,
    private val cs: CoroutineCriticalSection = CoroutineCriticalSection(),
    private val _status: MutableStateFlow<SyncStatus<T>> = MutableStateFlow(SyncStatus.NotSynced())
): Repository<T> {
    override val status = _status.asStateFlow()

    init {
        remoteEndpoints.requireIsNotEmpty()

        if (autoSync) {
            CoroutineScope(Dispatchers.Default)
                .launch { syncFromRemotesToLocal() }
        }
    }

    override suspend fun syncFromLocalToRemotes() =
        cs.lockAndRun { syncFromLocalToRemotesInternal() }

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
        cs.lockAndRunNonCancelable {
            try {
                notifySyncing()
                val entries = remoteEndpoints.runInParallel { getEntries() }
                localCache.setEntries(entries)
                notifySynced(entries)
            } catch (t: Throwable) {
                notifyNotSynced(t)
            }
        }

    override suspend fun upsertLatestItemIfDifferent(item: T): RepositoryEntry<T> =
        cs.lockAndRunNonCancelable {
            val currentEntries = localCache.getEntries()
            val latestEntry = currentEntries.lastOrNull()
            if (latestEntry != null && latestEntry.item == item) return@lockAndRunNonCancelable latestEntry

            val newEntries = currentEntries.updateLastWithItem(item)
            setEntriesIfDifferentInternal(newEntries)

            newEntries.last()
        }

    override suspend fun clearEntries() =
        cs.lockAndRunNonCancelable {
            localCache.clearEntries()
            remoteEndpoints.runInParallel { clearEntries() }
        }

    @OptIn(ExperimentalUuidApi::class)
    private fun List<RepositoryEntry<T>>.updateLastWithItem(item: T): List<RepositoryEntry<T>> =
        mutate {
            when(isEmpty()) {
                true -> add(RepositoryEntry(item = item))
                else -> this[size] = this[size].copy(item = item)
            }
        }

    private fun List<RepositoryEntry<T>>.mutate(
        mutator: MutableList<RepositoryEntry<T>>.()->Unit
    ): List<RepositoryEntry<T>> {
        val mutableList = this.toMutableList()
        mutableList.mutator()
        return mutableList.toList()
    }

    override suspend fun setEntriesIfDifferent(entries: List<RepositoryEntry<T>>) =
        cs.lockAndRunNonCancelable { setEntriesIfDifferentInternal(entries) }

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
