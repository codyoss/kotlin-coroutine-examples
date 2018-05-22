package com.github.codyoss.coroutine.example.channel

import kotlinx.coroutines.experimental.channels.Channel
import kotlinx.coroutines.experimental.delay
import kotlinx.coroutines.experimental.launch
import kotlinx.coroutines.experimental.runBlocking

fun main(args: Array<String>) = runBlocking {
    // This is exactly like the last example except this one can hold up to 10 items on the channel at a given time
    val channel = Channel<Int>(10)

    launch {
        for (i in 1..10) {
            channel.send(i)
            println("Sent $i")
            delay(1)
        }
    }

    repeat(10) {
        println("Pulled ${channel.receive()} off channel")
        delay(2)
    }
}