val kotlinLoggingVersion: String by rootProject
val junit4Version: String by rootProject
val javasmtSolverZ3Version: String by rootProject
val rdVersion: String by rootProject

dependencies {
    implementation(project(":utbot-framework")) {
        exclude(group = "org.soot-oss:soot", module = "soot")
    }
    implementation(group= "org.sosy-lab", name= "javasmt-solver-z3", version= javasmtSolverZ3Version)
    implementation(group= "io.github.microutils", name= "kotlin-logging", version= kotlinLoggingVersion)
    implementation(group= "com.jetbrains.rd", name= "rd-framework", version= rdVersion)
    implementation(group= "com.jetbrains.rd", name= "rd-core", version= rdVersion)
}

apply {
    plugin("org.jetbrains.dokka")
    plugin("signing")
}

plugins {
    signing
    id("org.jetbrains.dokka") version "1.7.20"
}

tasks.register<Jar>("javadocJar") {
    archiveClassifier.set("javadoc")
    dependsOn(rootProject.tasks.findByName("dokkaHtmlMultimodule"))
    from("${rootProject.buildDir}/dokka/htmlMultiModule")
}

tasks.build {
    dependsOn(tasks.findByName("javadocJar"))
    dependsOn(tasks.kotlinSourcesJar)
}

tasks.kotlinSourcesJar {
    rootProject.childProjects.values.map { project -> project.sourceSets.main.get().allSource }.forEach { sourceDirectorySet ->
        from(
            sourceDirectorySet
        )
    }
}

tasks.jar {
    dependsOn.addAll(listOf("classes", sourceSets.main.get().compileClasspath))
    manifest {
        attributes(
            mapOf(
                "Implementation-Title" to project.name,
                "Implementation-Version" to project.version
            )
        )
    }

    val contents = sourceSets["main"].output + sourceSets.main.get().compileClasspath.map {
        if (it.isDirectory) it else zipTree(it)
    }

    from(contents)

    duplicatesStrategy = DuplicatesStrategy.EXCLUDE

    isZip64 = true
}

publishing {
    publications {
        register<MavenPublication>("jar") {
            from(components["java"])
            artifact(rootProject.tasks.named("kotlinSourcesJar"))
            artifact(tasks.named("javadocJar"))

            groupId = "org.utbot"
            artifactId = project.name
            addPom()
            signPublication(project)
        }
    }
    repositories {
        maven {
            name = "repo"
            url = uri("https://maven.pkg.github.com/UnitTestBot/USE")
            val actor: String? by project
            val token: String? by project

            credentials {
                username = System.getenv("GITHUB_ACTOR")?:actor
                password = System.getenv("GITHUB_TOKEN")?:token
            }
        }
    }
}

fun MavenPublication.addPom() {
    pom {
        packaging = "jar"
        name.set("org.utbot")
        description.set("Symbolic Execution Analysis")
        issueManagement {
            url.set("https://github.com/UnitTestBot/USE/issues")
        }
        scm {
            connection.set("scm:git:https://github.com/UnitTestBot/USE.git")
            developerConnection.set("scm:git:https://github.com/UnitTestBot/USE.git")
            url.set("https://www.utbot.org")
        }
        url.set("https://www.utbot.org")
        licenses {
            license {
                name.set("The Apache License, Version 2.0")
                url.set("https://www.apache.org/licenses/LICENSE-2.0.txt")
            }
        }
        developers {
            developer {
                id.set("UnitTestBot")
                name.set("UnitTestBot Team")
            }
        }
    }
}
fun MavenPublication.signPublication(project: Project) = with(project) {
    signing {
        val gpgKey: String? by project
        val gpgPassphrase: String? by project
        val gpgKeyValue = gpgKey?.removeSurrounding("\"")
        val gpgPasswordValue = gpgPassphrase

        if (gpgKeyValue != null && gpgPasswordValue != null) {
            useInMemoryPgpKeys(gpgKeyValue, gpgPasswordValue)
            sign(this@signPublication)
        }
    }
}