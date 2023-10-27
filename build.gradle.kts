import java.text.SimpleDateFormat
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val group = "org.utbot"

val kotlinVersion: String by project
val semVer: String? by project
val coroutinesVersion: String by project
val collectionsVersion: String by project
val junit5Version: String by project
val dateBasedVersion: String = SimpleDateFormat("YYYY.MM").format(System.currentTimeMillis()) // CI proceeds the same way
val kotlinLoggingVersion: String by project
val junit4Version: String by project
val javasmtSolverZ3Version: String by project
val rdVersion: String by project

version = semVer ?: "$dateBasedVersion"

plugins {
    `java-library`
    kotlin("jvm") version "1.8.0"
    `maven-publish`
    id("org.jetbrains.dokka") version "1.7.20"
    signing
}

allprojects {

    apply {
        plugin("maven-publish")
        plugin("kotlin")
        plugin("org.jetbrains.dokka")
        plugin("signing")
    }

    tasks {
        withType<JavaCompile> {
            sourceCompatibility = "1.8"
            targetCompatibility = "1.8"
            options.encoding = "UTF-8"
            options.compilerArgs = options.compilerArgs + "-Xlint:all"
        }
        withType<KotlinCompile> {
            kotlinOptions {
                jvmTarget = "1.8"
                freeCompilerArgs = freeCompilerArgs + listOf("-Xallow-result-return-type", "-Xsam-conversions=class", "-Xcontext-receivers")
                allWarningsAsErrors = false
            }
        }
        compileTestKotlin {
            kotlinOptions {
                jvmTarget = "1.8"
                freeCompilerArgs = freeCompilerArgs + listOf("-Xallow-result-return-type", "-Xsam-conversions=class", "-Xcontext-receivers")
                allWarningsAsErrors = false
            }
        }
        withType<Test> {
            // uncomment if you want to see loggers output in console
            // this is useful if you debug in docker
            // testLogging.showStandardStreams = true
            // testLogging.showStackTraces = true

            // set heap size for the test JVM(s)
            minHeapSize = "128m"
            maxHeapSize = "3072m"
            jvmArgs = listOf(
                "-XX:MaxHeapSize=3072m",
                "--add-opens", "java.base/java.util.concurrent.atomic=ALL-UNNAMED",
                "--add-opens", "java.base/java.lang.invoke=ALL-UNNAMED",
                "--add-opens", "java.base/java.util.concurrent=ALL-UNNAMED",
                "--add-opens", "java.base/java.util.concurrent.locks=ALL-UNNAMED",
                "--add-opens", "java.base/java.text=ALL-UNNAMED",
                "--add-opens", "java.base/java.io=ALL-UNNAMED",
                "--add-opens", "java.base/java.nio=ALL-UNNAMED",
                "--add-opens", "java.base/java.nio.file=ALL-UNNAMED",
                "--add-opens", "java.base/java.net=ALL-UNNAMED",
                "--add-opens", "java.base/sun.security.util=ALL-UNNAMED",
                "--add-opens", "java.base/sun.reflect.generics.repository=ALL-UNNAMED",
                "--add-opens", "java.base/sun.net.util=ALL-UNNAMED",
                "--add-opens", "java.base/sun.net.fs=ALL-UNNAMED",
                "--add-opens", "java.base/java.security=ALL-UNNAMED",
                "--add-opens", "java.base/java.lang.ref=ALL-UNNAMED",
                "--add-opens", "java.base/java.math=ALL-UNNAMED",
                "--add-opens", "java.base/java.util.stream=ALL-UNNAMED",
                "--add-opens", "java.base/java.util=ALL-UNNAMED",
                "--add-opens", "java.base/jdk.internal.misc=ALL-UNNAMED",
                "--add-opens", "java.base/java.lang=ALL-UNNAMED",
                "--add-opens", "java.base/java.lang.reflect=ALL-UNNAMED",
                "--add-opens", "java.base/sun.security.provider=ALL-UNNAMED",
                "--add-opens", "java.base/jdk.internal.event=ALL-UNNAMED",
                "--add-opens", "java.base/jdk.internal.jimage=ALL-UNNAMED",
                "--add-opens", "java.base/jdk.internal.jimage.decompressor=ALL-UNNAMED",
                "--add-opens", "java.base/jdk.internal.jmod=ALL-UNNAMED",
                "--add-opens", "java.base/jdk.internal.jtrfs=ALL-UNNAMED",
                "--add-opens", "java.base/jdk.internal.loader=ALL-UNNAMED",
                "--add-opens", "java.base/jdk.internal.logger=ALL-UNNAMED",
                "--add-opens", "java.base/jdk.internal.math=ALL-UNNAMED",
                "--add-opens", "java.base/jdk.internal.misc=ALL-UNNAMED",
                "--add-opens", "java.base/jdk.internal.module=ALL-UNNAMED",
                "--add-opens", "java.base/jdk.internal.org.objectweb.asm.commons=ALL-UNNAMED",
                "--add-opens", "java.base/jdk.internal.org.objectweb.asm.signature=ALL-UNNAMED",
                "--add-opens", "java.base/jdk.internal.org.objectweb.asm.tree=ALL-UNNAMED",
                "--add-opens", "java.base/jdk.internal.org.objectweb.asm.tree.analysis=ALL-UNNAMED",
                "--add-opens", "java.base/jdk.internal.org.objectweb.asm.util=ALL-UNNAMED",
                "--add-opens", "java.base/jdk.internal.org.xml.sax=ALL-UNNAMED",
                "--add-opens", "java.base/jdk.internal.org.xml.sax.helpers=ALL-UNNAMED",
                "--add-opens", "java.base/jdk.internal.perf=ALL-UNNAMED",
                "--add-opens", "java.base/jdk.internal.platform=ALL-UNNAMED",
                "--add-opens", "java.base/jdk.internal.ref=ALL-UNNAMED",
                "--add-opens", "java.base/jdk.internal.reflect=ALL-UNNAMED",
                "--add-opens", "java.base/jdk.internal.util=ALL-UNNAMED",
                "--add-opens", "java.base/jdk.internal.util.jar=ALL-UNNAMED",
                "--add-opens", "java.base/jdk.internal.util.xml=ALL-UNNAMED",
                "--add-opens", "java.base/jdk.internal.util.xml.impl=ALL-UNNAMED",
                "--add-opens", "java.base/jdk.internal.vm=ALL-UNNAMED",
                "--add-opens", "java.base/jdk.internal.vm.annotation=ALL-UNNAMED"
            )

            useJUnitPlatform {
                excludeTags = setOf("slow", "IntegrationTest")
            }

            addTestListener(object : TestListener {
                override fun beforeSuite(suite: TestDescriptor) {}
                override fun beforeTest(testDescriptor: TestDescriptor) {}
                override fun afterTest(testDescriptor: TestDescriptor, result: TestResult) {
                    println("[$testDescriptor.classDisplayName] [$testDescriptor.displayName]: $result.resultType, length - ${(result.endTime - result.startTime) / 1000.0} sec")
                    if (result.resultType == TestResult.ResultType.FAILURE) {
                        println("Exception: " + result.exception?.stackTraceToString())
                    }
                }

                override fun afterSuite(testDescriptor: TestDescriptor, result: TestResult) {
                    if (testDescriptor.parent == null) { // will match the outermost suite
                        println("Test summary: ${result.resultType} (${result.testCount} tests, ${result.successfulTestCount} successes, ${result.failedTestCount} failures, ${result.skippedTestCount} skipped)")
                    }
                }
            })
        }
    }

    repositories {
        mavenCentral()
        maven("https://s01.oss.sonatype.org/content/repositories/orgunittestbotsoot-1004/")
        maven("https://plugins.gradle.org/m2")
        maven("https://www.jetbrains.com/intellij-repository/releases")
        maven("https://cache-redirector.jetbrains.com/maven-central")
    }

    dependencies {
        implementation(group = "org.jetbrains.kotlinx", name = "kotlinx-coroutines-core", version = coroutinesVersion)
        implementation(
            group = "org.jetbrains.kotlinx",
            name = "kotlinx-collections-immutable-jvm",
            version = collectionsVersion
        )
        implementation(group = "org.jetbrains.kotlin", name = "kotlin-stdlib-jdk8", version = kotlinVersion)
        implementation(group = "org.jetbrains.kotlin", name = "kotlin-reflect", version = kotlinVersion)
        runtimeOnly(group = "org.jetbrains.dokka", name = "dokka-gradle-plugin", version = "1.7.20")

        testImplementation("org.junit.jupiter:junit-jupiter") {
            version {
                strictly(junit5Version)
            }
        }
    }
}


