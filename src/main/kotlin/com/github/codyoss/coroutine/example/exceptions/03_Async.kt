package com.github.codyoss.coroutine.example.exceptions

import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.delay
import kotlinx.coroutines.experimental.runBlocking

fun main(args: Array<String>) = runBlocking {
    val deferredValue = async { throw RuntimeException() }
    delay(500)

    try {
        // Because of how async works, if an exception is thrown in the coroutine it will be propagated up just like
        // normal code would. Aka, you can handle exceptions "as normal"
        deferredValue.await()
    } catch (e: RuntimeException) {
        println("Exceptions percolate up through async/await")
    }
    println("Everything is just fine")
}