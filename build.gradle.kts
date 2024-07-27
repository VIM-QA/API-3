plugins {
    kotlin("plugin.serialization") version "1.9.23"
    kotlin("jvm") version "1.9.23"
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {

    implementation("com.squareup.okhttp3:okhttp:4.10.0")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4")
}




tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(17)
}