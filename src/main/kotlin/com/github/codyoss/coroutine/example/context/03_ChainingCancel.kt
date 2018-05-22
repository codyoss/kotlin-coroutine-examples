package com.github.codyoss.coroutine.example.context

import kotlinx.coroutines.experimental.delay
import kotlinx.coroutines.experimental.launch
import kotlinx.coroutines.experimental.runBlocking

fun main(args: Array<String>) = runBlocking {
    val parent = launch {
        launch(coroutineContext) {
            println("1: Starting Child")
            delay(700)
            println("1: Child Done")
        }
        val nonChild = launch {
            println("1: Starting subJobNotChild")
            delay(700)
            println("1: subJobNotChild Done")
        }
        // If a coroutine launches a non-child it is best practice to join at the end to ensure completion of work
        nonChild.join()
    }
    delay(500)
    parent.cancel()
    println("--- Parent canceled ---")
    delay(500)
}