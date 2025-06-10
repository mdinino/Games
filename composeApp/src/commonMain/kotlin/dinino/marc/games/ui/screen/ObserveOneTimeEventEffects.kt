package dinino.marc.games.ui.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.withContext

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

private typealias BoxedResult<T> = Box<Result<T>>

private data class Box<T>(val value: T?)


@Composable
fun <T> ObserveOneTimeEffectResult(
    oneTimeEffects: Flow<T>,
    lifeCycleState: Lifecycle.State = Lifecycle.State.STARTED,
    onsideEffect: @Composable (sideEffectResult: Result<T>) -> Unit
) {
    var lastEmittedSideEffect by remember {
        mutableStateOf(BoxedResult<T>(value = null))
    }
    val lifecycleOwner = LocalLifecycleOwner.current

    LaunchedEffect(lifecycleOwner.lifecycle, oneTimeEffects, lifeCycleState) {
        lifecycleOwner.repeatOnLifecycle(lifeCycleState) {
            withContext(Dispatchers.Main.immediate) {
                try {
                    oneTimeEffects.collect {
                    }
                } catch(t: Throwable) {

                }

            }
        }
    }
    val sideEffect: State<T?> = oneTimeEffects.collectAsStateWithLifecycle(
        initialValue = null,
        minActiveState = lifeCycleState)
    sideEffect.value?.let {
        onsideEffect(it)
    }
}

