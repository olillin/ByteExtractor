import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    kotlin("jvm") version "1.9.21"
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

group = "com.olillin.byteextractor"
version = "1.1"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.jetbrains.kotlinx:kotlinx-cli-jvm:0.3.6")
}

kotlin {
    jvmToolchain(17)
}

tasks.withType<Jar> {
    manifest {
        attributes (
            "Main-Class" to "MainKt"
        )
    }
}

tasks.getByName("build").dependsOn(tasks.getByName("shadowJar"))

tasks.withType<ShadowJar> {
    minimize()
    archiveClassifier = null
}