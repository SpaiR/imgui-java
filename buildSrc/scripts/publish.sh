#!/bin/bash

BASEDIR=$(dirname "$0")
cd $BASEDIR/../.. || exit

echo '> Publishing Modules...'
echo '>> Module [imgui-app]'
./gradlew imgui-app:publishImguiPublicationToRealRobotixMavenRepository
echo '>> Module [imgui-lwjgl3]'
./gradlew imgui-lwjgl3:publishImguiPublicationToRealRobotixMavenRepository
echo '>> Module [imgui-binding]'
./gradlew imgui-binding:publishImguiPublicationToRealRobotixMavenRepository

echo '> Publishing Natives...'
echo '>> Natives: [windows, freetype]'
./gradlew imgui-binding-natives:publishImguiPublicationToRealRobotixMavenRepository -PdeployType=windows -Pfreetype=true
echo '>> Natives: [windows]'
./gradlew imgui-binding-natives:publishImguiPublicationToRealRobotixMavenRepository -PdeployType=windows -Pfreetype=false
echo '>> Natives: [linux, freetype]'
./gradlew imgui-binding-natives:publishImguiPublicationToRealRobotixMavenRepository -PdeployType=linux -Pfreetype=true
echo '>> Natives: [linux]'
./gradlew imgui-binding-natives:publishImguiPublicationToRealRobotixMavenRepository -PdeployType=linux -Pfreetype=false
echo '>> Natives: [macOS, freetype]'
./gradlew imgui-binding-natives:publishImguiPublicationToRealRobotixMavenRepository -PdeployType=macos -Pfreetype=true
echo '>> Natives: [macOS]'
./gradlew imgui-binding-natives:publishImguiPublicationToRealRobotixMavenRepository -PdeployType=macos -Pfreetype=false
