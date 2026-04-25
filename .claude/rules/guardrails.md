# Guardrails

What **not** to do when working in this repo. Applies to **`imgui-binding`**, **`imgui-lwjgl3`**, **`imgui-app`**, and
other production modules.

## Scope note: `buildSrc/` is out of scope

The Gradle plugin and codegen tooling under `buildSrc/` are in an actively-evolving state. Style, structure, and
shortcuts there reflect work-in-progress, not project conventions. **Do not extract style rules, naming patterns, or "
the project does X" claims from `buildSrc/`.** When the task is "fix a bug in the binding" or "add a new wrapper," do
not borrow patterns from generator code as authority.

If the task explicitly targets `buildSrc/`, that is a separate context with its own (currently informal) rules — see
`AGENTS.md` for the few constraints that do apply there (Spoon generic-type bounds, Gradle 9 configuration cache).

---

## The big two

### 1. Never edit `imgui-binding/src/generated/java/`

This directory is **codegen output**. Hand-edits are silently reverted on the next
`./gradlew :imgui-binding:generateApi`.

The single source of truth is `imgui-binding/src/main/java/`. Workflow is always:

```
edit imgui-binding/src/main/java/...
./gradlew :imgui-binding:generateApi
git add imgui-binding/src/main/java imgui-binding/src/generated/java
git commit
```

If you find yourself wanting to fix something in the generated tree, that's a generator bug — file it or fix in
`buildSrc/`, do not work around it in the output.

### 2. Never commit native build artifacts manually

`bin/libimgui-java64.*` and `bin/imgui-java64.dll` are owned by CI's post-merge update job. Do not stage them as part of
feature work.

- Use `git add <specific-path>` rather than `git add -A` / `git add .` — bulk staging picks up `bin/` and `/tmp/imgui/`
  outputs by accident.
- The same applies to anything under `imgui-binding/build/libsNative/` or `/tmp/imgui/`.

---

## Don't break the JNI contract

### Don't reorder, rename, or remove `native` method signatures without regenerating

Native `Java_imgui_*` symbols on the C++ side are bound by JNI to the exact Java signature. Renaming a parameter is
fine; renaming a method, changing its parameter types, or moving it to a different class breaks the link silently —
you'll get `UnsatisfiedLinkError` only at runtime.

When adding/changing a `native` method:

1. Edit the source class under `src/main/java/`.
2. Run `./gradlew :imgui-binding:generateApi` to regenerate the Java side.
3. Run `./gradlew :imgui-binding:generateLibs` (or `buildSrc/scripts/build.sh <os>`) to rebuild the native lib.
4. Smoke-test by running `:example:run` against the locally-built lib.

Static checks alone are not enough: missing JNI bindings only surface when the call is actually made.

### Don't reach into `ImGuiStruct.ptr` from feature code

The `public long ptr` field on `imgui.binding.ImGuiStruct` is a binding implementation detail that the generator and JNI
layer rely on. It is intentionally public so the generator can swap pointers when iterating native arrays. Application
code should treat it as opaque — interact via the methods on the wrapper, not the raw pointer.

### Don't allocate native handles outside the binding

Anything that owns native memory comes from a wrapper that knows how to dispose it (`ImGuiStructDestroyable`, the
lifecycle hooks in `imgui.app.Application`). Don't construct fake `ImGuiStruct` instances pointing at addresses you
computed yourself, and don't bypass `ImGui.init()` — the static initializer in `ImGui.java` does library loading, JNI
handshake, and InputText callback setup, and other classes assume it has run.

---

## Don't loosen Checkstyle to avoid fixing code

`config/checkstyle/checkstyle.xml` runs at `severity="error"` for `imgui-lwjgl3` and `imgui-app`. When a check fires:

- Fix the code first.
- If a rule legitimately doesn't fit a specific construct, suppress at the **smallest scope** with
  `@SuppressWarnings("...")`.
- Do **not** add entries to `config/checkstyle/suppressions.xml` to silence warnings — those are reserved for the
  `imgui.*` codegen-touched package and a few historical carve-outs. New blanket suppressions weaken the signal for the
  whole codebase.
- Do **not** disable checks in the XML to "unblock" a PR.

---

## Don't break Java 8 compatibility on consumed classes

The build uses JDK 17 toolchain but compiles with `--release 8`. Public modules ship as Java 8 bytecode for downstream
consumers.

This means: **no Java 9+ APIs** in code that lands in `imgui-binding`, `imgui-lwjgl3`, or `imgui-app`, even if your IDE
auto-completes them. Common foot-guns:

- `List.of(...)`, `Map.of(...)`, `Set.of(...)` — Java 9.
- `String.isBlank()`, `String.strip()`, `String.repeat()` — Java 11.
- `var` for local types — Java 10.
- Stream collectors that arrived after 8 (`Collectors.toUnmodifiableList()` etc.).
- `Optional.isEmpty()`, `Optional.or(...)` — Java 9/11.

Use `Collections.singletonList(...)`, `new HashMap<>()`, `!s.trim().isEmpty()`, explicit types, etc.

---

## Don't break the doclint build

Javadoc runs as part of CI for `imgui-binding`. The doc comments are largely copied verbatim from Dear ImGui's C++
headers, and JDK 17's strict doclint trips on patterns that look like malformed HTML.

When pasting upstream comments:

- Wrap operators in `{@code ...}` — `<`, `>`, `&`, `&&`, `||`, `->` all break otherwise.
- `@link` references must resolve to an existing method **with the current signature**. After a method rename or
  parameter change, every `@link` to it must be updated in the same commit.
- `@param` and `@return` tags must match the current method shape; mismatched tags are doclint errors.

For a fast local check, run the javadoc snippet from `AGENTS.md` ("Fast javadoc iteration without Gradle").

---

## Don't introduce dependencies casually

`imgui-binding` ships with **zero runtime dependencies** — that is a feature, not an accident. Adding a dependency to
the binding is a breaking change for downstream consumers and must be discussed before doing it.

- `imgui-lwjgl3` may depend on LWJGL (it already does — that's the point).
- `imgui-app` bundles LWJGL + native libs and that's it.
- New transitive deps under `imgui-binding` are a hard "no" without explicit sign-off.

When tempted to pull in Guava / Apache Commons / SLF4J for a one-off utility — write the utility instead. Look at how
`ImGui.java` resolves the native lib path with stdlib `Files`/`Paths` only.

---

## Don't ship debug/log output by default

The binding does not log. There is no SLF4J, no `System.out.println`. The one place stderr is acceptable is the
assertion callback in `ImGui.java`'s static init, and that's a bug-discovery tool, not general logging.

If you need diagnostics during development, use them and remove before commit. Production code paths must stay quiet —
downstream applications integrate this library into their own logging, and surprise stdout breaks them.

---

## Don't merge submodule bumps with feature work

When upgrading Dear ImGui or an extension submodule:

- Bump the submodule, regenerate AST, regenerate API, fix javadoc — **one PR**.
- New surface added on top of the bump (e.g., exposing a new flag) — **separate PR**, after the bump merges.
- Same for Gradle/dependency bumps and codegen-tooling changes — keep them in their own PRs.

Mixing makes the diff unreviewable and the failure mode unclear when CI breaks.

---

## Don't trust AST regeneration to scope itself

`./gradlew generateAst` may rewrite JSONs unrelated to the submodule you bumped (e.g. `ast-ImGuiFileDialog.json`,
`ast-TextEditor.json`) because clang picks up different system headers per machine. Those changes are local environment
drift, not real API changes — **revert them** before committing. Keep the AST diff scoped to the submodule you actually
touched.
