plugins {
    `java-library`
}

group = project.group
version = project.version

repositories {
    mavenCentral()
    maven("https://repo.panda-lang.org/releases")
}

dependencies {
    compileOnly(libs.guava)
    compileOnly(libs.gson)
    compileOnly(libs.classgraph)
    compileOnly(libs.bundles.litecommands)
}

tasks {
    compileJava {
        options.encoding = Charsets.UTF_8.name()
        options.release.set(17)
    }
}