package com.rohengiralt.plugins

import io.ktor.server.routing.*
import io.ktor.server.application.*
import io.ktor.server.auth.*

fun Application.configureRouting() {
    routing {
        authenticate {
            route("/lesson-instance") {
            }
        }
    }
}