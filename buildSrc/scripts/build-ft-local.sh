#!/bin/bash

BASEDIR=$(dirname "$0")
cd $BASEDIR/../../modules/io.github.spair.imgui || exit

../gradlew clean generateLibs -Denvs=$* -Dlocal -Dfreetype=true
rm -frd libsNative
