plugins {
    `nexus-java`
    `nexus-repositories`
    alias(libs.plugins.shadow)
    alias(libs.plugins.runpaper)
    alias(libs.plugins.paperyml)
}

dependencies {
    compileOnly(libs.paper)
    implementation(project(":nexus-core"))

    // Lombok
    compileOnly(libs.lombok)
    annotationProcessor(libs.lombok)

    library(libs.paperlib)
    library(libs.commons)
    library(libs.bundles.ormlite)
    library(libs.hikaricp)
    library(libs.mariadb)
    library(libs.postgresql)
    library(libs.bundles.litecommands)
    library(libs.guice)
    library(libs.classgraph)
    library(libs.triumphgui)
    library(libs.bundles.adventure)
    library(libs.bundles.okaeri)
    library(libs.bundles.multification)

    // Plugin dependencies
    compileOnly(libs.placeholderapi)
    implementation(libs.bstats)
}

tasks {
    java {
        toolchain.languageVersion.set(JavaLanguageVersion.of(21))
    }
    build {
        dependsOn("shadowJar")
    }
    shadowJar {
        archiveFileName.set("Nexus-${project.version}.jar")
        relocate("org.bstats", "org.bxteam.nexus.dependencies.bstats")
        from(file("../LICENSE"))

        dependencies {
            exclude("META-INF/NOTICE")
            exclude("META-INF/maven/**")
            exclude("META-INF/versions/**")
            exclude("META-INF/**.kotlin_module")
        }
    }
    runServer {
        minecraftVersion("1.21.4")
    }
}

tasks.withType(xyz.jpenilla.runtask.task.AbstractRun::class) {
    javaLauncher = javaToolchains.launcherFor {
        vendor = JvmVendorSpec.JETBRAINS
        languageVersion = JavaLanguageVersion.of(21)
    }
    jvmArgs("-XX:+AllowEnhancedClassRedefinition")
}

paper {
    name = "Nexus"
    prefix = "Nexus"
    authors = listOf("NONPLAYT", "XEN0LIT")
    website = "https://bxteam.org"

    main = "org.bxteam.nexus.core.loader.NexusPlugin"
    loader = "org.bxteam.nexus.core.loader.NexusLoader"

    apiVersion = "1.19"
    foliaSupported = true
    serverDependencies {
        register("PlaceholderAPI") {
            required = false
        }
    }

    generateLibrariesJson = true
}
