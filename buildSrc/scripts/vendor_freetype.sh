#!/bin/bash

# Set base directory and navigate to project root
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

echo "Making FreeType for '$VTYPE'"

# Define library directory and version
LIBDIR=build/vendor/freetype
VERSION=2.12.1

# Clean and create library directory, then extract FreeType source
echo "Cleaning and creating library directory, then extracting FreeType source..."
rm -rf $LIBDIR
mkdir -p $LIBDIR
tar -xzf ./vendor/freetype-$VERSION.tar.gz -C $LIBDIR --strip-components=1
cd $LIBDIR || exit 1
echo "FreeType unzipped to $LIBDIR"

# Common configuration flags
COMMON_FLAGS="--enable-static --disable-shared --without-zlib --without-bzip2 --without-png --without-harfbuzz --without-brotli"

# Function to configure and build FreeType
build_freetype() {
    cflags=$1
    prefix=$2
    output_dir=$3

    echo "Cleaning previous builds..."
    make clean
    echo "Configuring FreeType with CFLAGS='$cflags' and PREFIX='$prefix'..."
    ./configure CFLAGS="$cflags" $COMMON_FLAGS $prefix
    echo "Building FreeType..."
    make

    echo "Checking if the generated library exists..."
    if [ ! -f objs/.libs/libfreetype.a ]; then
        echo "File objs/.libs/libfreetype.a not found!"
        exit 1
    fi

    echo "Copying the generated library to $output_dir..."
    cp objs/.libs/libfreetype.a "$output_dir"
    echo "Library copied to $output_dir"
}

# Ensure necessary directories exist
echo "Ensuring necessary directories exist..."
mkdir -p lib tmp

# Determine build process based on vendor type
case "$VTYPE" in
    windows)
        build_freetype "-arch x86_64" "--host=x86_64-w64-mingw32 --prefix=/usr/x86_64-w64-mingw32" "lib/libfreetype.a"
        ;;
    linux)
        build_freetype "-arch x86_64" "" "lib/libfreetype.a"
        ;;
    macos)
        MACOS_VERSION=10.15

        build_freetype "-arch x86_64 -mmacosx-version-min=$MACOS_VERSION" "" "tmp/libfreetype-x86_64.a"
        build_freetype "-arch arm64 -mmacosx-version-min=$MACOS_VERSION" "" "tmp/libfreetype-arm64.a"

        echo "Creating universal library using lipo..."
        lipo -create -output lib/libfreetype.a tmp/libfreetype-x86_64.a tmp/libfreetype-arm64.a
        echo "Universal library created at lib/libfreetype.a"
        ;;
    *)
        echo "Unknown vendor type: $VTYPE"
        exit 1
        ;;
esac

echo "Script completed successfully."
