package dinino.marc.games.coroutine

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope

/**
 * Runs a list of coroutine execution blocks in parallel, waits for all of them to complete,
 * then returns a list of the return values.
 * THis function will fail as soon as a single exception is thrown, and this function will
 * throw that exception
 */
suspend fun <RETURN> List<suspend CoroutineScope.() -> RETURN>.runInParallel(): List<RETURN> {
    val asyncs = mutableListOf<Deferred<RETURN>>()
    forEach {
        coroutineScope {
            asyncs.add(async { it() })
        }
    }
    return asyncs.awaitAll()
}

suspend fun List<suspend CoroutineScope.() -> Unit>.runInParallel() {
    runInParallel<Unit>()
}