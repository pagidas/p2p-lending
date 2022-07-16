plugins {
    id("java-test-fixtures")
}

val testFixturesImplementation by configurations

dependencies {
    implementation(project(":util"))

    testFixturesImplementation("org.junit.jupiter:junit-jupiter-api:5.8.2")
    testFixturesImplementation("org.junit.jupiter:junit-jupiter-params:5.8.2")
    testFixturesImplementation(project(":util"))
}