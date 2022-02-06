package com.rohengiralt.lessoninstanceservice.sessionqueue

import com.rabbitmq.client.ConnectionFactory
import com.rohengiralt.lessoninstanceservice.GeneralSpec.sessionQueueURL
import com.rohengiralt.lessoninstanceservice.generalConfig
import com.rohengiralt.sessionShared.SessionCommand
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class SessionQueue {
    private val factory = ConnectionFactory().apply {
        host = generalConfig[sessionQueueURL]
        println("Connecting to SessionQueue with host $host and port $port")
    }

    private val connection = factory.newConnection()
    private val channel = connection.createChannel()

    init {
        channel.exchangeDeclare("sessions", "fanout")
    }

    fun startLesson(lessonInstanceId: String) =
        publishCommand(SessionCommand.StartLesson(lessonInstanceId))

    fun changePage(lessonInstanceId: String, newPage: UInt) =
        publishCommand(SessionCommand.ChangePage(lessonInstanceId, newPage))

    fun endLesson(lessonInstanceId: String) =
        publishCommand(SessionCommand.EndLesson(lessonInstanceId))

    private fun publishCommand(command: SessionCommand) {
        channel.basicPublish(
            "sessions",
            "",
            null,
            Json.encodeToString(command).toByteArray()
        )
    }
}