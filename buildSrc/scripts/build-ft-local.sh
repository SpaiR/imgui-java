#!/bin/bash

BASEDIR=$(dirname "$0")
cd $BASEDIR/../../imgui-binding || exit

../gradlew clean generateLibs -Denvs=$* -Dlocal -Dfreetype
rm -frd libsNative
