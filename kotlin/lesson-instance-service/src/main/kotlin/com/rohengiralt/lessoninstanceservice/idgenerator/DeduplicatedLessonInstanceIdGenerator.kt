package com.rohengiralt.lessoninstanceservice.idgenerator

import com.rohengiralt.lessoninstanceservice.persistence.table.LessonInstanceTable
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction

class DeduplicatedLessonInstanceIdGenerator(private val delegate: LessonInstanceIdGenerator) : LessonInstanceIdGenerator {
    override fun generateLessonInstanceId(): String =
        generateSequence(delegate::generateLessonInstanceId)
            .take(256)
            .firstOrNull(::unused)
            ?: error("Could not generate a unique id after 256 tries.")

    private fun unused(id: String): Boolean = transaction {
        LessonInstanceTable.select { LessonInstanceTable.lessonInstanceId eq id }.empty()
    }
}