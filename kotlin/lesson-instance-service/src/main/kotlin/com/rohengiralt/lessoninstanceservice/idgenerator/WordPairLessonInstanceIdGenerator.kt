package com.rohengiralt.lessoninstanceservice.idgenerator

import kotlinx.datetime.Clock
import kotlin.random.Random

class WordPairLessonInstanceIdGenerator : LessonInstanceIdGenerator {
    private val wordlist = (this::class.java.getResource("/words/google-10000-english-usa-no-swears-medium.txt") ?: error("Can't find wordlist.")).readText().split("\n")
    private val random = Random(Clock.System.now().nanosecondsOfSecond)

    override fun generateLessonInstanceId(): String =
        "${wordlist.random(random)} ${wordlist.random(random)}"
}