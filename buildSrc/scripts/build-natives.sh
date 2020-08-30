#!/bin/bash

BASEDIR=$(dirname "$0")
cd $BASEDIR/../../imgui-binding || exit

rm -frd /tmp/imgui
../gradlew clean generateLibs -Denvs=$*
