package com.github.codyoss.coroutine.example.basics

import kotlinx.coroutines.experimental.delay
import kotlinx.coroutines.experimental.launch
import kotlinx.coroutines.experimental.runBlocking

fun main(args: Array<String>) {
    launch {
        delay(1000)
        println("from within a coroutine")
    }
    print("Hello ")

    // `Thread.sleep` was blocking. To use non-blocking suspending delay we need to be within a coroutine
    runBlocking {
        delay(2000)
    }
}