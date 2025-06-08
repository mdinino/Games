package dinino.marc.games.ui.screen

import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

object ComposableExtensions {
    /**
     * Used to register for side effects/single events in composable functions.
     */
    @Composable
    fun <T : Any> SingleEventEffect(
        sideEffectFlow: Flow<T>,
        lifeCycleState: Lifecycle.State = Lifecycle.State.STARTED,
        collector: (T) -> Unit
    ) {
        val lifecycleOwner = LocalLifecycleOwner.current

        LaunchedEffect(sideEffectFlow) {
            lifecycleOwner.repeatOnLifecycle(lifeCycleState) {
                sideEffectFlow.collect(collector)
            }
        }
    }
}