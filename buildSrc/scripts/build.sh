#!/bin/bash

echo "Setting base directory and navigating to project root..."
BASEDIR=$(dirname "$0")
cd "$BASEDIR"/../.. || exit 1
echo "Navigated to $(pwd)"

# Check if vendor type argument is provided
if [ -z "$1" ]; then
    echo "Vendor type is required"
    exit 1
fi

VTYPE=$1
echo "Vendor type set to '$VTYPE'"

# Make the vendor FreeType script executable and run it
echo "Making vendor FreeType script executable and running it..."
chmod +x buildSrc/scripts/vendor_freetype.sh
buildSrc/scripts/vendor_freetype.sh "$VTYPE"
if [ $? -ne 0 ]; then
    echo "Vendor FreeType script failed"
    exit 1
fi
echo "Vendor FreeType script completed successfully"

# Create the destination directory for imgui libraries
echo "Creating destination directory for imgui libraries..."
mkdir -p /tmp/imgui/dst
echo "Directory /tmp/imgui/dst created successfully"

# Function to check if a file exists
check_file_exists() {
    if [ ! -f "$1" ]; then
        echo "File $1 not found!"
        exit 1
    fi
}

# Determine build process based on vendor type
case "$VTYPE" in
    windows)
        echo "Running Gradle task for Windows..."
        ./gradlew imgui-binding:generateLibs -Denvs=windows -Dfreetype=true
        if [ $? -ne 0 ]; then
            echo "Gradle task for Windows failed"
            exit 1
        fi

        echo "Checking if the generated DLL exists..."
        check_file_exists /tmp/imgui/libsNative/windows64/imgui-java64.dll

        echo "Copying the generated DLL to the destination directory..."
        cp /tmp/imgui/libsNative/windows64/imgui-java64.dll /tmp/imgui/dst/imgui-java64.dll
        if [ $? -ne 0 ]; then
            echo "Failed to copy DLL to /tmp/imgui/dst/imgui-java64.dll"
            exit 1
        fi
        echo "DLL copied to /tmp/imgui/dst/imgui-java64.dll successfully"
        ;;
    linux)
        echo "Running Gradle task for Linux..."
        ./gradlew imgui-binding:generateLibs -Denvs=linux -Dfreetype=true
        if [ $? -ne 0 ]; then
            echo "Gradle task for Linux failed"
            exit 1
        fi

        echo "Checking if the generated SO file exists..."
        check_file_exists /tmp/imgui/libsNative/linux64/libimgui-java64.so

        echo "Copying the generated SO file to the destination directory..."
        cp /tmp/imgui/libsNative/linux64/libimgui-java64.so /tmp/imgui/dst/libimgui-java64.so
        if [ $? -ne 0 ]; then
            echo "Failed to copy SO file to /tmp/imgui/dst/libimgui-java64.so"
            exit 1
        fi
        echo "SO file copied to /tmp/imgui/dst/libimgui-java64.so successfully"
        ;;
    macos)
        echo "Running Gradle task for macOS and macOS ARM..."
        ./gradlew imgui-binding:generateLibs -Denvs=macos,macosarm64 -Dfreetype=true
        if [ $? -ne 0 ]; then
            echo "Gradle task for macOS failed"
            exit 1
        fi

        echo "Checking if the generated DYLIB files exist..."
        check_file_exists /tmp/imgui/libsNative/macosx64/libimgui-java64.dylib
        check_file_exists /tmp/imgui/libsNative/macosxarm64/libimgui-java64.dylib

        echo "Creating a universal library using lipo..."
        lipo -create -output /tmp/imgui/dst/libimgui-java64.dylib /tmp/imgui/libsNative/macosx64/libimgui-java64.dylib /tmp/imgui/libsNative/macosxarm64/libimgui-java64.dylib
        if [ $? -ne 0 ]; then
            echo "Failed to create universal library with lipo"
            exit 1
        fi
        echo "Universal library created at /tmp/imgui/dst/libimgui-java64.dylib successfully"
        ;;
    *)
        echo "Unknown vendor type: $VTYPE"
        exit 1
        ;;
esac

echo "Script completed successfully."
