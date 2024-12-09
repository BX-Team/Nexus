plugins {
    `java-library`
    alias(libs.plugins.lombok)
    alias(libs.plugins.runpaper)
    alias(libs.plugins.shadow)
    alias(libs.plugins.paperyml)
}

group = project.group
version = project.version
description = project.description

repositories {
    mavenCentral()
    maven("https://repo.papermc.io/repository/maven-public/")
    maven("https://oss.sonatype.org/content/groups/public/")
    maven("https://repo.extendedclip.com/content/repositories/placeholderapi/")
    maven("https://repo.panda-lang.org/releases")
    maven("https://repo.eternalcode.pl/releases")
    maven("https://s01.oss.sonatype.org/content/repositories/snapshots/")
    maven("https://storehouse.okaeri.eu/repository/maven-public/")
    maven("https://repo.bx-team.space/releases")
}

dependencies {
    compileOnly(libs.paper)
    implementation(project(":nexus-core"))

    library(libs.paperlib)
    library(libs.hikaricp)
    library(libs.mariadb)
    library(libs.litecommands)
    library(libs.litecommandsadventure)
    library(libs.guice)
    library(libs.classgraph)
    library(libs.triumphgui)
    library(libs.bundles.adventure)
    library(libs.bundles.okaeri)
    library(libs.bundles.multification)
    library(libs.bundles.commons)

    // Plugin dependencies
    compileOnly(libs.placeholderapi)

    implementation(libs.bstats)
}

tasks {
    java {
        toolchain.languageVersion.set(JavaLanguageVersion.of(21))
    }
    compileJava {
        options.encoding = Charsets.UTF_8.name()
        options.release.set(17)
        options.compilerArgs = listOf("-Xlint:deprecation", "-parameters")
    }
    build {
        dependsOn("shadowJar")
    }
    javadoc {
        options.encoding = Charsets.UTF_8.name()
    }
    jar {
        enabled = false
    }
    shadowJar {
        archiveFileName.set("Nexus-${project.version}.jar")
        relocate("org.bstats", "space.bxteam.nexus.dependencies.bstats")
        from(file("../LICENSE"))

        dependencies {
            exclude("META-INF/NOTICE")
            exclude("META-INF/maven/**")
            exclude("META-INF/versions/**")
            exclude("META-INF/**.kotlin_module")
        }
    }
    runServer {
        minecraftVersion("1.21.1")
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
    website = "https://github.com/BX-Team/Nexus"

    main = "space.bxteam.nexus.core.loader.NexusPlugin"
    loader = "space.bxteam.nexus.core.loader.paper.NexusLoader"

    apiVersion = "1.19"
    serverDependencies {
        register("PlaceholderAPI") {
            required = false
        }
    }

    generateLibrariesJson = true
}
