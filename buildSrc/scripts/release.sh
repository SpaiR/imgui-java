#!/bin/bash

BASEDIR=$(dirname "$0")
cd $BASEDIR/../.. || exit

echo [Publishing Modules...]
./gradlew :imgui-app:publishMavenJavaPublicationToMavenCentralRepository
./gradlew :imgui-lwjgl3:publishMavenJavaPublicationToMavenCentralRepository
./gradlew :imgui-binding:publishMavenJavaPublicationToMavenCentralRepository

echo [Publishing Native...]
./gradlew :imgui-binding-natives:publishMavenJavaPublicationToMavenCentralRepository -PdeployType=win -Pfreetype=true
./gradlew :imgui-binding-natives:publishMavenJavaPublicationToMavenCentralRepository -PdeployType=win -Pfreetype=false
./gradlew :imgui-binding-natives:publishMavenJavaPublicationToMavenCentralRepository -PdeployType=linux -Pfreetype=true
./gradlew :imgui-binding-natives:publishMavenJavaPublicationToMavenCentralRepository -PdeployType=linux -Pfreetype=false
./gradlew :imgui-binding-natives:publishMavenJavaPublicationToMavenCentralRepository -PdeployType=mac -Pfreetype=true
./gradlew :imgui-binding-natives:publishMavenJavaPublicationToMavenCentralRepository -PdeployType=mac -Pfreetype=false
