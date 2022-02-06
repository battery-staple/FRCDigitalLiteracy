package com.rohengiralt.ktorConfig

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.*
import io.ktor.server.response.*

fun Application.configureStatusPages() {
    install(StatusPages) {
        exception<NotFoundException> { call, e ->
            call.respond(HttpStatusCode.NotFound, e.message ?: "")
        }
        exception<BadRequestException> { call, e ->
            call.respond(HttpStatusCode.BadRequest, e.message ?: "")
        }
        exception<UnauthorizedException> { call, _ ->
            call.respond(HttpStatusCode.Unauthorized)
        }
    }
}

class UnauthorizedException : Exception(null, null)
