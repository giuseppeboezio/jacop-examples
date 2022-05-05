plugins {
    application
}

application {
    mainClass.set("app.Main")
}

tasks.named<Test>("test") {
    useJUnitPlatform()
}