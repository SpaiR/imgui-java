#!/bin/bash

# Set base directory and navigate to project root
echo "Setting base directory and navigating to project root..."
BASEDIR=$(dirname "$0")
cd "$BASEDIR"/../.. || exit 1
echo "Navigated to $(pwd)"

echo '> Publishing Modules...'

publish_module() {
    local module=$1
    echo ">> Publishing Module [$module]"
    ./gradlew $module:publishImguiPublicationToMavenCentralRepository
    if [ $? -ne 0 ]; then
        echo "Failed to publish $module module"
        exit 1
    fi
    echo ">> Module [$module] published successfully"
}

# Publish each module
publish_module "imgui-app"
publish_module "imgui-lwjgl3"
publish_module "imgui-binding"

echo '> Publishing Natives...'

publish_natives() {
    local platform=$1
    echo ">> Publishing Natives: [$platform]"
    ./gradlew imgui-binding-natives:publishImguiPublicationToMavenCentralRepository -PdeployType=$platform
    if [ $? -ne 0 ]; then
        echo "Failed to publish natives for $platform"
        exit 1
    fi
    echo ">> Natives for $platform published successfully"
}

# Publish natives for each platform
publish_natives "windows"
publish_natives "linux"
publish_natives "macos"

echo "> Nexus manual upload..."
curl -D - -X POST -u "${NEXUS_UPD_ID}:${NEXUS_UPD_PASS}" "https://ossrh-staging-api.central.sonatype.com/manual/upload/defaultRepository/io.github.spair"

echo "All modules and natives published successfully."
