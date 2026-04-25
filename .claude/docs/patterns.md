# Patterns

Common patterns for working in this codebase. Applies to **`imgui-binding`**, **`imgui-lwjgl3`**, **`imgui-app`** and
their tests/examples. The codegen plumbing under `buildSrc/` is **not** part of these patterns — see
`.claude/rules/guardrails.md` for the rationale.

## Working with the binding

### The codegen loop

The Java public API is materialized in two trees:

```
imgui-binding/src/main/java/        ← hand-written, source of truth
imgui-binding/src/generated/java/   ← codegen output, committed
```

You edit the first; the generator (`buildSrc/`) writes the second. Both are checked in so downstream consumers see a
normal Java project, but only the source tree is meaningful for review.

```
# typical edit flow
edit imgui-binding/src/main/java/imgui/Foo.java
./gradlew :imgui-binding:generateApi
./gradlew :imgui-binding:javadoc        # catches doclint regressions early
git add -p imgui-binding/src/main/java imgui-binding/src/generated/java
git commit
```

If you skip `generateApi`, the generated tree drifts and CI fails. If you commit only the generated tree, your change
vanishes on the next regen.

### Annotated stubs as a vocabulary

The hand-written sources use annotations from `imgui.binding.annotation` to describe the wanted Java API on top of the
C++ shape:

| Annotation                     | Use                                                                                                                     |
|--------------------------------|-------------------------------------------------------------------------------------------------------------------------|
| `@BindingSource`               | Class-level: this type participates in codegen.                                                                         |
| `@BindingMethod`               | Method-level: emit a JNI bridge for this method. `name=` overrides the Java name; `callName=` overrides the C++ symbol. |
| `@BindingField`                | Field-level: emit getter/setter for a struct field.                                                                     |
| `@BindingAstEnum`              | Bind a class to a clang-AST enum dump in `buildSrc/.../api/ast/`.                                                       |
| `@OptArg`                      | Mark a parameter as having an overload that omits it.                                                                   |
| `@ArgValue`, `@ArgVariant`     | Argument variant hints (e.g. `ImVec2` vs `(float, float)`).                                                             |
| `@ReturnValue`                 | Return-value hints (`isStatic` for shared instances, etc.).                                                             |
| `@TypeArray`, `@TypeStdString` | Map between Java arrays/`String` and C++ container types.                                                               |
| `@ExcludedSource`              | Skip this source when running codegen.                                                                                  |

When adding a new method, copy the annotation pattern from a structurally similar nearby method — the generator is
sensitive to placement and combinations.

### Wrapping a native struct

The recurring shape (see `ImVec2`, `ImInt`, `ImFontGlyph`, `ImColor`):

1. Extend `imgui.binding.ImGuiStruct` if the type wraps a native pointer; otherwise it's a value type with public
   mutable fields or a backing `T[] data` array.
2. Constructors: no-arg, copy (`new Foo(other)`), and per-field value form.
3. Fluent `set(...)` returning `this`.
4. `@Override` `toString` / `equals` / `hashCode`. Add `clone` with `@SuppressWarnings("MethodDoesntCallSuperMethod")`
   and the copy-constructor inside.
5. Annotate `@BindingSource` if it's bound from upstream; otherwise leave plain.

Don't invent a new layout — readers expect this one.

### Out-parameters via `Im*` wrappers

Dear ImGui's C++ API frequently takes `T*` for values the function will mutate (`bool* p_open`, `int* current_item`,
`float* values`). Java can't do that with primitives, so the binding uses single-element wrappers under `imgui.type`:

| C++                     | Java wrapper |
|-------------------------|--------------|
| `bool*`                 | `ImBoolean`  |
| `int*`                  | `ImInt`      |
| `short*`                | `ImShort`    |
| `long long*`            | `ImLong`     |
| `float*`                | `ImFloat`    |
| `double*`               | `ImDouble`   |
| `char*`, `std::string*` | `ImString`   |

Pattern from a caller's perspective:

```java
ImBoolean isOpen = new ImBoolean(true);
if (ImGui.begin("Window", isOpen)) {
    // ...
    ImGui.end();
}
if (!isOpen.get()) {
    // window was closed via the title-bar X
}
```

When designing a new binding method that takes an out-parameter, mirror this — accept the wrapper, not a `boolean[]` or
`AtomicReference`.

### `@OptArg` and overload generation

A C++ default argument becomes a series of Java overloads, generated automatically. In source you write the most general
signature once and tag the optional tail:

```java
@BindingMethod
public static native void ShowDemoWindow(@OptArg ImBoolean pOpen);
```

The generator emits both `showDemoWindow()` and `showDemoWindow(ImBoolean)`. You don't need to hand-write the overloads,
and you shouldn't.

