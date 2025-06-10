package dinino.marc.games.ui.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.compose.runtime.rememberCoroutineScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.Channel.Factory.UNLIMITED
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import kotlin.Result.Companion.failure
import kotlin.Result.Companion.success

/**
 * Utilities to observe one-time events and handle them as composable side effects
 * For example, a viewmodel might emit one time events through a channel about errors.
 * Then, a composable would want to observe those events and react accordingly
 * (show a snackbar, a toast, etc.).
 * These functions provide convent ways to accomplish the above user case.
 */
@Suppress("UNUSED")
object ObserveOneTimeEventEffects {

    @Composable
    fun <T> ObserveOneTimeEventsOrNullEffect(
        oneTimeEvents: ReceiveChannel<T>,
        lifeCycleState: Lifecycle.State = Lifecycle.State.STARTED,
        onEventOrNull: @Composable (eventOrNull: T?) -> Unit
    ) = ObserveOneTimeEventsResultEffect(
        oneTimeEvents = oneTimeEvents,
        lifeCycleState = lifeCycleState
    ) { result ->
        onEventOrNull(result.getOrNull())
    }

    @Composable
    fun <T> ObserveOneTimeEventsResultEffect(
        oneTimeEvents: ReceiveChannel<T>,
        lifeCycleState: Lifecycle.State = Lifecycle.State.STARTED,
        onEvent: @Composable (sideEffectResult: Result<T>) -> Unit
    ) = ObserveOneTimeEventsResultEffect(
        oneTimeEvents = oneTimeEvents.receiveAsFlow(),
        lifeCycleState = lifeCycleState,
        onEvent = onEvent)

    @Composable
    fun <T> ObserveOneTimeEventsOrNullEffect(
        oneTimeEvents: Flow<T>,
        lifeCycleState: Lifecycle.State = Lifecycle.State.STARTED,
        onEventOrNull: @Composable (eventOrNull: T?) -> Unit
    ) = ObserveOneTimeEventsResultEffect(
        oneTimeEvents = oneTimeEvents,
        lifeCycleState = lifeCycleState
    ) { result ->
        onEventOrNull(result.getOrNull())
    }

    @Composable
    fun <T> ObserveOneTimeEventsResultEffect(
        oneTimeEvents: Flow<T>,
        lifeCycleState: Lifecycle.State = Lifecycle.State.STARTED,
        onEvent: @Composable (sideEffectResult: Result<T>) -> Unit
    ) {
        val lifecycleOwner = LocalLifecycleOwner.current

        val scope = rememberCoroutineScope()

        /*
         * Use an unlimited channel to process events in the order received.
         * It's ok if we lose events if the lifecycle has been restarted
         * because if the lifecycle changes, we are no longer subscribed to oneTimeEvents anyway
         */
        val processChannel: Channel<InternalOneTimeEvent<Result<T>>?> by remember {
            mutableStateOf(Channel(capacity = UNLIMITED))
        }

        /*
         * By keeping track on the one time events, and whether or not they have been processed,
         * we prevent events from being handled multiple times (between recompositions)
         * and reach when a legitime event does come in.
         */
        val lastProcessChannelEmission: InternalOneTimeEvent<Result<T>>? =
            processChannel
            .receiveAsFlow()
            .distinctUntilChanged() /* prevents redundant events from poling up on the queue */
            .collectAsStateWithLifecycle(
                initialValue = null,
                lifecycleOwner = lifecycleOwner,
                minActiveState = lifeCycleState)
            .value

        LaunchedEffect(lifecycleOwner.lifecycle, oneTimeEvents, lifeCycleState) {
            lifecycleOwner.repeatOnLifecycle(lifeCycleState) {
                this.launch {
                    try {
                        oneTimeEvents.collect { event ->
                            processChannel.send(
                                event
                                    .asSuccessResult()
                                    .asOneTimeEvent()
                            )
                        }
                    } catch(throwable: Throwable) {
                        processChannel.send(
                            throwable
                                .asFailureResult<T>()
                                .asOneTimeEvent()
                        )
                    }
                }
            }
        }

        if(lastProcessChannelEmission?.processed == false) {
            onEvent(lastProcessChannelEmission.event)
            scope.launch {
                processChannel.send(
                    lastProcessChannelEmission.processed()
                )
            }
        }
    }

    private data class InternalOneTimeEvent<T>(val event: T, val processed: Boolean = false)

    private fun <T> T.asSuccessResult() = success(value = this)
    private fun <T> Throwable.asFailureResult() = failure<T>(exception = this)

    private fun <T> InternalOneTimeEvent<T>.processed(processed: Boolean = true): InternalOneTimeEvent<T> =
        copy(processed = processed)

    private fun <T> T.asOneTimeEvent(processed: Boolean = false) =
        InternalOneTimeEvent(event = this, processed = processed)
}





