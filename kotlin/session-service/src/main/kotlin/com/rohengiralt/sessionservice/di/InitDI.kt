@file:Suppress("RemoveExplicitTypeArguments")

package com.rohengiralt.sessionservice.di

import com.rohengiralt.sessionservice.sessionqueue.SessionQueue
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import org.koin.core.context.startKoin
import org.koin.dsl.module
import org.koin.logger.SLF4JLogger

fun initDI() {
    startKoin {
        SLF4JLogger()
        modules(
            module {
                single<HttpClient> { HttpClient(CIO) }
                single<SessionQueue> { SessionQueue() }
            }
        )
    }
}