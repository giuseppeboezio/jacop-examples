plugins {
    application
}

subprojects {
    repositories {
        mavenCentral()
    }

    apply(plugin = "java")

    dependencies {
        implementation("org.jacop:jacop:4.8.0")
        testImplementation("org.junit.jupiter:junit-jupiter:5.8.1")
        implementation("com.google.guava:guava:30.1.1-jre")
    }

}
