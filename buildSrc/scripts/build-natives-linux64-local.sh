#!/bin/bash

BASEDIR=$(dirname "$0")
cd $BASEDIR/../../imgui-binding || exit

../gradlew clean generateLibs -Denvs=linux64 -Dlocal
rm -frd libsNative
