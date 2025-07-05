package dinino.marc.games.userflow.common.data

import dinino.marc.games.coroutine.CoroutineCriticalSection
import dinino.marc.games.coroutine.runInParallel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalTime::class)
class DefaultGameRepository<SERIALIZABLE_TYPE: Any>(
    private val cs: CoroutineCriticalSection,
    private val _status: MutableStateFlow<SyncStatus<SERIALIZABLE_TYPE>> =
        MutableStateFlow(SyncStatus.NotSynced()),
    private val inMemoryEndpoint: GameRepository.Endpoint.InMemory<SERIALIZABLE_TYPE>,
    private val databaseEndpoint: GameRepository.Endpoint.Persistent<SERIALIZABLE_TYPE>,
): GameRepository<SERIALIZABLE_TYPE> {

    constructor(
        cs: CoroutineCriticalSection = CoroutineCriticalSection(),
        getItemsFromDatabase: suspend () -> List<SERIALIZABLE_TYPE>,
        setItemsInDatabase: suspend (List<SERIALIZABLE_TYPE>) -> Unit,
    ) : this(
        cs = cs,
        inMemoryEndpoint = DefaultInMemoryEndpoint<SERIALIZABLE_TYPE>(
            cs = CoroutineCriticalSection(parent = cs)
        ),
        databaseEndpoint = DefaultDatabaseEndpoint<SERIALIZABLE_TYPE>(
            cs = CoroutineCriticalSection(parent = cs),
            doGetItems = getItemsFromDatabase,
            doSetItems = setItemsInDatabase
        )
    )

    private val endpoints = listOf(inMemoryEndpoint, databaseEndpoint)

    override val status = _status.asStateFlow()

    init {
        CoroutineScope(Dispatchers.Default)
            .launch { sync() }
    }

    override suspend fun sync() =
        cs.lockAndRunNonCancelable { syncInternal() }

    private suspend fun syncInternal() {
        notifySyncing()
        try {
            notifySynced(items = databaseEndpoint.getItems())
        } catch (t: Throwable) {
            notifyNotSynced(reason = t)
        }
    }

    override suspend fun setItemsIfDifferent(items: List<SERIALIZABLE_TYPE>) =
        cs.lockAndRun { setItemsIfDifferentInternal(items) }

    private suspend fun setItemsIfDifferentInternal(items: List<SERIALIZABLE_TYPE>) {
        runInParallelOverEndpoints { setItemsIfDifferent(items) }
    }

    private suspend fun notifySyncing() {
        val currentStatus = _status.value
        val newStatus = SyncStatus.Syncing(
            lastSuccessfulSync = currentStatus.lastSuccessfulSync
        )
        _status.emit(newStatus)
    }

    private suspend fun notifySynced(items: List<SERIALIZABLE_TYPE>) {
        val newStatus = SyncStatus.Synced(
            lastSuccessfulSync = LastSuccessfulSync(
                items = items
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

    private suspend fun emitStatus(status: SyncStatus<SERIALIZABLE_TYPE>) {
        _status.emit(status)
    }

    @Suppress("unused")
    private suspend fun <RETURN> runInParallelOverEndpoints(
        block: suspend GameRepository.Endpoint<SERIALIZABLE_TYPE>.() -> RETURN
    ) = mutableListOf<suspend CoroutineScope.() -> RETURN>()
        .apply {
            endpoints
                .forEach { add { block(it) } }
        }.runInParallel()

    private suspend fun runInParallelOverEndpoints(
        block: suspend GameRepository.Endpoint<SERIALIZABLE_TYPE>.() -> Unit
    ): Unit = mutableListOf<suspend CoroutineScope.() -> Unit>()
        .apply {
            endpoints
                .forEach { add { block(it) } }
        }.runInParallel()
}

private class DefaultInMemoryEndpoint<SERIALIZABLE_TYPE: Any>(
    private val cs: CoroutineCriticalSection,
    initial: List<SERIALIZABLE_TYPE> = emptyList(),
) : GameRepository.Endpoint.InMemory<SERIALIZABLE_TYPE> {

    private var items: List<SERIALIZABLE_TYPE> = initial

    override suspend fun getItems() =
        cs.lockAndRun { items }

    override suspend fun setItemsIfDifferent(items: List<SERIALIZABLE_TYPE>) =
        cs.lockAndRun {
            if (this.items != items) {
                this.items = items
            }
        }
}

private class DefaultDatabaseEndpoint<SERIALIZABLE_TYPE: Any>(
    private val cs: CoroutineCriticalSection,
    private val doGetItems: suspend ()->List<SERIALIZABLE_TYPE>,
    private val doSetItems: suspend (List<SERIALIZABLE_TYPE>)->Unit,
): GameRepository.Endpoint.Persistent<SERIALIZABLE_TYPE> {

    override suspend fun getItems(): List<SERIALIZABLE_TYPE> =
        cs.lockAndRun { doGetItems() }

    override suspend fun setItemsIfDifferent(items: List<SERIALIZABLE_TYPE>) =
        cs.lockAndRun {
            if (items != doGetItems()) {
                doSetItems(items)
            }
        }
}

