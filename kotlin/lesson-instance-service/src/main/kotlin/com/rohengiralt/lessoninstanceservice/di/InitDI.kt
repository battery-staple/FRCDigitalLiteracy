@file:Suppress("RemoveExplicitTypeArguments")

package com.rohengiralt.lessoninstanceservice.di

import com.rohengiralt.lessoninstanceservice.expiration.ExpirationGenerator
import com.rohengiralt.lessoninstanceservice.id.DeduplicatedLessonInstanceIdGenerator
import com.rohengiralt.lessoninstanceservice.id.LessonInstanceIdGenerator
import com.rohengiralt.lessoninstanceservice.id.WordPairLessonInstanceIdGenerator
import com.rohengiralt.lessoninstanceservice.sessionqueue.SessionQueue
import org.koin.core.context.startKoin
import org.koin.dsl.module
import org.koin.logger.SLF4JLogger
import kotlin.time.Duration.Companion.seconds

fun initDI() {
    startKoin {
        SLF4JLogger()
        modules(
            module {
                single<LessonInstanceIdGenerator> { DeduplicatedLessonInstanceIdGenerator(WordPairLessonInstanceIdGenerator()) }
                single<ExpirationGenerator> { ExpirationGenerator(delay = 12.seconds) }
                single<SessionQueue> { SessionQueue() }
            }
        )
    }
}