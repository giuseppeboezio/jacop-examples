plugins {
    application
}

repositories {
    mavenCentral()
}

application {
    mainClass.set("src/main/java/Main")
}

dependencies {
    testImplementation("junit")

    implementation("com.google.guava:guava:30.1.1-jre")
}

tasks.named<Test>("test") {
    useJUnitPlatform()
}