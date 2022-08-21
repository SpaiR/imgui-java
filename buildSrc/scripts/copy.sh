#!/bin/bash

BASEDIR=$(dirname "$0")
cd $BASEDIR/../.. || exit

[ -e "/tmp/imgui/libsNative/windows32/imgui-java.dll" ] && cp /tmp/imgui/libsNative/windows32/imgui-java.dll ./bin
[ -e "/tmp/imgui/libsNative/windows64/imgui-java64.dll" ] && cp /tmp/imgui/libsNative/windows64/imgui-java64.dll ./bin
[ -e "/tmp/imgui/libsNative/linux32/libimgui-java.so" ] && cp /tmp/imgui/libsNative/linux32/libimgui-java.so ./bin
[ -e "/tmp/imgui/libsNative/linux64/libimgui-java64.so" ] && cp /tmp/imgui/libsNative/linux64/libimgui-java64.so ./bin
[ -e "/tmp/imgui/libsNative/macosx64/libimgui-java64.dylib" ] && cp /tmp/imgui/libsNative/macosx64/libimgui-java64.dylib ./bin
[ -e "/tmp/imgui/libsNative/macosxarm64/libimgui-javaarm64.dylib" ] && cp /tmp/imgui/libsNative/macosxarm64/libimgui-javaarm64.dylib ./bin
