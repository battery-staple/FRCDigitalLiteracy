package com.rohengiralt.ktorConfig

import io.ktor.server.application.*
import io.ktor.server.plugins.*
import org.slf4j.event.Level

fun Application.configureCallLogging() {
    install(CallLogging) {
        level = Level.INFO
    }
}