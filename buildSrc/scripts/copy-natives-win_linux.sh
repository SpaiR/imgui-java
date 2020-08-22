#!/bin/bash

BASEDIR=$(dirname "$0")
cd $BASEDIR/../.. || exit

cp /tmp/imgui/libsNative/windows32/imgui-java.dll ./bin
cp /tmp/imgui/libsNative/windows64/imgui-java64.dll ./bin
cp /tmp/imgui/libsNative/linux32/libimgui-java.so ./bin
cp /tmp/imgui/libsNative/linux64/libimgui-java64.so ./bin
