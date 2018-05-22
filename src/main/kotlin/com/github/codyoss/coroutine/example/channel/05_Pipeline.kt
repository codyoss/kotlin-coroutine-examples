package com.github.codyoss.coroutine.example.channel

import kotlinx.coroutines.experimental.channels.Channel
import kotlinx.coroutines.experimental.delay
import kotlinx.coroutines.experimental.launch
import kotlinx.coroutines.experimental.runBlocking
import kotlin.system.measureTimeMillis

fun main(args: Array<String>) = runBlocking {
    // creating channels for coroutines to communicate with
    val firstChannel = Channel<Int>(1)
    val secondChannel = Channel<Int>(1)

    // launching worker threads that read/write from/to channels
    val secondJob = launch { secondStage(firstChannel, secondChannel) }
    val thirdJob = launch { thirdStage(secondChannel) }

    val time = measureTimeMillis {
        // kick off my producer
        val firstJob = launch { firstStage(firstChannel) }

        // tear everything down nicely
        firstJob.join()
        firstChannel.close()
        secondJob.join()
        secondChannel.close()
        thirdJob.join()
        println("Work Done")
    }
    println("Took ${time}ms")
}

// producing numbers 1 to 100 onto channel
private suspend fun firstStage(outChannel: Channel<Int>) {
    for (x in 1..100) {
        outChannel.send(x)
    }
}

// squaring value from channel and sending it to the next step
private suspend fun secondStage(inChannel: Channel<Int>, outChannel: Channel<Int>) {
    for (item in inChannel) {
        val mutatedItem = item * item
        // mocking some long work operation
        delay(100)
        outChannel.send(mutatedItem)
    }
}

// printing out the final mutated value
private suspend fun thirdStage(inChannel: Channel<Int>) {
    for (item in inChannel) {
        println("The mutated value is $item")
    }
}

