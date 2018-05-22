package com.github.codyoss.coroutine.example.context

import kotlinx.coroutines.experimental.delay
import kotlinx.coroutines.experimental.launch
import kotlinx.coroutines.experimental.runBlocking

fun main(args: Array<String>) = runBlocking {
    launch(coroutineContext) {
        println("I am a child of $coroutineContext")
        delay(1000)
        println("Because my parent loves me, it can not complete without me, even without `.join()`")
    }
    println("I'm the parent")
}