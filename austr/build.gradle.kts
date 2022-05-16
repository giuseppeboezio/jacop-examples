plugins {
    application
    kotlin("jvm")
}

application {
    mainClass.set("app.MainKt")
}

tasks.named<Test>("test") {
    useJUnitPlatform()
}