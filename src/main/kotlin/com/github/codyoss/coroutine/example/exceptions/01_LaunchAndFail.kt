package com.github.codyoss.coroutine.example.exceptions

import kotlinx.coroutines.experimental.launch

fun main(args: Array<String>) {
    try {
        // You can't catch exceptions thrown by coroutines
        launch { throw RuntimeException() }
    } catch (e: RuntimeException) {
        println("caught and exceptions in a normal coroutine")
    }
    Thread.sleep(1000)
}