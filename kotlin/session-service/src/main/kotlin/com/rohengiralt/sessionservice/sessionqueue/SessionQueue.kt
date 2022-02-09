package com.rohengiralt.sessionservice.sessionqueue

import com.rabbitmq.client.ConnectionFactory
import com.rabbitmq.client.Delivery
import com.rohengiralt.sessionShared.SessionCommand
import com.rohengiralt.sessionservice.GeneralSpec.sessionQueueURL
import com.rohengiralt.sessionservice.generalConfig
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

class SessionQueue {
    private val factory = ConnectionFactory().apply {
        host = generalConfig[sessionQueueURL]
        println("Connecting to SessionQueue with host $host and port $port")
    }

    private val connection = factory.newConnection()
    private val channel = connection.createChannel()

    private val coroutineScope: CoroutineScope = CoroutineScope(Dispatchers.IO)
    private val queueMessagesMutableSharedFlow: MutableSharedFlow<SessionCommand> = MutableSharedFlow(Channel.UNLIMITED)

    private fun deliverCallback(consumerTag: String, message: Delivery) {
        coroutineScope.launch {
            val messageText = message.body.decodeToString()
            val command = Json.decodeFromString<SessionCommand>(messageText)
            queueMessagesMutableSharedFlow.emit(command)
        }
    }

    private fun cancelCallback(consumerTag: String) {}

    init {
        channel.exchangeDeclare("sessions", "fanout")
        val queueName = channel.queueDeclare().queue
        channel.queueBind(queueName, "sessions", "")
        channel.basicConsume(queueName, true, ::deliverCallback, ::cancelCallback)
    }

    val queueMessages: Flow<SessionCommand> = queueMessagesMutableSharedFlow.asSharedFlow()
}


















