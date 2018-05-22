package com.github.codyoss.coroutine.example.basics

import kotlinx.coroutines.experimental.delay
import kotlinx.coroutines.experimental.launch
import kotlinx.coroutines.experimental.runBlocking

// Now the whole main thread can make use of all features of coroutines
fun main(args: Array<String>) = runBlocking {
    launch {
        delay(1000)
        println("from within a coroutine")
    }
    print("Hello ")
    delay(2000)
}