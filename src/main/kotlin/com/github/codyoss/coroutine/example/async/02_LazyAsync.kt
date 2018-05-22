package com.github.codyoss.coroutine.example.async

import kotlinx.coroutines.experimental.CoroutineStart
import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.delay
import kotlinx.coroutines.experimental.runBlocking
import kotlin.system.measureTimeMillis

fun main(args: Array<String>) = runBlocking {
    val time = measureTimeMillis {
        // You can tell async functions not to launch until await is called if you tell is to start lazy
        val one = async(start = CoroutineStart.LAZY) { getOne() }
        val two = async(start = CoroutineStart.LAZY) { getTwo() }
        val three = async(start = CoroutineStart.LAZY) { getThree() }

        println(one.await() + two.await() + three.await())
    }
    println("Took ${time}ms")
}

private suspend fun getOne(): Int {
    delay(1000)
    return 1
}

private suspend fun getTwo(): Int {
    delay(1000)
    return 2
}

private suspend fun getThree(): Int {
    delay(1000)
    return 3
}