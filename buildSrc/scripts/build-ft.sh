#!/bin/bash

BASEDIR=$(dirname "$0")
cd $BASEDIR/../../modules/io.github.spair.imgui || exit

rm -frd /tmp/imgui
../gradlew clean generateLibs -Denvs=$* -Dfreetype=true
