package com.github.codyoss.coroutine.example.channel

import kotlinx.coroutines.experimental.channels.Channel
import kotlinx.coroutines.experimental.launch
import kotlinx.coroutines.experimental.runBlocking

fun main(args: Array<String>) = runBlocking {
    val channel = Channel<Int>(5)

    // launching a coroutine to put items on a channel
    launch {
        for (i in 1..5) {
            channel.send(i)
            println("Sent $i")
        }
        // Once a channel is done being used, `.close()` should be called on it. This signals the channel will not be
        // having any more data be put onto it. This will also cause for loops iterating over channels to break once
        // the channel has been drained of all data.
        channel.close()
    }

    iterateOverChannel(channel)
    println("Shutting down gracefully after all records pulled off channel")

}

private suspend fun iterateOverChannel(channel: Channel<Int>) {
    // Just like a list, a channel can be iterated over. Calling `.close()` on the channel above will signal to break
    // out of this loop.
    for (item in channel) {
        println("Pulled $item off channel")
    }
    println("Broke out of the loop, the channel must me closed...")
}