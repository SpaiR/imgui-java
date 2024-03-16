#!/bin/bash

BASEDIR=$(dirname "$0")
cd $BASEDIR/../.. || exit

echo '> Publishing Modules...'
echo '>> Module [imgui-app]'
./gradlew imgui-app:publishImguiPublicationToMavenCentralRepository
echo '>> Module [imgui-lwjgl3]'
./gradlew imgui-lwjgl3:publishImguiPublicationToMavenCentralRepository
echo '>> Module [imgui-binding]'
./gradlew imgui-binding:publishImguiPublicationToMavenCentralRepository

echo '> Publishing Natives...'
echo '>> Natives: [windows, freetype]'
./gradlew imgui-binding-natives:publishImguiPublicationToMavenCentralRepository -PdeployType=windows -Pfreetype=true
echo '>> Natives: [windows]'
./gradlew imgui-binding-natives:publishImguiPublicationToMavenCentralRepository -PdeployType=windows -Pfreetype=false
echo '>> Natives: [linux, freetype]'
./gradlew imgui-binding-natives:publishImguiPublicationToMavenCentralRepository -PdeployType=linux -Pfreetype=true
echo '>> Natives: [linux]'
./gradlew imgui-binding-natives:publishImguiPublicationToMavenCentralRepository -PdeployType=linux -Pfreetype=false
echo '>> Natives: [macOS, freetype]'
./gradlew imgui-binding-natives:publishImguiPublicationToMavenCentralRepository -PdeployType=macos -Pfreetype=true
echo '>> Natives: [macOS]'
./gradlew imgui-binding-natives:publishImguiPublicationToMavenCentralRepository -PdeployType=macos -Pfreetype=false
echo '>> Natives: [macOS-arm64, freetype]'
./gradlew imgui-binding-natives:publishImguiPublicationToMavenCentralRepository -PdeployType=macosarm64 -Pfreetype=true
echo '>> Natives: [macOS-arm64]'
./gradlew imgui-binding-natives:publishImguiPublicationToMavenCentralRepository -PdeployType=macosarm64 -Pfreetype=false
