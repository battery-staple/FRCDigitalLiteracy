package com.rohengiralt.lessoninstanceservice

import com.rohengiralt.ktorConfig.*
import com.rohengiralt.lessoninstanceservice.plugins.configureRouting
import com.typesafe.config.ConfigFactory
import io.ktor.server.config.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import org.slf4j.LoggerFactory

fun main() {
    embeddedServer(Netty, environment = applicationEngineEnvironment {
        log = LoggerFactory.getLogger("ktor.application")
        config = HoconApplicationConfig(ConfigFactory.load())

        module {
            configureCallLogging()
            configureCORS()
            configureSecurity()
            configureStatusPages()
            configureSerialization()
//            configureHTTP()
            configureRouting()
        }

        connector {
            port = 8080
            host = "0.0.0.0"
        }
    }).start(wait = true)
}