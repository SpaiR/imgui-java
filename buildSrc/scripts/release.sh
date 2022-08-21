#!/bin/bash

BASEDIR=$(dirname "$0")
cd $BASEDIR/../.. || exit

echo '> Publishing Modules...'
echo '>> Module [imgui-app]'
./gradlew :imgui-app:publishMavenJavaPublicationToMavenCentralRepository
echo '>> Module [imgui-lwjgl3]'
./gradlew :imgui-lwjgl3:publishMavenJavaPublicationToMavenCentralRepository
echo '>> Module [imgui-binding]'
./gradlew :imgui-binding:publishMavenJavaPublicationToMavenCentralRepository

echo '> Publishing Natives...'
echo '>> Natives: [win & freetype]'
./gradlew :imgui-binding-natives:publishMavenJavaPublicationToMavenCentralRepository -PdeployType=win -Pfreetype=true
echo '>> Natives: [win & no freetype]'
./gradlew :imgui-binding-natives:publishMavenJavaPublicationToMavenCentralRepository -PdeployType=win -Pfreetype=false
echo '>> Natives: [linux & freetype]'
./gradlew :imgui-binding-natives:publishMavenJavaPublicationToMavenCentralRepository -PdeployType=linux -Pfreetype=true
echo '>> Natives: [linux & no freetype]'
./gradlew :imgui-binding-natives:publishMavenJavaPublicationToMavenCentralRepository -PdeployType=linux -Pfreetype=false
echo '>> Natives: [mac & freetype]'
./gradlew :imgui-binding-natives:publishMavenJavaPublicationToMavenCentralRepository -PdeployType=mac -Pfreetype=true
echo '>> Natives: [mac & no freetype]'
./gradlew :imgui-binding-natives:publishMavenJavaPublicationToMavenCentralRepository -PdeployType=mac -Pfreetype=false
echo '>> Natives: [macarm & freetype]'
./gradlew :imgui-binding-natives:publishMavenJavaPublicationToMavenCentralRepository -PdeployType=macarm -Pfreetype=true
echo '>> Natives: [macarm & no freetype]'
./gradlew :imgui-binding-natives:publishMavenJavaPublicationToMavenCentralRepository -PdeployType=macarm -Pfreetype=false
