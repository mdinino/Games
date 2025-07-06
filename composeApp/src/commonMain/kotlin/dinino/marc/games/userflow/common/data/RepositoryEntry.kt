package dinino.marc.games.userflow.common.data

import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid


@OptIn(ExperimentalUuidApi::class)
data class RepositoryEntry<out T: Any>(
    val uuid: Uuid = Uuid.random(),
    val item: T
)