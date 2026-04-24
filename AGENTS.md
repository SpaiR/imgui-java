# AGENTS.md

Guidance for AI coding agents working on `imgui-java`.

## What this repo is

JNI-based Java binding for [Dear ImGui](https://github.com/ocornut/imgui) + extensions (ImPlot, ImNodes, ImGuizmo, imgui-node-editor, imgui-knobs, imgui-file-dialog, imgui-text-edit, imgui-club). Multi-module Gradle build, published as `io.github.spair:imgui-java-{binding,lwjgl3,app}`. Java is **codegen-driven**: annotated stubs in `imgui-binding/src/main/java/`, expanded by the Spoon-based generator in `buildSrc/` into `imgui-binding/src/generated/java/` — both trees committed.

## Golden rule: never edit generated code directly

`imgui-binding/src/generated/java/` is codegen output. `imgui-binding/src/main/java/` is the **single source of truth**. Workflow is always: edit source → `./gradlew :imgui-binding:generateApi` → commit both trees in one commit.

If the generator produces something you "just need to fix in the generated file" — that's a generator bug. Fix it in `buildSrc/`. Hand-edits to `src/generated/` are silently reverted on the next regen.

## Project layout

| Path | What's there |
|---|---|
| `imgui-binding/` | Core JNI binding + codegen |
| `imgui-binding/src/main/java/` | Hand-written annotated sources (`@BindingSource`, `@BindingField`, `@BindingMethod`, `@BindingAstEnum`) |
| `imgui-binding/src/generated/java/` | Codegen output; committed. See Golden rule |
| `imgui-binding/src/main/native/` | Hand-written JNI `.cpp` / `.h` (struct marshaling etc.) |
| `imgui-lwjgl3/` | LWJGL backend (GLFW + OpenGL) |
| `imgui-app/` | Convenience wrapper bundling natives + an `Application` entry-point |
| `include/` | Git submodules: imgui, implot, imnodes, imguizmo, etc. |
| `bin/` | Native libs (`.so/.dylib/.dll`), committed by CI |
| `buildSrc/` | Gradle plugin: codegen tasks, JNI build, AST parser |
| `buildSrc/src/main/resources/generator/api/ast/*.json` | Clang-dumped AST data, consumed by `@BindingAstEnum` |
| `example/` | Example apps |
| `patches/` | Local patches against vendored submodules |

## Build & test

Gradle's toolchain pins JDK 17 regardless of the system JDK.

```bash
./gradlew :imgui-binding:compileJava        # fast Java-side check
./gradlew :imgui-binding:javadoc            # catch doc errors (CI also runs this; mandatory pass)
./gradlew :imgui-binding:generateApi        # regen src/generated/java from annotated sources
./gradlew generateAst                       # regen clang AST JSONs (needs clang++ installed)
./gradlew :imgui-binding:generateLibs       # build native JNI libs (per-OS; CI handles cross-platform)
./gradlew build                             # full build + tests
```

**Visual smoke test** — backend, font, texture, or example changes need a runtime run; static checks can't catch blank atlases or assertion loops:

```bash
buildSrc/scripts/build.sh <macos|linux|windows>   # builds freetype + generateLibs
cp /tmp/imgui/dst/libimgui-java64.<so|dylib|dll> bin/
./gradlew :example:run -PlibPath=$PWD/bin
```

**Fast javadoc iteration without Gradle:**

```bash
find imgui-binding/src/generated/java imgui-binding/src/main/java -name '*.java' > /tmp/files.txt
javadoc -d /tmp/jdoc -Xdoclint:all \
    -sourcepath imgui-binding/src/generated/java:imgui-binding/src/main/java \
    @/tmp/files.txt 2>&1 | grep 'error:'
```

Zero `error:` lines is the bar. Warnings (mostly missing `@param`/`@return` on regenerated methods) are the baseline and non-blocking.

## Upgrading Dear ImGui or an extension

### 0. Orient yourself

Read the upstream changelog and diff before touching anything.

```bash
cd include/imgui                        # or implot, imnodes, etc.
git fetch --tags origin
less CHANGELOG.txt                      # imgui has this; others vary (docs/CHANGELOG.md, release notes)
git log --oneline <current>..<target>   # commit-level overview
git diff <current>..<target> -- '*.h'   # header-level diff — what actually changes the C API
```

Identify:

- **Breaking changes** — removed/renamed functions, changed signatures, enum renames, struct field changes. Grep hand-written sources for any `@BindingMethod`/`@BindingField` that hits these.
- **Obsoleted APIs** — usually guarded by `IMGUI_DISABLE_OBSOLETE_FUNCTIONS`. Decide per-case whether the binding keeps the legacy surface (flagged as obsolete in docs) or drops it; mirror upstream's stance.
- **New features worth exposing** — new functions, flags, struct fields useful from Java. Prefer landing the bump first, then adding new surface in follow-up commits.
- **AST implications** — new types/renames surface in regenerated `ast-*.json`. Unexpected AST changes outside your target submodule = drift (revert per Gotchas).

Use this reading to write a meaningful commit message and PR description. A one-line "bump imgui" is never enough.

### 1. Mechanical flow

1. Bump the submodule pointer.
   ```bash
   git -C include/imgui checkout v1.92.7
   ```
2. Regenerate the clang AST.
   ```bash
   ./gradlew generateAst
   ```
   Commit the resulting `buildSrc/src/main/resources/generator/api/ast/ast-<name>.json`. If *other* AST JSONs change (e.g., `ast-ImGuiFileDialog.json`, `ast-TextEditor.json`), that's unrelated local clang/system-header drift — **revert those**, keep the diff scoped.
3. Update annotated sources under `imgui-binding/src/main/java/` to match upstream's new/renamed/removed fields and methods exactly. Doc comments copy through verbatim — pre-sanitize anything that'd trip doclint (see Gotchas).
4. Regenerate Java.
   ```bash
   ./gradlew :imgui-binding:generateApi
   ```
   Commit `src/generated/java/` changes alongside the source changes.
5. **Run javadoc locally** (see above). Any errors → fix in source, regen, recheck.
6. `./gradlew generateLibs` rebuilds the native lib for the current OS. CI builds the cross-platform set on merge and commits to `bin/`.

## Gotchas (what bites on a submodule bump)

### Javadoc doclint rejects C++ comment patterns

Dear ImGui's C++ headers use `<`, `>`, `&`, `->` as operators in doc comments; strict doclint (JDK 17+) treats them as malformed HTML and fails. Wrap offending text in `{@code ...}` in source, then regen. Examples:

| Bad | Good |
|---|---|
| `(flags & X)` | `{@code (flags & X)}` |
| `if threshold < 0.0f` | `{@code if threshold < 0.0f}` |
| `"Demo->Child->X"` | `{@code Demo->Child->X}` |
| `{@link ImGui#pushFont(ImFont)}` after rename | `{@link ImGui#pushFont(ImFont, float)}` |

Only enum-constant docs are auto-sanitized (via `ast_content.kt`'s `sanitizeDocComment`); class- and method-level Javadoc is copied verbatim. Extending the sanitizer to cover those too would eliminate this step — open opportunity. Run javadoc locally before pushing.

### Vendor patches

Some submodules need local patches to compile against current imgui (e.g., `imgui-node-editor`'s `operator*` overload colliding with imgui's). Patches live in `patches/`, applied idempotently by `buildSrc/scripts/apply_vendor_patches.sh` (wired into `generateLibs`). When bumping a patched submodule, re-check the patch still applies; drop if upstream fixed the issue.

### Native source rewrites in `GenerateLibs.groovy`

`buildSrc/.../GenerateLibs.groovy` does literal-string rewrites against vendored C++ sources (e.g., swapping the default font loader in `imgui_draw.cpp`). When imgui renames the anchor symbol across versions, the rewrite silently no-ops and the native build ships unpatched — grep `replaceSourceFileContent(` on every submodule bump and refresh each anchor.

### AST regeneration drift

`./gradlew generateAst` may update JSONs unrelated to your bump (e.g., `ast-ImGuiFileDialog.json`, `ast-TextEditor.json`) because clang picks up different system headers per machine. **Revert those** — keep the diff scoped.

### Rebasing: regenerate, don't hand-merge

Conflict markers inside generated files or AST JSONs are a false fight. Take your side, then re-run `generateAst` / `generateApi` on the rebased tree. Hand-merging codegen output just produces drift.

### Submodule sync after branch switch

`git checkout <branch>` doesn't update submodules. If `include/*` pointers differ between branches you'll compile against the wrong headers with mystifying errors. After any branch/rebase touching `include/`: `git submodule update --init --recursive`.

### Don't commit build artifacts

Never commit `bin/libimgui-java64.*` or anything staged from `/tmp/imgui/` — `bin/` is owned by the post-merge CI update job. Prefer explicit `git add <path>` over `-A` to avoid picking these up.

## Design conventions

### Dual font loader (stb_truetype + FreeType)

Native build ships both loaders. `GenerateLibs.groovy` rewrites `imgui_draw.cpp` so the compile-time default is stb_truetype even when `IMGUI_ENABLE_FREETYPE` is set; `ImFontAtlas#setFreeTypeRenderer(boolean)` flips between them at runtime. When refactoring font-related code or bumping imgui, preserve this: the patch anchor (see Gotchas) and the Java toggle must stay in sync with imgui's current loader-selection API.

## Codegen conventions (when editing `buildSrc/`)

Only relevant if you're changing the generator itself (`buildSrc/src/main/kotlin/tool/generator/**`); routine source edits under `imgui-binding/src/main/java/` don't need this section.

### Don't use `<Nothing>` as a generic type arg in Spoon calls

Kotlin 2.x K2 emits a runtime `CHECKCAST java/lang/Void` for `<Nothing>` generic returns, which fails since Spoon returns concrete types. Use bound-appropriate types:

| Setter | Bound / use |
|---|---|
| `setType` | `<CtTypedElement<Any>>` |
| `setSimpleName` on CtNamedElement | `<CtNamedElement>` |
| `setSimpleName` on CtReference | `<CtReference>` |
| `setParent`, `setAnnotations`, `addAnnotation`, `setDocComment` | `<CtElement>` |
| `addModifier`, `setModifiers` | `<CtModifiable>` |
| `addParameter`, `addParameterAt`, `setParameters` | `<CtMethod<Any>>` |
| `setBody` | `<CtBodyHolder>` |
| `addStatement` | `<CtStatementList>` |
| `setTags`, `removeTag` | `<CtJavaDoc>` |
| `setValue` | `<CtCodeSnippet>` |
| `addValue` | `<CtAnnotation<Annotation>>` |
| `createMethod` / `createField` / `createParameter` | `<Any>` |

For `CtMethod<*>` receivers of setters bounded on `CtExecutable<captured *>`, Kotlin can't reconcile — cast:

```kotlin
@Suppress("UNCHECKED_CAST")
val newMethod = method.clone() as CtMethod<Any>
newMethod.setParameters<CtMethod<Any>>(newParams)
```

### Gradle 9 configuration cache

Use `providers.exec { ... }` for shell-outs, never `.execute()`. Capture resolved strings at configuration time; don't capture the `Project` model in closures that execute later.

## PR workflow

Keep submodule bumps, Gradle/deps bumps, and codegen changes as separate commits and separate PRs.

When multiple PRs are open, suggest a merge order:

1. Infrastructure (Gradle, build tooling) — unblocks everything else.
2. Independent changes (submodule refresh) — low risk, fast review.
3. Larger API bumps, in dependency order.

Offer to rebase after each preceding merge.

For CI failures, start with `gh pr checks <n>` to identify the failing job, then `gh run view --job <job-id> --log` for focused output. Reproduce locally — don't iterate on CI.
