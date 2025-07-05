package dinino.marc.games.userflow.common.data

import dinino.marc.games.coroutine.CoroutineCriticalSection
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalTime::class)
class DefaultRepository<SERIALIZABLE_TYPE: Any>(
    private val cs: CoroutineCriticalSection,
    private val _status: MutableStateFlow<SyncStatus<SERIALIZABLE_TYPE>> =
        MutableStateFlow(SyncStatus.NotSynced()),
    private val inMemoryEndpoint: Repository.Endpoint.InMemory<SERIALIZABLE_TYPE>,
    private val databaseEndpoint: Repository.Endpoint.Persistent<SERIALIZABLE_TYPE>,
): Repository<SERIALIZABLE_TYPE> {

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

    override val status = _status.asStateFlow()

    init {
        CoroutineScope(Dispatchers.Default)
            .launch { sync() }
    }

    override suspend fun sync() =
        cs.lockAndRunNonCancelable { syncInternal() }

    private suspend fun upsertLatestIfDifferent(latest: SERIALIZABLE_TYPE) {
        val items = _status.value.lastSuccessfulSync?.items
        if (latest == _status.value.lastSuccessfulSync?.items?.last()) return

    }

    private suspend fun syncInternal() {
        try {
            notifySyncing()
            val items = databaseEndpoint.getItems()
            inMemoryEndpoint.setItems(items = items)
            notifySynced(items = items)
        } catch (t: Throwable) {
            notifyNotSynced(reason = t)
        }
    }



    override suspend fun setItemsIfDifferent(items: List<SERIALIZABLE_TYPE>) =
        cs.lockAndRun { setItemsIfDifferentInternal(items) }

    private suspend fun setItemsIfDifferentInternal(items: List<SERIALIZABLE_TYPE>) {
        if (items == _status.value.lastSuccessfulSync?.items) return

        try {
            notifySyncing()
            inMemoryEndpoint.setItems(items = items)
            databaseEndpoint.setItems(items = items)
            notifySynced(items = items)
        } catch (t: Throwable) {
            notifyNotSynced(reason = t)
        }
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
}

private class DefaultInMemoryEndpoint<SERIALIZABLE_TYPE: Any>(
    private val cs: CoroutineCriticalSection,
    initial: List<SERIALIZABLE_TYPE> = emptyList(),
) : Repository.Endpoint.InMemory<SERIALIZABLE_TYPE> {

    private var items: List<SERIALIZABLE_TYPE> = initial

    override suspend fun getItems() =
        cs.lockAndRun { items }

    override suspend fun setItems(items: List<SERIALIZABLE_TYPE>) =
        cs.lockAndRun { this.items = items }
}

private class DefaultDatabaseEndpoint<SERIALIZABLE_TYPE: Any>(
    private val cs: CoroutineCriticalSection,
    private val doGetItems: suspend ()->List<SERIALIZABLE_TYPE>,
    private val doSetItems: suspend (List<SERIALIZABLE_TYPE>)->Unit,
): Repository.Endpoint.Persistent<SERIALIZABLE_TYPE> {

    override suspend fun getItems(): List<SERIALIZABLE_TYPE> =
        cs.lockAndRun { doGetItems() }

    override suspend fun setItems(items: List<SERIALIZABLE_TYPE>) =
        cs.lockAndRun { doSetItems(items) }
}

