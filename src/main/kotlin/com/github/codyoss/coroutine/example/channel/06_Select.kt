package com.github.codyoss.coroutine.example.channel

import kotlinx.coroutines.experimental.channels.ReceiveChannel
import kotlinx.coroutines.experimental.channels.produce
import kotlinx.coroutines.experimental.delay
import kotlinx.coroutines.experimental.runBlocking
import kotlinx.coroutines.experimental.selects.select

fun main(args: Array<String>) = runBlocking {
    val evens = evenProducer()
    val odds = oddProducer()
    repeat(10) {
        selectFun(evens, odds)
    }
    evens.cancel()
    odds.cancel()
    println("Done")


}

// `select` is a construct that allows to try to read from several channels at the same time. It will execute the first
// onReceive that is available to run. It is sort-of-like the when/switch of channels.
private suspend fun selectFun(evens: ReceiveChannel<Int>, odds: ReceiveChannel<Int>) = select<Unit> {
    evens.onReceive {
        println("Got even: $it")
    }
    odds.onReceive {
        println("Got odd: $it")
    }
}

private suspend fun evenProducer() = produce {
    var even = 0
    while (true) {
        even += 2
        send(even)
        delay(100)
    }
}

private suspend fun oddProducer() = produce {
    var even = 1
    while (true) {
        even += 2
        send(even)
        delay(200)
    }
}