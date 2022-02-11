#!/bin/bash

BASEDIR=$(dirname "$0")
cd $BASEDIR/../.. || exit

mkdir ./bin/freetype

[ -e "/tmp/imgui/libsNative/windows32/imgui-java.dll" ] && cp /tmp/imgui/libsNative/windows32/imgui-java.dll ./bin/freetype
[ -e "/tmp/imgui/libsNative/windows64/imgui-java64.dll" ] && cp /tmp/imgui/libsNative/windows64/imgui-java64.dll ./bin/freetype
[ -e "/tmp/imgui/libsNative/linux32/libimgui-java.so" ] && cp /tmp/imgui/libsNative/linux32/libimgui-java.so ./bin/freetype
[ -e "/tmp/imgui/libsNative/linux64/libimgui-java64.so" ] && cp /tmp/imgui/libsNative/linux64/libimgui-java64.so ./bin/freetype
[ -e "/tmp/imgui/libsNative/macosx64/libimgui-java64.dylib" ] && cp /tmp/imgui/libsNative/macosx64/libimgui-java64.dylib ./bin/freetype
[ -e "/tmp/imgui/libsNative/macosxarm64/libimgui-javaarm64.dylib" ] && cp /tmp/imgui/libsNative/macosxarm64/libimgui-javaarm64.dylib ./bin/freetype
