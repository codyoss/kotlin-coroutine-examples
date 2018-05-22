package com.github.codyoss.coroutine.example.channel

import kotlinx.coroutines.experimental.channels.produce
import kotlinx.coroutines.experimental.runBlocking

fun main(args: Array<String>) = runBlocking {
    val producer = evenNumberProducer()

    repeat(5) {
        println("Pulled ${producer.receive()} off channel")
    }
    // This is the equivalent to `close()` on a normal channel
    producer.cancel()
    println("Work done and coroutine cleaned up")
}

// `produce` is syntactic sugar for returning a `ReceiveChannel<T>`. Which is a channel items can only be pulled off of
fun evenNumberProducer() = produce {
    var x = 0
    while (true) {
        x += 2
        send(x)
    }
}