# AGENTS.md

Guidance for AI coding agents working on `imgui-java`.

This file is the operational guide: what the repo is, how to build it, the procedure for
upgrading submodules, and the codegen-internals conventions. The everyday conventions and
"don't"s live in companion files — read them too:

- **`.claude/rules/guardrails.md`** — what *not* to do. Covers the golden rules (never
  edit `src/generated/java/`, never commit `bin/` natives), the JNI contract, Java 8
  compatibility on consumed classes, doclint pitfalls, dependency hygiene, AST drift,
  and the rule against mixing submodule bumps with feature work.
- **`.claude/rules/codestyle.md`** — Java style for `imgui-binding/`, `imgui-lwjgl3/`,
  `imgui-app/` (formatting, naming, modifiers, imports, javadoc, wrapper class shape,
  binding-source annotations). Authoritative copy is `config/checkstyle/checkstyle.xml`.
- **`.claude/docs/patterns.md`** — recurring patterns when adding to the binding (the
  codegen loop, annotated stubs, struct wrappers, out-parameter `Im*` wrappers, module
  boundaries, application lifecycle).
- **`docs/CONTRIBUTING.md`** — PR/commit workflow. Includes the conventional-commit
  format and, importantly, the **`Co-authored-by` trailer rule for AI-assisted commits**
  and the "you are responsible for the change" rule. Read it before opening a PR.

If anything below conflicts with those files, the dedicated file wins — update it
there, not here.

## What this repo is

JNI-based Java binding for [Dear ImGui](https://github.com/ocornut/imgui) + extensions (ImPlot, ImNodes, ImGuizmo, imgui-node-editor, imgui-knobs, imgui-file-dialog, imgui-text-edit, imgui-club). Multi-module Gradle build, published as `io.github.spair:imgui-java-{binding,lwjgl3,app}`. Java is **codegen-driven**: annotated stubs in `imgui-binding/src/main/java/`, expanded by the Spoon-based generator in `buildSrc/` into `imgui-binding/src/generated/java/` — both trees committed.

The most important rule to internalize before doing anything else: **never edit
`imgui-binding/src/generated/java/` by hand** — it is regenerated from the annotated
sources in `imgui-binding/src/main/java/`. See `.claude/rules/guardrails.md` for the
full statement and the workflow.

## Project layout

| Path                                                   | What's there                                                                                            |
|--------------------------------------------------------|---------------------------------------------------------------------------------------------------------|
| `imgui-binding/`                                       | Core JNI binding + codegen                                                                              |
| `imgui-binding/src/main/java/`                         | Hand-written annotated sources (`@BindingSource`, `@BindingField`, `@BindingMethod`, `@BindingAstEnum`) |
| `imgui-binding/src/generated/java/`                    | Codegen output; committed. See `.claude/rules/guardrails.md`                                            |
| `imgui-binding/src/main/native/`                       | Hand-written JNI `.cpp` / `.h` (struct marshaling etc.)                                                 |
| `imgui-lwjgl3/`                                        | LWJGL backend (GLFW + OpenGL)                                                                           |
| `imgui-app/`                                           | Convenience wrapper bundling natives + an `Application` entry-point                                     |
| `include/`                                             | Git submodules: imgui, implot, imnodes, imguizmo, etc.                                                  |
| `bin/`                                                 | Native libs (`.so/.dylib/.dll`), committed by CI                                                        |
| `buildSrc/`                                            | Gradle plugin: codegen tasks, JNI build, AST parser                                                     |
| `buildSrc/src/main/resources/generator/api/ast/*.json` | Clang-dumped AST data, consumed by `@BindingAstEnum`                                                    |
| `example/`                                             | Example apps                                                                                            |
| `patches/`                                             | Local patches against vendored submodules                                                               |

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

The general "don't"s — javadoc doclint, AST drift, committing `bin/` artifacts — are
all spelled out in `.claude/rules/guardrails.md`. The items below are submodule-bump
specifics that aren't covered there.

### Vendor patches

Some submodules need local patches to compile against current imgui (e.g., `imgui-node-editor`'s `operator*` overload colliding with imgui's). Patches live in `patches/`, applied idempotently by `buildSrc/scripts/apply_vendor_patches.sh` (wired into `generateLibs`). When bumping a patched submodule, re-check the patch still applies; drop if upstream fixed the issue.

### Native source rewrites in `GenerateLibs.groovy`

`buildSrc/.../GenerateLibs.groovy` does literal-string rewrites against vendored C++ sources (e.g., swapping the default font loader in `imgui_draw.cpp`). When imgui renames the anchor symbol across versions, the rewrite silently no-ops and the native build ships unpatched — grep `replaceSourceFileContent(` on every submodule bump and refresh each anchor.

### Rebasing: regenerate, don't hand-merge

Conflict markers inside generated files or AST JSONs are a false fight. Take your side, then re-run `generateAst` / `generateApi` on the rebased tree. Hand-merging codegen output just produces drift.

### Submodule sync after branch switch

`git checkout <branch>` doesn't update submodules. If `include/*` pointers differ between branches you'll compile against the wrong headers with mystifying errors. After any branch/rebase touching `include/`: `git submodule update --init --recursive`.

### Doclint sanitizer coverage gap

Only enum-constant docs are auto-sanitized (via `ast_content.kt`'s `sanitizeDocComment`); class- and method-level
Javadoc is copied verbatim from upstream C++ headers. Extending the sanitizer to cover those too would eliminate the
manual `{@code ...}` step on bumps — open opportunity. Until then, run javadoc locally before pushing (see
`.claude/rules/guardrails.md` → "Don't break the doclint build" for the fix patterns).

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

The full commit-message format, the conventional-commit types/scopes, and the
mandatory `Co-authored-by` trailer for AI-assisted commits are documented in
`docs/CONTRIBUTING.md` — follow that. The rule that submodule bumps, Gradle/deps
bumps, and codegen-tooling changes each go in their own PR is in
`.claude/rules/guardrails.md` ("Don't merge submodule bumps with feature work").

Operational guidance on top of those:

- When multiple PRs are open, suggest a merge order:
    1. Infrastructure (Gradle, build tooling) — unblocks everything else.
    2. Independent changes (submodule refresh) — low risk, fast review.
    3. Larger API bumps, in dependency order.
- Offer to rebase after each preceding merge.
- For CI failures, start with `gh pr checks <n>` to identify the failing job, then `gh run view --job <job-id> --log`
  for focused output. Reproduce locally — don't iterate on CI.
