package dinino.marc.games.userflow.common.data

import kotlin.time.Clock
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

sealed interface SyncStatus<out T: Any>{
    val lastSuccessfulSync: LastSuccessfulSync<T>?

    data class NotSynced<out T: Any>(
        override val lastSuccessfulSync: LastSuccessfulSync<T>? = null,
        val reason: Throwable? = null
    ): SyncStatus<T>

    @OptIn(ExperimentalTime::class)
    data class Syncing<out T: Any>(
        val syncStartedAt: Instant = Clock.System.now(),
        override val lastSuccessfulSync: LastSuccessfulSync<T>?
    ): SyncStatus<T>

    data class Synced<out T: Any>(
        override val lastSuccessfulSync: LastSuccessfulSync<T>
    ): SyncStatus<T>
}