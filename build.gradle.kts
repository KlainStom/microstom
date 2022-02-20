plugins {
    alias(libs.plugins.blossom)
    alias(libs.plugins.shadowJar)
    java
}

var displayName = "Microstom"

group = "com.github.klainstom"
version = "4.0.0"

dependencies {
    implementation(libs.minestom)

    compileOnly(libs.bundles.logging)
    compileOnly(libs.bundles.terminal)
}

tasks {
    blossom {
        replaceToken("&NAME", displayName.toUpperCase())
        replaceToken("&Name", displayName)
        replaceToken("&name", displayName.toLowerCase())
        replaceToken("&version", version)
        replaceToken("&minestomVersion", libs.versions.minestom.get())
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