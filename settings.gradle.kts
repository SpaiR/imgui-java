plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version("1.0.0")
}

rootProject.name = "imgui-java"
include("imgui-binding")
include("imgui-lwjgl3")
include("imgui-binding-natives")
include("imgui-app")
include("example")
