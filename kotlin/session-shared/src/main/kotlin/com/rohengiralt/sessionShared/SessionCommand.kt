package com.rohengiralt.sessionShared

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
sealed class SessionCommand {
    abstract val lessonInstanceId: String
    @Serializable
    @SerialName("StartLesson")
    class StartLesson(override val lessonInstanceId: String) : SessionCommand()
    @Serializable
    @SerialName("ChangePage")
    class ChangePage(override val lessonInstanceId: String, val pageNumber: UInt) : SessionCommand()
    @Serializable
    @SerialName("EndLesson")
    class EndLesson(override val lessonInstanceId: String) : SessionCommand()
}