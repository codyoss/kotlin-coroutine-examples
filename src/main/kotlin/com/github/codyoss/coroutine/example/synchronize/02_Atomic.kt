package com.github.codyoss.coroutine.example.synchronize

import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.runBlocking
import java.util.concurrent.atomic.AtomicInteger

// You can make use of the Java Atomic package to solve this problem
private var counter = AtomicInteger()

fun main(args: Array<String>) = runBlocking {
    runAllTheCoroutines(CommonPool) {
        counter.incrementAndGet()
    }
    println("Counter: ${counter.get()}")
}