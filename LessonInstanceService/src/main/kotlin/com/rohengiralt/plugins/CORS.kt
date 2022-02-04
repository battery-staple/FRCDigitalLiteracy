package com.rohengiralt.plugins

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.*

fun Application.configureCORS() {
    install(CORS) {
        header(HttpHeaders.Authorization)
        header(HttpHeaders.AccessControlAllowHeaders)
        header(HttpHeaders.AccessControlAllowOrigin)
        header(HttpHeaders.AccessControlAllowMethods)
        header(HttpHeaders.AccessControlMaxAge)
        header(HttpHeaders.ContentType)
        allowNonSimpleContentTypes = true
        anyHost()
    }
}