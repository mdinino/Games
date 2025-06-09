package dinino.marc.games.ui.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow

/**
 * Sets up sets up observation on composable to monitor one one time side effects
 * (e.g. errors, snackbars, toasts, etc.)
 */
@Composable
fun <T : Any> ObserveOneTimeEffect(
    oneTimeEffects: ReceiveChannel<T>,
    lifeCycleState: Lifecycle.State = Lifecycle.State.STARTED,
    onsideEffect: @Composable (sideEffect: T) -> Unit
) = ObserveOneTimeEffect(
    oneTimeEffects = oneTimeEffects.receiveAsFlow(),
    lifeCycleState = lifeCycleState,
    onsideEffect = onsideEffect)

@Composable
fun <T : Any> ObserveOneTimeEffect(
    oneTimeEffects: Flow<T>,
    lifeCycleState: Lifecycle.State = Lifecycle.State.STARTED,
    onsideEffect: @Composable (sideEffect: T) -> Unit
) {
    val sideEffect: State<T?> = oneTimeEffects.collectAsStateWithLifecycle(
        initialValue = null,
        minActiveState = lifeCycleState)
    sideEffect.value?.let {
        onsideEffect(it)
    }
}

