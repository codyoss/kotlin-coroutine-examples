package com.github.codyoss.coroutine.example.exceptions

import kotlinx.coroutines.experimental.launch

fun main(args: Array<String>) {
    // You can pretend like the exception did not happen but it can't be handled gracefully
    launch {
        throw RuntimeException()
    }
    Thread.sleep(500)
    println("Pretending nothing happened")

    launch {
        // To handle the exception you must do it in the coroutine scope
        try {
            throw RuntimeException()
        } catch (e: RuntimeException) {
            println("Handled with grace")
        }
    }

    Thread.sleep(500)
}
