package com.rohengiralt.sessionservice

import com.rohengiralt.sessionservice.di.initDI
import com.rohengiralt.sessionservice.plugins.configureRouting
import com.rohengiralt.sessionservice.plugins.configureSockets
import com.rohengiralt.shared.ktorConfig.configureCORS
import com.rohengiralt.shared.ktorConfig.configureCallLogging
import com.rohengiralt.shared.ktorConfig.configureSerialization
import com.rohengiralt.shared.ktorConfig.configureStatusPages
import com.typesafe.config.ConfigFactory
import io.ktor.server.config.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import org.slf4j.LoggerFactory

fun main() {
    initDI()

    embeddedServer(Netty, environment = applicationEngineEnvironment {
        log = LoggerFactory.getLogger("ktor.application")
        config = HoconApplicationConfig(ConfigFactory.load())

        module {
            configureCallLogging()
            configureCORS()
            configureStatusPages()
            configureSerialization()
//            configureHTTP()
            configureSockets()
            configureRouting()
        }

        connector {
            port = 8081
            host = "0.0.0.0"
        }
    }).start(wait = true)
}
