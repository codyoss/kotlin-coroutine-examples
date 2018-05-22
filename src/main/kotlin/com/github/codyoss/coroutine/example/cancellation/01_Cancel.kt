package com.github.codyoss.coroutine.example.cancellation

import kotlinx.coroutines.experimental.cancelAndJoin
import kotlinx.coroutines.experimental.delay
import kotlinx.coroutines.experimental.launch
import kotlinx.coroutines.experimental.runBlocking

fun main(args: Array<String>) = runBlocking {
    val job = launch {
        repeat(10) { i ->
            println("$i: I am sleeping still...")

            // This will not cancel as it does not suspend the coroutine
            Thread.sleep(300)

            // This will allow for cancellation. This is considered a suspension point.
            //delay(300)
        }
    }
    delay(1000)
    println("stop sleeping please")

    // We already saw what `join` does, `cancelAndJoin` is a concise method for calling both `job.cancel()` followed
    // by `job.join()`. Cancel tells the coroutine that it is no longer needed and it can try to die. Note that the
    // coroutine needs to support dying via suspension points, the most basic of which is `delay`. If a coroutine
    // has no suspension points it can not be canceled. Once a coroutine is suspended it will throw a
    // `CancellationException`.
    job.cancelAndJoin()
    println("Goodbye")
}