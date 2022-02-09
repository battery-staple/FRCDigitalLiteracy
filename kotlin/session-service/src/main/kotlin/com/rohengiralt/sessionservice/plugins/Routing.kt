package com.rohengiralt.sessionservice.plugins

import com.rohengiralt.sessionservice.GeneralSpec
import com.rohengiralt.sessionservice.generalConfig
import com.rohengiralt.sessionservice.sessionqueue.SessionQueue
import com.rohengiralt.shared.di.withKoin
import com.rohengiralt.shared.model.LessonInstanceId
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.*
import io.ktor.server.routing.*
import io.ktor.server.websocket.*
import io.ktor.websocket.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.serialization.SerializationException
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.koin.core.component.get
import org.koin.core.component.inject

fun Application.configureRouting() = withKoin {
    routing {
        route("/session") {
            webSocket {
                val requestData = getLessonInstanceId() ?: error("Could not get lessonInstanceId")
                val sessionQueue: SessionQueue by inject()

                with(requestData) {
                    if (isValidLessonInstanceId(lessonInstanceId)) {
                        sessionQueue.queueMessages
                            .map(Json::encodeToString)
                            .map(Frame::Text)
                            .collect(outgoing::send)
                    }
                }
            }
        }
    }
}

suspend fun DefaultWebSocketSession.getLessonInstanceId(): LessonInstanceId? {
    for (frame in incoming) {
        if (frame is Frame.Text) {
            val text = frame.readText()
            return try {
                Json.decodeFromString(text)
            } catch (e: SerializationException) {
                throw BadRequestException("Could not parse lesson id")
            }
        }
    }

    return null
}


val httpClient: HttpClient = withKoin { get() }
private suspend fun isValidLessonInstanceId(lessonInstanceId: String): Boolean {
    val response: HttpResponse = httpClient.get(
        urlString = "${generalConfig[GeneralSpec.lessonInstanceServiceUrl]}/lesson-instance/$lessonInstanceId"
    )

    return response.status.isSuccess()
}