package dinino.marc.games.coroutine

import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlin.Result.Companion.failure
import kotlin.Result.Companion.success

/**
 * Runs a list of coroutine suspend functions in parallel,
 * waits for all of them to complete, then returns a list of the return values.
 * Fails as soon as an exception is throws in any parallel execution, then throws that exception.
 */
suspend fun <RETURN> Collection<suspend ()->RETURN>.runInParallel(): List<RETURN> {
    val asyncs = mutableListOf<Deferred<RETURN>>()
    forEach {  suspendable ->
        val deferred: Deferred<RETURN> = coroutineScope {
            async {
                suspendable()
            }
        }
        asyncs.add(deferred)
    }
    return asyncs
        .toList()
        .awaitAll()
}

/**
 * Runs a list of coroutine suspend functions in parallel,
 * waits for all of them to complete, then returns a list of the return values.
 * A failure in one parallel execution path does not affect the others
 */
suspend fun <RETURN> Collection<suspend ()->RETURN>.runInParallelFailIndependently():
        List<Result<RETURN>> {
    val asyncs = mutableListOf<Deferred<Result<RETURN>>>()
    forEach {  suspendable ->
        val deferred: Deferred<Result<RETURN>> = coroutineScope {
            async {
                try {
                    success(suspendable())
                } catch (t: Throwable) {
                    failure(t)
                }
            }
        }
        asyncs.add(deferred)
    }
    return asyncs
        .toList()
        .awaitAll()
}