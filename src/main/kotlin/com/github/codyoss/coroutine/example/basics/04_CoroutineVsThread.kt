package com.github.codyoss.coroutine.example.basics

import kotlinx.coroutines.experimental.delay
import kotlinx.coroutines.experimental.launch
import kotlinx.coroutines.experimental.runBlocking
import kotlin.concurrent.thread
import kotlin.system.measureTimeMillis

private const val NUM_OF_THREADS = 100_000

fun main(args: Array<String>) {
    // When I ran this test coroutines finish 25x faster
    benchTimeToLaunch()

    // Launching so many threads all at once will fail on any standard machine due to lack of memory
    benchMemToLaunch()
}

fun benchTimeToLaunch() {
    threadBenchTime()
    coroutineBenchTime()
}

fun threadBenchTime() {
    var counter = 0
    val time = measureTimeMillis {
        repeat(NUM_OF_THREADS) {
            thread {
                counter += 1
            }
        }
    }
    println("Created $NUM_OF_THREADS threads in ${time}ms.")
}

fun coroutineBenchTime() {
    var counter = 0
    val time = measureTimeMillis {
        repeat(NUM_OF_THREADS) {
            launch {
                counter += 1
            }
        }
    }
    println("Created $NUM_OF_THREADS coroutines in ${time}ms.")
}

fun benchMemToLaunch() {
    println("Testing Coroutines:")
    coroutineBenchMem()

    println("Clearing memory:")
    System.gc()
    Thread.sleep(1000)

    println("Testing Threads:")
    threadBenchMem()
}

fun coroutineBenchMem() = runBlocking {
    repeat(NUM_OF_THREADS) {
        launch {
            delay(1000)
            print(".")
        }
    }

    delay(2000)
    println()
}

fun threadBenchMem() {
    repeat(NUM_OF_THREADS) {
        thread {
            Thread.sleep(1000)
            print(".")
        }
    }

    Thread.sleep(2000)
    println()
}