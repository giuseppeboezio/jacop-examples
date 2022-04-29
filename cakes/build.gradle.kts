plugins {
    application
}

application {
    mainClass.set("jacop-examples.Main")
}

tasks.named<Test>("test") {
    useJUnitPlatform()
}