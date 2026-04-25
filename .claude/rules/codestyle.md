# Code Style

Applies to **`imgui-binding/src/main/java/`**, **`imgui-lwjgl3/`**, **`imgui-app/`** and any other production module.
Does **not** apply to `buildSrc/` — see `guardrails.md`.

The authoritative rules live in `config/checkstyle/checkstyle.xml`; this document is the human-readable summary. If the
two disagree, Checkstyle wins.

## Formatting

- **Indent**: 4 spaces. Never tabs (`FileTabCharacter` enforced).
- **Line length**: 160 columns max.
- **Trailing whitespace**: forbidden. End every file with a single newline.
- **EOL**: LF (`.editorconfig` pins this; do not introduce CRLF).
- **Braces**: K&R / Java standard — opening brace on the same line, single-statement `if`/`for`/`while` still get
  braces (`NeedBraces`).
- **Blocks**: no nested anonymous `{ ... }` blocks (`AvoidNestedBlocks`); empty blocks must have a comment explaining
  why (`EmptyBlock`).

## Naming

Follows standard Java conventions, enforced by Checkstyle:

| Element                        | Convention               | Example                        |
|--------------------------------|--------------------------|--------------------------------|
| Package                        | lowercase, dot-separated | `imgui.binding.annotation`     |
| Class / interface / annotation | UpperCamelCase           | `ImFontAtlas`, `BindingMethod` |
| Method                         | lowerCamelCase           | `getFontSize()`                |
| Field / parameter / local      | lowerCamelCase           | `fontGlyphRanges`              |
| Constant (`static final`)      | UPPER_SNAKE_CASE         | `LIB_PATH_PROP`                |

Class names that wrap a Dear ImGui struct or enum keep the upstream prefix (`ImGuiIO`, `ImGuiStyle`, `ImGuiKey`) so Java
code reads close to C++ headers. Vector wrappers drop the namespace (`ImVec2`, `ImVec4`).

## Imports

- **No star imports**, with two whitelisted exceptions for `org.lwjgl.opengl.GL32` and `org.lwjgl.glfw.GLFW` — both
  expose hundreds of constants and the explicit listing pattern is established (see `ImGuiImplGl3.java`).
- **Sort order**: third-party imports, then `java.*` / `javax.*`, then `static` imports — a blank line between the
  regular and the static block. Match what's already in `ImGuiImplGl3.java`.
- **No unused imports** (`UnusedImports`). Don't keep them around "just in case".
- **Static imports** are fine for OpenGL constants, JUnit assertions, and similar bulk APIs. Use the same package per
  file (e.g. consolidate everything on `GL32` even if a symbol exists in `GL11` — keeps the file uniform).

## Modifiers & visibility

- Modifier order is JLS-canonical: `public static final ...`, never `final static`.
- Redundant modifiers (`public` on interface methods, `static` on nested interface, etc.) are flagged.
- **Method parameters and local variables are `final` by default** (`FinalParameters`, `FinalLocalVariable`). Only drop
  `final` when reassignment is genuinely needed; suppression for `ImGui.java` is intentional (it is generator-touched
  and not representative).
- **`HideUtilityClassConstructor`**: utility classes (`ImColor`, `ImColor`-style `final class` with only static methods)
  must declare a `private` no-arg constructor.
- **`FinalClass`**: a class with only private constructors must be declared `final`.
- **`VisibilityModifier`**: fields must be `private` unless there is a documented reason to expose them. Native struct
  wrappers expose the `long ptr` field on purpose — that exception is established in `imgui.binding.ImGuiStruct` and the
  `imgui` package is whitelisted in `suppressions.xml`.
- **`DesignForExtension`**: any non-final, non-private method on a non-final class must be `final`, `abstract`, or have
  an empty body — i.e. design extension points explicitly. The `imgui.app` package is suppressed because `Application`
  is meant to be subclassed; do the same only when subclass-friendly behavior is the intent.

## Common-coding rules

Enforced by Checkstyle and worth keeping in mind:

- `EmptyStatement` — no stray `;`.
- `EqualsHashCode` — override both or neither.
- `InnerAssignment` — don't assign inside conditions.
- `MagicNumber` — extract numeric literals other than `-1, 0, 1, 2` into named constants. Suppressed for the `imgui.*`
  packages because the binding mirrors raw enum values from C++ headers; do **not** use that as a license elsewhere.
- `MultipleVariableDeclarations` — one declaration per line (`int a, b;` is rejected).
- `MissingSwitchDefault`, `SimplifyBooleanExpression`, `SimplifyBooleanReturn`.
- `UpperEll` — `1L`, never `1l`.
- `ArrayTypeStyle` — `int[] arr`, never `int arr[]`.

## Whitespace

`GenericWhitespace`, `OperatorWrap`, `ParenPad`, `WhitespaceAround` and friends are all on. In practice: standard Java
spacing — space around binary operators and after commas, no space inside parens, generic angle brackets touch the type
name (`Map<String, Long>`).

## Javadoc

- The binding's public API ships Javadoc copied verbatim from upstream Dear ImGui headers. Preserve those comments when
  editing — they round-trip through codegen.
- Inline operators that look like HTML (`<`, `>`, `&`, `->`) trip JDK 17 doclint. Wrap them in `{@code ...}`. Examples:
    - `(flags & X)` → `{@code (flags & X)}`
    - `if threshold < 0.0f` → `{@code if threshold < 0.0f}`
    - `"Demo->Child->X"` → `{@code Demo->Child->X}`
- `@deprecated` Javadoc must come together with `@Deprecated` annotation, and should point at the replacement (
  `{@link #...}`).
- Don't add commentary for the sake of comments. The `ImColor`-style one-liner Javadoc on each overload is the bar —
  accurate, terse.

## Class structure (binding wrappers)

Recurring pattern in `imgui.*` data classes (`ImVec2`, `ImInt`, `ImFontGlyph`, …):

1. Public mutable fields **or** a private backing array, exposed via `get/set`.
2. No-arg, copy, and value constructors.
3. Fluent `set(...)` returning `this`.
4. `@Override` for `toString`, `equals`, `hashCode`, `clone` (with `@SuppressWarnings("MethodDoesntCallSuperMethod")` on
   `clone`).
5. For struct pointers — extend `imgui.binding.ImGuiStruct`; never re-implement the `long ptr` field manually.

When adding a new wrapper, mirror this layout — readers expect it.

## Annotations on binding sources

In `imgui-binding/src/main/java/`, hand-written sources are fed into the codegen via annotations from
`imgui.binding.annotation`:

- `@BindingSource` — class-level marker that the type participates in codegen.
- `@BindingMethod`, `@BindingField`, `@BindingAstEnum` — method/field-level metadata.
- `@OptArg`, `@ArgValue`, `@ArgVariant`, `@ReturnValue`, `@TypeArray`, `@TypeStdString` — fine-grained signature hints.
- `@ExcludedSource` — exclude from codegen processing.

Use the existing surrounding methods as the template; the generator is sensitive to annotation placement.

## Suppressing rules

- Prefer fixing the code over suppressing the rule.
- If suppression is required, use `@SuppressWarnings("CheckName")` at the smallest possible scope. Class-level
  suppression of generic `"unused"` is reserved for `ImGui.java` and similar generator-fed entry points.
- Do **not** add new entries to `config/checkstyle/suppressions.xml` casually. That file currently relaxes rules for the
  codegen-touched `imgui` package; adding more reduces signal across the rest of the codebase.
