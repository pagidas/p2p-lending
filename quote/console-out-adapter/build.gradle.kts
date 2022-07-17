dependencies {
    implementation(project(":quote:domain"))
    implementation(project(":util"))
    testImplementation(testFixtures(project(":quote:domain")))
}

