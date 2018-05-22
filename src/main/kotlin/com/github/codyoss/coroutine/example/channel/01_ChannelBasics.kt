package com.github.codyoss.coroutine.example.channel

import kotlinx.coroutines.experimental.channels.Channel
import kotlinx.coroutines.experimental.delay
import kotlinx.coroutines.experimental.launch
import kotlinx.coroutines.experimental.runBlocking

fun main(args: Array<String>) = runBlocking {
    // Think of a channel as a blocking queue.
    val channel = Channel<Int>()

    launch {
        for (i in 1..10) {
            // send will only send an item on the channel if it has room. This channel can only hold one item at a time
            println("Sending $i")
            channel.send(i)
        }
    }

    repeat(10) {
        // receive pulls items off of the channel
        println("Pulled ${channel.receive()} off channel")
        delay(10)
    }
}