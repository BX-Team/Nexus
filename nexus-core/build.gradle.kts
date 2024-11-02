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
}

dependencies {
    implementation(project(":nexus-api"))

    // Libraries
    compileOnly(libs.paper)
    compileOnly(libs.hikaricp)
    compileOnly(libs.mariadb)
    compileOnly(libs.litecommands)
    compileOnly(libs.litecommandsadventure)
    compileOnly(libs.configlib)
    compileOnly(libs.guice)
    compileOnly(libs.classgraph)
    compileOnly(libs.bundles.adventure)

    // Plugin dependencies
    compileOnly(libs.placeholderapi)

    compileOnly(libs.bstats)
}

tasks.withType<JavaCompile>() {
    options.encoding = "UTF-8"
    options.release = 17
    options.compilerArgs.add("-parameters")
}
