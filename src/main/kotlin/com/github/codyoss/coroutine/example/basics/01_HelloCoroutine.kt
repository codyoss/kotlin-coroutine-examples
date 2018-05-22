package com.github.codyoss.coroutine.example.basics

import kotlinx.coroutines.experimental.delay
import kotlinx.coroutines.experimental.launch

fun main(args: Array<String>) {
    // kicks off a coroutine
    launch {

        // suspension point in the coroutine
        delay(1000)

        println("from within a coroutine")
    }
    // Print from the main thread
    print("Hello ")

    // waiting to synchronize, just like back in college
    Thread.sleep(2000)
}