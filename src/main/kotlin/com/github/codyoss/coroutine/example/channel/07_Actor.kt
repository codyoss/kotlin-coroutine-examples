package com.github.codyoss.coroutine.example.channel

import kotlinx.coroutines.experimental.CompletableDeferred
import kotlinx.coroutines.experimental.channels.actor
import kotlinx.coroutines.experimental.runBlocking

// Creating different types of Msgs for the actor to work with
sealed class Msg

class AppendMsg(val msgFragment: String) : Msg()
object AddExclamationMsg : Msg()
// take special note of this msg type as it allows us to extract some current state from the actor
class GetCurrentMsg(val response: CompletableDeferred<String>) : Msg()

fun main(args: Array<String>) = runBlocking {
    val actor = createActor()
    actor.send(AppendMsg("Hello "))
    actor.send(AppendMsg("World"))
    actor.send(AddExclamationMsg)

    val currentMsg = CompletableDeferred<String>()
    actor.send(GetCurrentMsg(currentMsg))
    actor.close()
    println("Actor returned: ${currentMsg.await()}")
}

// The actor pattern provides some sort of state and a channel. It creates a channel with the type provided for the
// actor function. It is a common pattern to used sealed classes and when blocks with this pattern to run different
// types of messages through the actor. This pattern can be used to synchronize data... which leads into the next
// section.
private suspend fun createActor() = actor<Msg> {
    var str = ""
    for (msg in channel) {
        when (msg) {
            is AppendMsg -> str += msg.msgFragment
            is AddExclamationMsg -> str += "!"
            is GetCurrentMsg -> msg.response.complete(str)
        }
    }
}