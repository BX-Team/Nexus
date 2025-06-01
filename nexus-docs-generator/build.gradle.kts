plugins {
    `java-library`
    `nexus-repositories`
}

group = project.group
version = project.version

dependencies {
    implementation(project(":nexus-core"))
    implementation(project(":nexus-docs"))

    implementation(libs.guava)
    implementation(libs.gson)
    implementation(libs.classgraph)
    implementation(libs.bundles.litecommands)

    runtimeOnly(libs.paper)
    runtimeOnly(libs.paperlib)
    runtimeOnly(libs.bundles.ormlite)
    runtimeOnly(libs.bundles.commons)
    runtimeOnly(libs.hikaricp)
    runtimeOnly(libs.mariadb)
    runtimeOnly(libs.postgresql)
    runtimeOnly(libs.bundles.litecommands)
    runtimeOnly(libs.guice)
    runtimeOnly(libs.classgraph)
    runtimeOnly(libs.triumphgui)
    runtimeOnly(libs.bundles.adventure)
    runtimeOnly(libs.bundles.okaeri)
    runtimeOnly(libs.bundles.multification)
    runtimeOnly(libs.placeholderapi)
    runtimeOnly(libs.bstats)
}

tasks {
    compileJava {
        options.encoding = Charsets.UTF_8.name()
        options.release.set(21)
    }
}
