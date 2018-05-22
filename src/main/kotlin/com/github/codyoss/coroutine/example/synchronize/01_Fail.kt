package com.github.codyoss.coroutine.example.synchronize

import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.launch
import kotlinx.coroutines.experimental.runBlocking
import kotlin.coroutines.experimental.CoroutineContext
import kotlin.system.measureTimeMillis

private var counter = 0

// Updating a single object from 1000 running coroutines requires some sort of synchronization. Without any
// synchronization notice how the final value of the counter is wrong, it should be 1_000_000.
fun main(args: Array<String>) = runBlocking {
    runAllTheCoroutines(CommonPool) {
        counter++
    }
    println("Counter: $counter")
}

// launching a 1000 coroutines with the provided context and executing the action 1000 times
suspend fun runAllTheCoroutines(ctx: CoroutineContext, action: suspend () -> Unit) {
    val concurrency = 1000
    val time = measureTimeMillis {
        val jobs = List(concurrency) {
            launch(ctx) {
                repeat(concurrency) { action() }
            }
        }
        jobs.forEach { it.join() }
    }
    println("Completed in ${time}ms")
}