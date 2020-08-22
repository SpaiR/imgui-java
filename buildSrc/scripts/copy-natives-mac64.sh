#!/bin/bash

BASEDIR=$(dirname "$0")
cd $BASEDIR/../.. || exit

cp /tmp/imgui/libsNative/macosx64/libimgui-java64.dylib ./bin
