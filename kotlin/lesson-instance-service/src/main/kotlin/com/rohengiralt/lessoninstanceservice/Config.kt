package com.rohengiralt.lessoninstanceservice

import com.uchuhimo.konf.Config
import com.uchuhimo.konf.ConfigSpec

internal val generalConfig = Config { addSpec(GeneralSpec) }
    .from.systemProperties()
    .from.env()

internal object GeneralSpec : ConfigSpec() {
    val sessionQueueURL by required<String>()
}