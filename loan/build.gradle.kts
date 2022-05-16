plugins {
    application
    kotlin("jvm")
}

application {
    mainClass.set("app.Main")
}

tasks.named<Test>("test") {
    useJUnitPlatform()
}

dependencies {
    implementation("it.unibo.tuprolog:solve-classic-jvm:0.20.4")
    implementation("it.unibo.tuprolog:parser-theory-jvm:0.20.4")
}
