package com.rohengiralt.lessoninstanceservice.expirationhandler

import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlin.time.Duration

class ExpirationGenerator(private val delay: Duration) {
    fun generateExpiration(): Instant =
        Clock.System.now() + delay
}