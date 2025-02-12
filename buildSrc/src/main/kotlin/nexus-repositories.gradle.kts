import org.gradle.kotlin.dsl.maven
import org.gradle.kotlin.dsl.repositories

plugins {
    `java-library`
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
    maven("https://repo.bxteam.org/releases")
}
