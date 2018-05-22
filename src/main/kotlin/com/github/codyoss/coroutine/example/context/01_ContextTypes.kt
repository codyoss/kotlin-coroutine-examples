package com.github.codyoss.coroutine.example.context

import kotlinx.coroutines.experimental.*

// This code example is based off
// https://github.com/Kotlin/kotlinx.coroutines/blob/master/core/kotlinx-coroutines-core/src/test/kotlin/guide/example-context-01.kt
// Coroutines are always launched in a context/dispatcher. This example shows the some of the different built-in
// contexts.
fun main(args: Array<String>) = runBlocking {
    val jobs = arrayListOf<Job>()

    // not confined -- will work with main thread until first suspension
    jobs += launch(Unconfined) {
        println("      'Unconfined': I'm working in thread ${Thread.currentThread().name}")
        delay(10)
        println("      'Unconfined': I'm working in thread ${Thread.currentThread().name}")
    }

    // context of the parent, runBlocking coroutine. When a coroutine spawns children like this it does not have to call
    // `.join()` as they will be awaited for by default.
    jobs += launch(coroutineContext) {
        println("'coroutineContext': I'm working in thread ${Thread.currentThread().name}")
    }

    // will get dispatched to ForkJoinPool.commonPool (or equivalent). If a dispatcher is
    // not provided this is the default
    jobs += launch(CommonPool) {
        println("      'CommonPool': I'm working in thread ${Thread.currentThread().name}")
    }

    // will get its own new thread
    jobs += launch(newSingleThreadContext("MyOwnThread")) {
        println("          'newSTC': I'm working in thread ${Thread.currentThread().name}")
    }
    jobs.forEach { it.join() }
}