val projectType: String by settings
val ultimateEdition: String by settings
val springEdition: String by settings
val languagesEdition: String by settings
val pureJavaEdition: String by settings

val ideType: String by settings
val buildType: String by settings

val pythonIde: String by settings
val jsIde: String by settings
val jsBuild: String by settings
val goIde: String by settings

pluginManagement {
    resolutionStrategy {
        eachPlugin {
            if (requested.id.name == "rdgen") {
                useModule("com.jetbrains.rd:rd-gen:${requested.version}")
            }
        }
    }
}

rootProject.name = "utbot"

include("utbot-core")
include("utbot-framework")
include("utbot-framework-api")
include("utbot-api")
include("utbot-rd")
include("use")

