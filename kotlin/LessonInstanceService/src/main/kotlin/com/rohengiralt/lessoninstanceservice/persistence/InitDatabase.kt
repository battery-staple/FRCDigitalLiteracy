package com.rohengiralt.lessoninstanceservice.persistence

import com.rohengiralt.lessoninstanceservice.persistence.DatabaseSpec.password
import com.rohengiralt.lessoninstanceservice.persistence.DatabaseSpec.url
import com.rohengiralt.lessoninstanceservice.persistence.DatabaseSpec.username
import com.rohengiralt.lessoninstanceservice.persistence.table.LessonInstanceTable
import kotlinx.coroutines.delay
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.StdOutSqlLogger
import org.jetbrains.exposed.sql.addLogger
import org.jetbrains.exposed.sql.transactions.transaction
import java.sql.SQLException

tailrec suspend fun initDatabase(tries: Int = 100) {
    println("Trying to connect to database at ${databaseConfig[url]}")

    if(!connectToDatabase()) {
        delay(100)
        if (tries > 0) {
            return initDatabase(tries - 1)
        } else {
            error("Could not connect to database")
        }
    }

    println("Connected to database")

    transaction {
        addLogger(StdOutSqlLogger)
        SchemaUtils.create(LessonInstanceTable)
    }
}

private fun connectToDatabase(): Boolean {
    try {
        Database.connect(
            databaseConfig[url],
            driver = "org.postgresql.Driver",
            user = databaseConfig[username],
            password = databaseConfig[password]
        )
    } catch (e: SQLException) {
        return false
    }

    return transaction {
        try { // have to catch inside the transaction lest the error be logged
            !connection.isClosed
        } catch (e: SQLException) {
            false
        }
    }
}