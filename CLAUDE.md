# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

See [AGENTS.md](AGENTS.md) — it is the canonical guidance for AI agents in this repo and covers everything Claude Code
needs:

- What the repo is (JNI binding for Dear ImGui + extensions, codegen-driven multi-module Gradle build).
- Project layout (`imgui-binding/`, `imgui-lwjgl3/`, `imgui-app/`, `buildSrc/`, `include/` submodules, etc.).
- The **golden rule**: never edit `imgui-binding/src/generated/java/` — it is regenerated from annotated sources in
  `imgui-binding/src/main/java/`. Workflow: edit source → `./gradlew :imgui-binding:generateApi` → commit both trees
  together.
- Build & test commands (`compileJava`, `javadoc`, `generateApi`, `generateAst`, `generateLibs`, `build`) and the visual
  smoke-test flow for backend/font/example changes.
- Submodule-bump procedure for Dear ImGui and extensions, including AST regeneration scope rules.
- Gotchas: doclint-vs-C++ comment patterns, vendor patches, `GenerateLibs.groovy` literal-string rewrites, AST drift,
  rebase strategy for codegen output, submodule sync after branch switch, `bin/` ownership by CI.
- Design conventions (dual font loader: stb_truetype + FreeType).
- Codegen-internals conventions for `buildSrc/` (Spoon generic-type bounds, Gradle 9 configuration cache).
- PR workflow and merge-ordering guidance.

Treat `AGENTS.md` as authoritative. If something here ever conflicts with `AGENTS.md`, `AGENTS.md` wins — update it
there, not here.
