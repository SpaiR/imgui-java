#!/usr/bin/env sh

# Separate shell script required to ensure that clang++ command is runnable.
# Java ProcessBuilder doesn't see command properly.
# Also, running like that helps to ignore local clang++ warnings which can happen.

# Assign the first argument to 'input_file' (the C++ file to analyze).
input_file="$1"

# Assign the second argument to 'output_file' (where to save the AST JSON).
output_file="$2"

# 'shift 2' shifts the positional parameters to the left by two positions.
# This means "$@" will now contain any additional arguments (like macro definitions).
shift 2

# Run clang++ with the specified options.
# -Xclang -ast-dump=json: Dumps the AST in JSON format.
# -fsyntax-only: Only checks the syntax, does not compile.
# -fparse-all-comments: Includes comments in the AST.
# -fmerge-all-constants: Merges identical constants.
# "$@" passes any additional arguments (like -D for macro definitions).
# "$input_file" is the C++ source file to process.
# The output is redirected and appended to the specified output file.
clang++ -Xclang -ast-dump=json -fsyntax-only -fparse-all-comments -fmerge-all-constants "$@" "$input_file" >> "$output_file"
