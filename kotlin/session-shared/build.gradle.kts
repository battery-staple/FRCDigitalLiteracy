val ktor_version: String by project
val serialization_version: String by project

plugins {
    kotlin("jvm") version "1.6.10"
    kotlin("plugin.serialization") version "1.6.10"
}

group = "com.rohengiralt"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven { url = uri("https://maven.pkg.jetbrains.space/public/p/ktor/eap") }
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation(project(":shared"))
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-core:$serialization_version")
}