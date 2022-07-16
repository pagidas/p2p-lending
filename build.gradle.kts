import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.7.10" apply false
}

group = "org.example"
version = "1.0-SNAPSHOT"

subprojects {
    apply {
        plugin("org.jetbrains.kotlin.jvm")
    }

    repositories {
        mavenCentral()
    }

    val testImplementation by configurations
    val testRuntimeOnly by configurations

    dependencies {
        testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.2")
        testImplementation("org.junit.jupiter:junit-jupiter-params:5.8.2")
        testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.2")
    }

    tasks.withType<Test> {
        useJUnitPlatform()
    }

    tasks.withType<KotlinCompile> {
        kotlinOptions.jvmTarget = "11"
    }
}
