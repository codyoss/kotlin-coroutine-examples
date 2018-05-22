package com.github.codyoss.coroutine.example.cancellation

import kotlinx.coroutines.experimental.cancelAndJoin
import kotlinx.coroutines.experimental.delay
import kotlinx.coroutines.experimental.launch
import kotlinx.coroutines.experimental.runBlocking

fun main(args: Array<String>) = runBlocking {
    val job = launch {
        // `isActive` is a special property that coroutines have. Can be used to see if the coroutine has been cancelled.
        while (isActive) {
            println("I am sleeping still...")
            Thread.sleep(300)
        }
    }
    delay(1000)
    println("stop sleeping please")
    job.cancelAndJoin()
    println("Goodbye")
}