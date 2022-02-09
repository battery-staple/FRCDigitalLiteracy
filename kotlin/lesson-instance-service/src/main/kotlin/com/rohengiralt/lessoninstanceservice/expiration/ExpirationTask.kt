package com.rohengiralt.lessoninstanceservice.expiration

import com.rohengiralt.lessoninstanceservice.persistence.table.LessonInstanceTable
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.slf4j.LoggerFactory
import kotlin.time.Duration.Companion.seconds

private val logger = LoggerFactory.getLogger("Expiration Task")
@OptIn(DelicateCoroutinesApi::class)
fun expirationTask() {
    GlobalScope.launch {
        while (true) {
            logger.info("Clearing expired instances")
            LessonInstanceTable.deleteExpiredInstances()
            delay(5.seconds)
        }
    }
    logger.info("Started expiration task")
}