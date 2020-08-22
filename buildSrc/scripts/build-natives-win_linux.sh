#!/bin/bash

BASEDIR=$(dirname "$0")
cd $BASEDIR/../../imgui-binding || exit

rm -frd /tmp/imgui
../gradlew clean generateLibs -Denvs=win32,win64,linux32,linux64
