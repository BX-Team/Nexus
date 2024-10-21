plugins {
    `java-library`
    `maven-publish`
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
}

dependencies {
    compileOnly(libs.paper)
    implementation(project(":nexus-core"))

    library(libs.hikaricp)
    library(libs.mariadb)
    library(libs.litecommands)
    library(libs.litecommandsadventure)
    library(libs.configlib)
    library(libs.guice)

    // Plugin dependencies
    compileOnly(libs.placeholderapi)

    implementation(libs.bstats)
}

tasks {
    compileJava {
        options.encoding = Charsets.UTF_8.name()
        options.release.set(17)
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

paper {
    name = "Nexus"
    prefix = "Nexus"
    authors = listOf("NONPLAYT", "XEN0LIT")
    website = "https://github.com/BX-Team/Nexus"

    main = "space.bxteam.nexus.core.loader.NexusPlugin"
    loader = "space.bxteam.nexus.core.loader.NexusLoader"

    apiVersion = "1.19"
    serverDependencies {
        register("PlaceholderAPI") {
            required = false
        }
    }

    generateLibrariesJson = true
}

publishing {
    repositories {
        maven {
            name = "nexus"
            if (project.version.toString().contains("-SNAPSHOT")) {
                url = uri("https://repo.bx-team.space/snapshots/")
            } else {
                url = uri("https://repo.bx-team.space/releases/")
            }
            credentials(PasswordCredentials::class)
            authentication {
                create<BasicAuthentication>("basic")
            }
        }
    }
    publications {
        create<MavenPublication>("maven") {
            groupId = project.group.toString()
            artifactId = "nexus"
            version = project.version.toString()
            from(components["java"])
        }
    }
}
