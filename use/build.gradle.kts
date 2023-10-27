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