tasks.dokkaHtmlMultiModule {
    removeChildTasks(
        listOf(project(":utbot-rd"))
    )
}

val javadocJar = tasks.register<Jar>("javadocJar") {
    archiveClassifier.set("javadoc")
    dependsOn(tasks.dokkaHtmlMultimodule)
    from("$buildDir/dokka/htmlMultiModule")
}

tasks.kotlinSourcesJar {
    rootProject.childProjects.values.map { project -> project.sourceSets.main.get().allSource }.forEach { sourceDirectorySet ->
        from(
            sourceDirectorySet
        )
    }
}

tasks.build {
    dependsOn(javadocJar)
    dependsOn(tasks.kotlinSourcesJar)
}

dependencies {

    implementation(project(":utbot-framework")) {
        exclude(group = "org.soot-oss:soot", module = "soot")
    }

    implementation(group= "org.sosy-lab", name= "javasmt-solver-z3", version= javasmtSolverZ3Version)
    implementation(group= "io.github.microutils", name= "kotlin-logging", version= kotlinLoggingVersion)
    implementation(group= "com.jetbrains.rd", name= "rd-framework", version= rdVersion)
    implementation(group= "com.jetbrains.rd", name= "rd-core", version= rdVersion)

    runtimeOnly(group = "org.jetbrains.kotlin", name = "kotlin-gradle-plugin", version = kotlinVersion)
    runtimeOnly(group = "org.jetbrains.kotlin", name = "kotlin-allopen", version = kotlinVersion)
}

fun MavenPublication.addPom() {
    pom {
        packaging = "jar"
        name.set(group)
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
    repositories {
        maven {
            name = "USE"
            url = uri("/repository/USE")
            credentials {
                username = ""
                password = ""
            }

            isAllowInsecureProtocol = true
        }
    }

    publications {
        create<MavenPublication>("jar") {
            from(components["kotlin"])
            groupId = group
            artifactId = project.name
            artifact(javadocJar)
            artifact(tasks.kotlinSourcesJar)
            addPom()
            signPublication(project)
        }
    }
}
