#!/bin/bash
# Apply patches to vendored submodules.
# Idempotent: each patch is applied only if not already applied.

set -e

BASEDIR=$(dirname "$0")
cd "$BASEDIR"/../.. || exit 1

apply_patch_idempotent() {
    local patch_file="$1"
    local target_dir="$2"

    # --check returns 0 if the patch can apply cleanly (i.e. not yet applied),
    # non-zero if it can't (already applied, or conflicts).
    if git -C "$target_dir" apply --check "$patch_file" 2>/dev/null; then
        echo "Applying $patch_file to $target_dir"
        git -C "$target_dir" apply "$patch_file"
    else
        # Try reverse-check: if the patch is already applied, reversing would succeed.
        if git -C "$target_dir" apply --reverse --check "$patch_file" 2>/dev/null; then
            echo "Patch $patch_file already applied to $target_dir, skipping"
        else
            echo "ERROR: Patch $patch_file neither applies cleanly nor is already applied to $target_dir" >&2
            exit 1
        fi
    fi
}

apply_patch_idempotent \
    "$(pwd)/patches/imgui-node-editor-imgui-1.92-operator-star.patch" \
    include/imgui-node-editor
