package dinino.marc.games.coroutine

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

/**
 * Many people mistakenly think of coroutines as a Kotlin's version of threads.
 * While this is mostly true, there is a "gotcha":
 * A single coroutine may run on multiple threads during the course of execution,
 * so coroutines do not guarantee FIFO execution in the way that a traditional thread would.
 * For more information refer to this page
 * https://kotlinlang.org/docs/shared-mutable-state-and-concurrency.html
 *
 * This is a utility class to enable clean FIFO execution within the context of Kotlin coroutines.
 *
 * Example usage:
 *
 * interface Foo {
 *     suspend fun startup() // asynchronous startup, should not be canceled
 *     suspend fun suspendableFunction(): Int
 *     fun nonSuspendableFunction(): String
 *     suspend fun shutdown() // asynchronous shutdown, can be canceled
 *     fun close() // stop the world and do any necessary cleanup now
 * }
 *
 * class FooImpl : Foo {
 *     ...
 * }
 *
 * class FooConcurrent(
 *     private val delegate: Foo,
 *     private val cs: CoroutineCriticalSection = CoroutineCriticalSection()
 * ) : Foo {
 *     override suspend fun startup() =
 *         cs.lockAndRunNonCancelable {
 *             delegate.startup()
 *         }
 *
 *     override suspend fun suspendableFunction() =
 *         cs.lockAndRun {
 *             delegate.suspendableFunction()
 *         }
 *
 *     override fun nonSuspendableFunction() =
 *         cs.lockAndRunNow {
 *             delegate.nonSuspendableFunction()
 *         }
 *
 *     override suspend fun shutdown() =
 *         cs.lockAndRun {
 *             delegate.shutdown()
 *         }
 *
 *     companion object {
 *         fun Foo.concurrent(cs: CoroutineCriticalSection = CoroutineCriticalSection()): Foo =
 *             FooConcurrent(delegate = this, cs = cs)
 *     }
 * }
 *
 * class Bar(dependencyBar: Foo = FooImpl().concurrent()) {
 *     ...
 * }
 */
class CoroutineCriticalSection(
    private val coroutineScope: CoroutineScope,
    private val lock: Mutex = Mutex()
) {
    constructor(
        coroutineCtx: CoroutineContext = Dispatchers.Default,
        lock: Mutex = Mutex()
    ) : this(SupervisorScope(on = coroutineCtx), lock)

    suspend fun <T> lockAndRun(block: suspend () -> T) =
        withContext(coroutineScope.coroutineContext) {
            lock.withLock(this) {
                block()
            }
        }

    fun <T> closeAndRunNow(afterClose: () -> T): T {
        coroutineScope.cancel()
        return afterClose()
    }

    /**
     * This locks and runs the given suspend block, but runs it blocking under the hood.
     * This is the only way to guarantee certain calls (e.g. startup, shutdown, etc.)
     * are run on a background dispatcher but will not be canceled or interfered with until completion
     */
    suspend fun <T> lockAndRunNonCancelable(block: suspend () -> T) =
        withContext(NonCancellable) {
            lock.withLock(this) {
                block()
            }
        }

    companion object {
        /**
         * A special type of coroutine scope that allows sibling tasks to fail independently.
         */
        @Suppress("FunctionName")
        private fun SupervisorScope(on: CoroutineContext = Dispatchers.Default) =
            CoroutineScope(on + SupervisorJob())
    }
}