package com.rohengiralt.lessoninstanceservice.di

import com.rohengiralt.lessoninstanceservice.expirationhandler.ExpirationGenerator
import com.rohengiralt.lessoninstanceservice.idgenerator.DeduplicatedLessonInstanceIdGenerator
import com.rohengiralt.lessoninstanceservice.idgenerator.LessonInstanceIdGenerator
import com.rohengiralt.lessoninstanceservice.idgenerator.WordPairLessonInstanceIdGenerator
import com.rohengiralt.lessoninstanceservice.sessionqueue.SessionQueue
import org.koin.core.context.startKoin
import org.koin.dsl.module
import org.koin.logger.SLF4JLogger
import kotlin.time.Duration.Companion.hours

fun initDI() {
    startKoin {
        SLF4JLogger()
        modules(
            module {
                single<LessonInstanceIdGenerator> { DeduplicatedLessonInstanceIdGenerator(WordPairLessonInstanceIdGenerator()) }
                single<ExpirationGenerator> { ExpirationGenerator(delay = 24.hours) }
                single<SessionQueue> { SessionQueue() }
            }
        )
    }
}