package dinino.marc.games.userflow.common.data

import kotlin.time.Clock
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

@OptIn(ExperimentalTime::class)
data class LastSuccessfulSync<out T: Any>(
    val entries: List<RepositoryEntry<T>>,
    val instant: Instant = Clock.System.now()
)
