package com.rohengiralt.lessoninstanceservice.plugins

import com.rohengiralt.ktorConfig.UnauthorizedException
import com.rohengiralt.lessoninstanceservice.di.withKoin
import com.rohengiralt.lessoninstanceservice.expirationhandler.ExpirationGenerator
import com.rohengiralt.lessoninstanceservice.idgenerator.LessonInstanceIdGenerator
import com.rohengiralt.lessoninstanceservice.persistence.table.LessonInstanceTable
import com.rohengiralt.lessoninstanceservice.sessionqueue.SessionQueue
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.plugins.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.Serializable
import org.koin.core.component.get
import org.koin.core.component.inject

fun Application.configureRouting() = withKoin {
    routing {
        authenticate {
            route("/lesson-instance") {
                post {
                    with(call.getLessonInstanceCreatorData()) {
                        val lessonInstanceId = get<LessonInstanceIdGenerator>().generateLessonInstanceId()
                        val expiration = get<ExpirationGenerator>().generateExpiration()

                        LessonInstanceTable.deleteOpenInstances(
                            creatorId = creatorId,
                            creatorIssuer = creatorIssuer
                        )

                        LessonInstanceTable.newInstance(
                            lessonInstanceId = lessonInstanceId,
                            creatorId = creatorId,
                            creatorIssuer = creatorIssuer,
                            expiration = expiration
                        )

                        call.respond(mapOf("lessonInstanceId" to lessonInstanceId))
                    }
                }

                route("/{id}") {
                    get {
                        val lessonInstanceId = call.getLessonInstanceId()
                        call.respond(
                            if (LessonInstanceTable.instanceExists(lessonInstanceId))
                                HttpStatusCode.NoContent
                            else
                                HttpStatusCode.NotFound
                        )
                    }

                    delete {
                        with(call.getLessonInstanceCreatorData()) {
                            LessonInstanceTable.deleteOpenInstances(
                                creatorId = creatorId,
                                creatorIssuer = creatorIssuer,
                                lessonInstanceId = call.getLessonInstanceId()
                            )
                        }
                    }

                    route("/active") {
                        post {
                            val sessionQueue: SessionQueue by inject()
                            sessionQueue.startLesson(call.getLessonInstanceId())
                        }

                        delete {
                            val sessionQueue: SessionQueue by inject()
                            sessionQueue.endLesson(call.getLessonInstanceId())
                        }
                    }

                    route("/current-page") {
                        put {
                            val newPage = call.receiveOrNull<CurrentPage>()?.currentPage ?: throw BadRequestException("Missing page to set")
                            val sessionQueue: SessionQueue by inject()

                            sessionQueue.changePage(call.getLessonInstanceId(), newPage)
                        }
                    }
                }
            }
        }
    }
}

fun ApplicationCall.getLessonInstanceCreatorData(): LessonInstanceCreator = withKoin {
    val user = (authentication.principal as JWTPrincipal?) ?: throw UnauthorizedException()
    LessonInstanceCreator(

        creatorId = user.jwtId ?: throw BadRequestException("JWT missing user id"),
        creatorIssuer = user.issuer ?: throw BadRequestException("JWT missing user issuer"),
    )
}

fun ApplicationCall.getLessonInstanceId(): String =
    parameters["id"] ?: throw BadRequestException("Missing lesson id")

data class LessonInstanceCreator(
    val creatorId: String,
    val creatorIssuer: String,
)

@Serializable
data class CurrentPage(val currentPage: UInt)