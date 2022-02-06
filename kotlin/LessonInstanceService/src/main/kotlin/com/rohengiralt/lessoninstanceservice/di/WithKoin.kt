package com.rohengiralt.lessoninstanceservice.di

import org.koin.core.component.KoinComponent

fun <R> withKoin(block: KoinComponent.() -> R): R = KoinComponentObject.block()

private object KoinComponentObject: KoinComponent {}