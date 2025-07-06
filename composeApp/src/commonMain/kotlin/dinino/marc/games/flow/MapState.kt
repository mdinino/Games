package dinino.marc.games.flow

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.stateIn

/**
 * Used to transform one StateFlow into another
 */
@OptIn(ExperimentalCoroutinesApi::class)
fun <T, K> StateFlow<T>.mapState(
    scope: CoroutineScope = CoroutineScope(Dispatchers.Default),
    transform: (T) -> K
): StateFlow<K> =
    mapLatest {
        transform(it)
    }.stateIn(scope, SharingStarted.Eagerly, transform(value))