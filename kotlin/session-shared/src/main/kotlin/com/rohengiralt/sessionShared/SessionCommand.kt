package com.rohengiralt.sessionShared

import kotlinx.serialization.Serializable

@Serializable
sealed class SessionCommand() {
    abstract val lessonInstanceId: String

    @Serializable
    class StartLesson(override val lessonInstanceId: String) : SessionCommand()
    @Serializable
    class ChangePage(override val lessonInstanceId: String, val pageNumber: UInt) : SessionCommand()
    @Serializable
    class EndLesson(override val lessonInstanceId: String) : SessionCommand()
}