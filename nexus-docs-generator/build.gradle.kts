plugins {
    `java-library`
}

group = project.group
version = project.version

repositories {
    mavenCentral()
    maven("https://repo.papermc.io/repository/maven-public/")
    maven("https://oss.sonatype.org/content/groups/public/")
    maven("https://repo.extendedclip.com/content/repositories/placeholderapi/")
    maven("https://repo.panda-lang.org/releases")
    maven("https://repo.eternalcode.pl/releases")
    maven("https://s01.oss.sonatype.org/content/repositories/snapshots/")
    maven("https://storehouse.okaeri.eu/repository/maven-public/")
    maven("https://repo.bxteam.org/releases")
}

dependencies {
    implementation(project(":nexus-core"))
    implementation(project(":nexus-docs"))

    implementation(libs.guava)
    implementation(libs.gson)
    implementation(libs.classgraph)
    implementation(libs.bundles.litecommands)

    runtimeOnly(libs.paper)
    runtimeOnly(libs.paperlib)
    runtimeOnly(libs.commons)
    runtimeOnly(libs.bundles.ormlite)
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
        options.release.set(17)
    }
}