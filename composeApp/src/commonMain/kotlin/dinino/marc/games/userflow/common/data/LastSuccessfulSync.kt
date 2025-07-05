package dinino.marc.games.userflow.common.data

import kotlin.time.Clock
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

@OptIn(ExperimentalTime::class)
data class LastSuccessfulSync<out SERIALIZABLE_TYPE: Any>(
    val entries: List<RepositoryEntry<SERIALIZABLE_TYPE>>,
    val instant: Instant = Clock.System.now()
)
