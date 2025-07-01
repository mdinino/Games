package dinino.marc.games.userflow.common.data

import kotlin.time.Clock
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

sealed interface SyncStatus<out SERIALIZABLE_TYPE: Any>{
    val lastSuccessfulSync: LastSuccessfulSync<SERIALIZABLE_TYPE>?

    data class NotSynced<out SERIALIZABLE_TYPE: Any>(
        override val lastSuccessfulSync: LastSuccessfulSync<SERIALIZABLE_TYPE>? = null,
        val reason: Throwable? = null
    ): SyncStatus<SERIALIZABLE_TYPE>

    @OptIn(ExperimentalTime::class)
    data class Syncing<out SERIALIZABLE_TYPE: Any>(
        val syncStartedAt: Instant = Clock.System.now(),
        override val lastSuccessfulSync: LastSuccessfulSync<SERIALIZABLE_TYPE>?
    ): SyncStatus<SERIALIZABLE_TYPE>

    data class Synced<out SERIALIZABLE_TYPE: Any>(
        override val lastSuccessfulSync: LastSuccessfulSync<SERIALIZABLE_TYPE>
    ): SyncStatus<SERIALIZABLE_TYPE>
}