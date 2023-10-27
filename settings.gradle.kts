
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
include("utbot-light")

