buildscript {
    repositories {
        maven("https://repo.spongepowered.org/maven")
    }
}

plugins {
    id("com.github.johnrengelman.shadow") version "7.0.0"
    id("net.kyori.blossom") version "1.3.0"
    java
}

var displayName = "Microstom"
var minestomVersion = "be100fa5b8a410258e0da2aa4341cc341a0359a6"

group = "com.github.klainstom"
version = "3.1-SNAPSHOT"

repositories {
    mavenCentral()
    maven("https://repo.spongepowered.org/maven")
    maven("https://jitpack.io")
}

dependencies {
    implementation("com.github.Minestom:Minestom:$minestomVersion")

    implementation("org.tinylog:tinylog-api:2.4.1")
    implementation("org.tinylog:tinylog-impl:2.4.1")

    implementation("org.jline:jline:3.21.0")
}

tasks {
    blossom {
        replaceToken("&Name", displayName)
        replaceToken("&name", displayName.toLowerCase())
        replaceToken("&version", version)
        replaceToken("&minestomVersion", minestomVersion)
    }

    processResources {
        expand(
            mapOf(
                "Name" to displayName,
                "version" to version
            )
        )
    }

    shadowJar {
        manifest {
            attributes("Main-Class" to "com.github.klainstom.microstom.Server")
        }
        archiveBaseName.set(displayName)
        archiveClassifier.set("")
        archiveVersion.set(project.version.toString())
        mergeServiceFiles()
    }

    test {
        useJUnitPlatform()
    }

    build {
        dependsOn(shadowJar)
    }
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}