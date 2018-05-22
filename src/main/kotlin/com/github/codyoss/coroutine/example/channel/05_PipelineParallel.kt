package com.github.codyoss.coroutine.example.channel

import kotlinx.coroutines.experimental.channels.Channel
import kotlinx.coroutines.experimental.delay
import kotlinx.coroutines.experimental.launch
import kotlinx.coroutines.experimental.runBlocking
import kotlin.system.measureTimeMillis

private const val CONCURRENCY = 100

// This example is all the same code as the last example, just launching more coroutines and making channels bigger.
// There is no limit to how many things can write and read from channels. This makes it easy to make fan-in/fan-out
// pattens that scale well.
fun main(args: Array<String>) = runBlocking {
    val firstChannel = Channel<Int>(CONCURRENCY)
    val secondChannel = Channel<Int>(CONCURRENCY)

    val secondJobs = List(CONCURRENCY) { launch { secondStage(firstChannel, secondChannel) } }
    val thirdJobs = List(CONCURRENCY) { launch { thirdStage(secondChannel) } }

    val time = measureTimeMillis {
        val firstJob = launch { firstStage(firstChannel) }
        firstJob.join()
        firstChannel.close()
        secondJobs.forEach { it.join() }
        secondChannel.close()
        thirdJobs.forEach { it.join() }
        println("Work Done")
    }
    println("Took ${time}ms")
}

private suspend fun firstStage(outChannel: Channel<Int>) {
    for (x in 1..100) {
        outChannel.send(x)
    }
}

private suspend fun secondStage(inChannel: Channel<Int>, outChannel: Channel<Int>) {
    for (item in inChannel) {
        val mutatedItem = item * item
        delay(100)
        outChannel.send(mutatedItem)
    }
}

private suspend fun thirdStage(inChannel: Channel<Int>) {
    for (item in inChannel) {
        println("The mutated value is $item")
    }
}