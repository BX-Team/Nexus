plugins {
    `nexus-java`
    `nexus-repositories`
}

dependencies {
    implementation(project(":nexus-api"))
    api(project(":nexus-docs"))

    // Lombok
    compileOnly(libs.lombok)
    annotationProcessor(libs.lombok)

    // Libraries
    compileOnly(libs.paper)
    compileOnly(libs.paperlib)
    compileOnly(libs.commons)
    compileOnly(libs.bundles.ormlite)
    compileOnly(libs.hikaricp)
    compileOnly(libs.mariadb)
    compileOnly(libs.postgresql)
    compileOnly(libs.bundles.litecommands)
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
