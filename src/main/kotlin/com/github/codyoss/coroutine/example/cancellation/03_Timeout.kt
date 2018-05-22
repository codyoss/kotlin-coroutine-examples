package com.github.codyoss.coroutine.example.cancellation

import kotlinx.coroutines.experimental.delay
import kotlinx.coroutines.experimental.runBlocking
import kotlinx.coroutines.experimental.withTimeout

fun main(args: Array<String>) = runBlocking {
    // `withTimeout` is syntax sugar to launch a coroutine and kill it after so long. Nice for jobs you don't want to
    // run forever. Note that if the action times out it will throw a `TimeoutCancellationException`. If you would
    // rather not catch an exceptions use `withTimeoutOrNull` which returns null if the operation does not finish.
    withTimeout(1000) {
        repeat(10) {
            println("I am still alive")
            delay(300)
        }
    }
    println("My coroutine friend is gone...")
}