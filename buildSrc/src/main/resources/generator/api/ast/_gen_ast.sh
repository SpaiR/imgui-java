#!/usr/bin/env sh

# Separate shell script required to ensure that clang++ command is runnable.
# Java ProcessBuilder doesn't see command properly.
# Also, running like that helps to ignore local clang++ warnings which can happen.

clang++ -Xclang -ast-dump=json -fsyntax-only -fparse-all-comments -fmerge-all-constants "$1" >> "$2"
