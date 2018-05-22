package com.github.codyoss.coroutine.example.synchronize

import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.runBlocking
import kotlinx.coroutines.experimental.sync.Mutex
import kotlinx.coroutines.experimental.sync.withLock

private var counter = 0

// Just like many other languages, such as C or Go, Kotlin can make use of mutexes for critical sections. This is a nice
// option if not using the atomic package
private val mutex = Mutex()

fun main(args: Array<String>) = runBlocking {
    runAllTheCoroutines(CommonPool) {
        mutex.withLock { counter++ }
    }
    println("Counter: $counter")
}