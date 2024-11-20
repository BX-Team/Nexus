plugins {
    `java-library`
    alias(libs.plugins.lombok)
}

repositories {
    mavenCentral()
    maven("https://repo.papermc.io/repository/maven-public/")
    maven("https://oss.sonatype.org/content/groups/public/")
    maven("https://repo.extendedclip.com/content/repositories/placeholderapi/")
    maven("https://repo.panda-lang.org/releases")
    maven("https://repo.eternalcode.pl/releases")
    maven("https://s01.oss.sonatype.org/content/repositories/snapshots/")
    maven("https://storehouse.okaeri.eu/repository/maven-public/")
}

dependencies {
    implementation(project(":nexus-api"))

    // Libraries
    compileOnly(libs.paper)
    compileOnly(libs.hikaricp)
    compileOnly(libs.mariadb)
    compileOnly(libs.litecommands)
    compileOnly(libs.litecommandsadventure)
    compileOnly(libs.guice)
    compileOnly(libs.classgraph)
    compileOnly(libs.triumphgui)
    compileOnly(libs.bundles.adventure)
    compileOnly(libs.bundles.okaeri)
    compileOnly(libs.bundles.multification)

    // Plugin dependencies
    compileOnly(libs.placeholderapi)

    compileOnly(libs.bstats)
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
    options.release = 17
    options.compilerArgs = listOf("-Xlint:deprecation", "-parameters")
}
