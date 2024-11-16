plugins {
    `java-library`
    alias(libs.plugins.lombok)
}

repositories {
    mavenCentral()
    maven("https://repo.papermc.io/repository/maven-public/")
}

dependencies {
    compileOnly(libs.paper)
}

tasks.withType<JavaCompile>() {
    options.encoding = "UTF-8"
    options.release = 17
}
