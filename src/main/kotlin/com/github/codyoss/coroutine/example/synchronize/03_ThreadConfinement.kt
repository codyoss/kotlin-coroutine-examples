package com.github.codyoss.coroutine.example.synchronize

import kotlinx.coroutines.experimental.*

private var counter = 0
private val myCtx = newSingleThreadContext("myCtx")

fun main(args: Array<String>) = runBlocking {
    runAllTheCoroutines(CommonPool) {
        // can switch down to a single threaded context to update the variable. This is quite an expensive context switch
        // though and should be avoided if possible
        withContext(myCtx) {
            counter++
        }
    }
    println(" Run 1 Counter: $counter")

    delay(5000)
    println("--- Resetting ---")
    counter = 0

    // It is much better in this case to just run all the coroutines from one thread
    runAllTheCoroutines(myCtx) {
        counter++
    }
    println("Run 2 Counter: $counter")
}