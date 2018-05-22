package com.github.codyoss.coroutine.example.basics

import kotlinx.coroutines.experimental.delay
import kotlinx.coroutines.experimental.launch
import kotlinx.coroutines.experimental.runBlocking

fun main(args: Array<String>) = runBlocking {
    // launching a coroutine returns a job
    val jobs = List(3) {
        launch { coroutineAtWork(it) }
    }

    // waits until each job is finished executing fully
    jobs.forEach { it.join() }
}

// suspend keyword tells kotlin this function will be called from a coroutine. Kotlin is then smart enough to pass the
// whole coroutineContext, we will get back to this, into this function. That was why we can call delay without "being
// in a coroutine".
suspend fun coroutineAtWork(i: Int) {
    delay(100)
    println("$i: Work done!")
}