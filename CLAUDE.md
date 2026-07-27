# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

Read [AGENTS.md](AGENTS.md) — it is the canonical and authoritative guidance for AI agents in this repo, covering
project layout, build & test commands, the codegen workflow, submodule bumps, gotchas, conventions, and the PR process.

The golden rule from there, repeated because it is easy to trip over: **never edit
`imgui-binding/src/generated/java/`** — it is regenerated from annotated sources in `imgui-binding/src/main/java/`.

If anything here ever conflicts with `AGENTS.md`, `AGENTS.md` wins — update it there, not here.
