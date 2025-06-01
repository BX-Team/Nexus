plugins {
    `java-library`
    `nexus-repositories`
}

group = project.group
version = project.version

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
