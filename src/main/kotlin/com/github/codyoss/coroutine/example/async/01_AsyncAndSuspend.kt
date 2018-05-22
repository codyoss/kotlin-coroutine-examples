package com.github.codyoss.coroutine.example.async

import kotlinx.coroutines.experimental.Deferred
import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.delay
import kotlinx.coroutines.experimental.runBlocking
import kotlin.system.measureTimeMillis

fun main(args: Array<String>) = runBlocking {
    val time = measureTimeMillis {
        // async launch a coroutine that returns a `Deferred<T>`. When you want the value from this deferred value you
        // have to call `.await()`. If you are used to `Promises` or `Futures`, this is really the same idea.
        val one = async { getOne() }
        val two = getTwoAsync()
        val three = getThreeAsync()

        println(one.await() + two.await() + three.await())
    }
    println("Took ${time}ms")
}

private suspend fun getOne(): Int {
    delay(1000)
    return 1
}

// It is a common pattern to make async functions instead of just launching things async. Makes the code read
// a bit nicer
private fun getTwoAsync(): Deferred<Int> = async {
    delay(1000)
    // since async functions already return a deferred value we need to tell the compiler where we are returning to
    return@async 3
}

private fun getThreeAsync() = async {
    // To get around the return quirk in `getTwoAsync()` you could call a suspend function. Kotlin is smart enough
    // to know what you wanted to do here.
    getThree()
}

private suspend fun getThree(): Int {
    delay(1000)
    return 3
}
