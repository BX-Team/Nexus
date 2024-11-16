plugins {
    `java-library`
    alias(libs.plugins.lombok)
}

repositories {
    mavenCentral()
    maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
}

dependencies {
    compileOnly(libs.paper)
}

tasks.withType<JavaCompile>() {
    options.encoding = "UTF-8"
    options.release = 17
}
