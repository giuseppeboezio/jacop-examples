plugins {
    application
}

application {
    mainClass.set("src/main/java/Main")
}

tasks.named<Test>("test") {
    useJUnitPlatform()
}