### Enums as constants on a holder class

Dear ImGui's enums (`ImGuiWindowFlags`, `ImGuiCond`, `ImGuiKey`, …) are surfaced as classes under `imgui.flag.*` with
`public static final int` constants and a private constructor. They're driven from clang AST dumps via
`@BindingAstEnum`. When upstream adds or renames an enum value, regenerate the AST, then `generateApi` propagates it to
the flag class.

Don't hand-edit the flag classes. If a value is missing, the AST is stale.

## Module boundaries

```
imgui-binding   ← Dear ImGui Java API + JNI. Zero runtime deps.
imgui-lwjgl3    ← GLFW + OpenGL backend. Depends on lwjgl + imgui-binding.
imgui-app       ← Application abstraction. Depends on lwjgl + imgui-binding + imgui-lwjgl3, ships natives in the jar.
example         ← Demos. Depends on imgui-app.
```

Rules of thumb:

- New ImGui surface (a new function from upstream) → `imgui-binding`.
- New backend wiring (alternate GL version, headless renderer) → new module sibling to `imgui-lwjgl3`. Don't add Vulkan
  to `imgui-lwjgl3`.
- New Application convenience (an extra lifecycle hook) → `imgui-app`. Keep it minimal — it's meant as a starting point,
  not a framework.
- Demo of a new feature → `example/src/main/java/Example*.java`, named `Example<Feature>.java`.

Cross-module dependencies are one-way. `imgui-binding` must not depend on lwjgl or anything else.

## Lifecycle of an `imgui-app` application

`Application` extends `Window`. The `launch(...)` entry point runs:

```
configure(Configuration)      ← override to set title/size/etc.
initWindow(Configuration)
initImGui(Configuration)      ← override to load fonts, set styles
preRun()                      ← override: one-shot setup
loop:
    preProcess()              ← override: per-frame pre-work
    process()                 ← override: your UI
    postProcess()             ← override: per-frame post-work
postRun()                     ← override: one-shot teardown
disposeImGui()
disposeWindow()
```

Threading: ImGui is single-threaded by design. Heavy work goes outside `process()` (background threads + lock-free
hand-off), not inside it. The `imgui.app.Application` Javadoc spells this out.

## Tests

Tests use **JUnit Jupiter (5.x)** — see `imgui-binding/build.gradle`. They run via `./gradlew :imgui-binding:test`.
There is no separate "integration" suite; test what you can in pure JVM, and rely on the example app for visual smoke
tests. UI behavior changes (new widget rendering, font glyph coverage, viewport handling) cannot be unit-tested — run
`./gradlew :example:run -PlibPath=...` and verify by eye.

The `TypeName` Checkstyle rule is suppressed under `src/test/` so tests can use names like `ImGui_Test` without
complaint, but otherwise tests follow the same code style as production code.

## Building native libs locally

Two flows, both documented in `README.md`:

1. **Recommended**: `buildSrc/scripts/build.sh <windows|linux|macos>` — runs `vendor_freetype.sh`, calls `generateLibs`,
   drops the binary in `/tmp/imgui/dst/`. Matches what CI does.
2. **Direct**: `./gradlew imgui-binding:generateLibs -Denvs=<csv> -Dlocal` — skip when you don't need FreeType or want
   output under the project tree.

After building, point the example at the fresh binary:

```
cp /tmp/imgui/dst/libimgui-java64.<so|dylib|dll> bin/
./gradlew :example:run -PlibPath=$PWD/bin
```

Iterate `:example:run` (instead of `:imgui-binding:generateLibs` again) for Java-only changes — the native lib only
needs rebuilding when JNI signatures or vendored sources change.

## Submodule bumps in one paragraph

`AGENTS.md` is the canonical guide. The condensed flow: read upstream's changelog and header diff before touching
anything; bump the submodule pointer; `./gradlew generateAst` and revert any AST changes outside your scope; update
annotated sources to match upstream's renames/removals; `./gradlew :imgui-binding:generateApi`; run javadoc locally and
fix doclint hits; rebuild natives. Submodule bumps go in their own commit, separate from new-feature exposure.

## Where to look first

- `imgui.ImGui` — the giant entry-point class (1.4 MB worth of generated bindings + the static lib loader).
- `imgui.binding.ImGuiStruct` — the JNI handshake.
- `imgui.app.Application` — the lifecycle template you'd subclass.
- `imgui-lwjgl3/.../ImGuiImplGl3.java` — reference for what a full backend looks like.
- `example/src/main/java/Main.java` — the smallest end-to-end runnable, plus per-extension `Example*.java` siblings.
- `AGENTS.md` (repo root) — submodule-bump procedure, generator gotchas, doclint pitfalls.
