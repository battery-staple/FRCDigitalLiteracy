package com.rohengiralt.lessoninstanceservice.persistence.table

import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.kotlin.datetime.timestamp
import org.jetbrains.exposed.sql.transactions.transaction
import java.sql.SQLException

object LessonInstanceTable : Table() {
    val lessonInstanceId: Column<String> = varchar("lesson_instance_id", 17)
    val creatorId: Column<String> = varchar("creator", 256)
    val creatorIssuer: Column<String> = varchar("creator_issuer", 256)
    val expiration: Column<Instant> = timestamp("expiration")

    override val primaryKey = PrimaryKey(lessonInstanceId)

    init {
        uniqueIndex(creatorId, creatorIssuer)
    }

    fun instanceExists(lessonInstanceId: String): Boolean = transaction {
        !select { LessonInstanceTable.lessonInstanceId eq lessonInstanceId }
            .empty()
    }

    fun newInstance(lessonInstanceId: String, creatorId: String, creatorIssuer: String, expiration: Instant): Boolean = transaction {
        try {
            insert {
                it[this.lessonInstanceId] = lessonInstanceId
                it[this.creatorId] = creatorId
                it[this.creatorIssuer] = creatorIssuer
                it[this.expiration] = expiration
            }
            true
        } catch (e: SQLException) {
            false
        }
    }

    fun deleteOpenInstances(creatorId: String, creatorIssuer: String, lessonInstanceId: String? = null) = transaction {
        deleteWhere {
            var query = (LessonInstanceTable.creatorId eq creatorId) and (LessonInstanceTable.creatorIssuer eq creatorIssuer)
            if (lessonInstanceId != null) {
                query = query and (LessonInstanceTable.lessonInstanceId eq lessonInstanceId)
            }
            query
        }
    }

    fun deleteExpiredInstances() = transaction {
        val now = Clock.System.now()
        deleteWhere { expiration lessEq now }
    }
}