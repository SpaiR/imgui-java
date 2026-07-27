# Contributing to imgui-java

Contributions are welcome — bug reports, missing-binding requests, fixes, new binding surface, backend work, and doc
improvements alike.

This document covers the mechanics: how to build the project, the rules that decide whether a patch is reviewable, the
commit format, the PR flow, and how releases are cut.

- **Contents**
    - [Before you start](#before-you-start)
    - [Quick start](#quick-start)
    - [Development loop](#development-loop)
    - [Commit message format](#commit-message-format)
    - [Pull requests](#pull-requests)
    - [Releases](#releases)
    - [Contributing with AI agents](#contributing-with-ai-agents)
    - [Reporting bugs](#reporting-bugs)
    - [License](#license)

## Before you start

Four rules cause most of the rejected patches in this repo. Read them before writing code.

### 1. Never edit `imgui-binding/src/generated/java/`

The Java API is **codegen-driven**. Hand-written annotated stubs live in `imgui-binding/src/main/java/`; the Spoon-based
generator in `buildSrc/` expands them into `imgui-binding/src/generated/java/`. Both trees are committed, but only the
first is a source. Edits to the generated tree are silently reverted on the next `generateApi` run.

```bash
# edit imgui-binding/src/main/java/...
./gradlew :imgui-binding:generateApi
git add imgui-binding/src/main/java imgui-binding/src/generated/java
```

Source and regenerated output belong in the **same commit** — a commit that changes one without the other leaves the
tree inconsistent.

### 2. Never commit native binaries

`bin/libimgui-java64.*` and `bin/imgui-java64.dll` are owned by CI. The `update-bin` job rebuilds them on `main` after a
binding change and commits them as `[ci skip] update native binaries`. Locally built natives must not be staged.

Use `git add <specific-path>` instead of `git add -A` / `git add .` — bulk staging picks up `bin/` and
`imgui-binding/build/libsNative/` by accident.

### 3. One topic per pull request

Submodule bumps, Gradle/dependency bumps, codegen-tooling changes, and new API surface each go in their own PR. A
submodule bump already produces a large regenerated diff; adding a feature on top makes it unreviewable and makes CI
failures ambiguous. Land the bump first, add the new surface in a follow-up.

### 4. Javadoc must pass doclint

Doc comments in the binding are copied verbatim from Dear ImGui's C++ headers, and JDK 17's doclint rejects anything
that parses as broken HTML. CI runs javadoc as part of the build; a doc-comment typo fails the whole pipeline.

Wrap operators in `{@code ...}` — `<`, `>`, `&`, `&&`, `||`, `->` all break otherwise. Keep `@link` targets, `@param`,
and `@return` in sync with the current method signature.

## Quick start

```bash
git clone --recurse-submodules https://github.com/SpaiR/imgui-java.git
cd imgui-java
./gradlew :example:run
```

Already cloned without submodules, or switched branches?

```bash
git submodule update --init --recursive
```

`git checkout` does **not** update submodules. If `include/*` pointers differ between branches you will compile against
the wrong headers and get mystifying errors.

**Prerequisites**

| What                          | Needed for                                       | Notes                                                     |
|-------------------------------|--------------------------------------------------|-----------------------------------------------------------|
| Any recent JDK                | Everything                                       | Gradle's toolchain pins JDK 17 and downloads it if missing |
| A C++ toolchain               | Rebuilding the native library (`generateLibs`)   | Only if you touch JNI or `native` method signatures        |
| `clang++`                     | `./gradlew generateAst`                          | Only when bumping a submodule                              |
| `mingw-w64`                   | Cross-building the Windows native from Linux     | CI does this; rarely needed locally                        |

Java-only changes run fine against the natives already committed in `bin/` — no C++ toolchain required.

## Development loop

### Adding or changing binding API

```bash
# 1. edit the annotated source
$EDITOR imgui-binding/src/main/java/imgui/ImGui.java

# 2. regenerate the Java API
./gradlew :imgui-binding:generateApi

# 3. check
./gradlew :imgui-binding:compileJava
./gradlew :imgui-binding:javadoc
```

If you add, rename, remove, or reorder a `native` method, the committed natives no longer match the Java side and you
will get an `UnsatisfiedLinkError` at runtime — static checks will not catch it. Rebuild and smoke-test:

```bash
buildSrc/scripts/build.sh <macos|linux|windows>
cp /tmp/imgui/dst/libimgui-java64.<so|dylib|dll> bin/
./gradlew :example:run -PlibPath=$PWD/bin
```

Then **revert the `bin/` copy** before committing (see [rule 2](#2-never-commit-native-binaries)).

Backend, font, texture, and example changes also need a real run — a blank font atlas or an assertion loop does not show
up in a compile.

### Checks before you push

```bash
./gradlew :imgui-binding:compileJava   # fastest sanity check
./gradlew check                        # tests + Checkstyle (imgui-lwjgl3, imgui-app)
./gradlew :imgui-binding:javadoc       # zero `error:` lines is the bar
./gradlew buildAll                     # what CI runs
```

Checkstyle runs at `severity="error"` for `imgui-lwjgl3` and `imgui-app`. Fix the code rather than relaxing the rule; if
a rule genuinely does not fit a construct, suppress at the smallest possible scope with `@SuppressWarnings`. Do not add
entries to `config/checkstyle/suppressions.xml`.

`imgui-binding` ships with **zero runtime dependencies**. Adding one is a breaking change for downstream consumers and
needs sign-off before you write the code. Public modules also compile with `--release 8` — no `List.of`, `var`,
`String.isBlank()`, or other Java 9+ APIs in `imgui-binding`, `imgui-lwjgl3`, or `imgui-app`.

For deeper background — the codegen internals, the submodule-upgrade procedure, the dual font-loader design — see
[`AGENTS.md`](AGENTS.md). It is written for AI agents but applies equally to humans.

## Commit message format

*Adapted from the
[Angular commit message format](https://github.com/angular/angular/blob/92113d73dd38d0285e25e8d678c240cf4aa8834a/CONTRIBUTING.md#commit)
and [Conventional Commits](https://www.conventionalcommits.org/en/v1.0.0/).*

Commit history is a changelog for the next maintainer. Precise messages make it readable.

```
<header>
<BLANK LINE>
<body>
<BLANK LINE>
<footer>
```

The `header` is mandatory and must conform to the [header format](#commit-message-header).

The `body` is required whenever the reason for the change is not obvious from the header alone — anything with a
trade-off, a workaround, an upstream cause, or a behavior change. It is optional for self-evident one-liners (typo
fixes, `docs:` polish, dependency bumps).

The `footer` is optional, except where [noted below](#commit-message-footer).

### Commit message header

```
<type>(<scope>): <short summary>
  │      │             │
  │      │             └─⫸ Summary in imperative, present tense. Not capitalized. No period at the end.
  │      │
  │      └─⫸ Commit scope: see the scope table
  │
  └─⫸ Commit type: feat | fix | perf | refactor | docs | test | build | chore | revert
```

`<type>` and `<summary>` are mandatory; `(<scope>)` is optional but strongly preferred. Append `!` after the type or
scope (`feat(api)!:`) to flag a breaking change — that also requires a `BREAKING CHANGE:` footer.

Keep the whole first line under 72 characters.

#### Type

| Type       | Use for                                                                                                                       |
|------------|-------------------------------------------------------------------------------------------------------------------------------|
| `feat`     | New user-visible capability: new binding surface, a new backend, a new `Application` hook, a submodule bump that adds API.     |
| `fix`      | A bug fix in the binding, generator, backend, or build.                                                                       |
| `perf`     | Change whose point is speed or allocation — JNI marshaling, render-loop work. No behavior change. Say what you measured.        |
| `refactor` | Internal restructuring with no behavior change: rename, extract, reorganize.                                                   |
| `docs`     | Documentation only — `README.md`, `AGENTS.md`, this file, `.claude/`, or Javadoc-only edits.                                    |
| `test`     | Adding or changing tests under `src/test/`.                                                                                     |
| `build`    | Build system, Gradle, CI workflows, publishing, native toolchain, and **dependency bumps** (`build(deps)`, what Dependabot emits). |
| `chore`    | Housekeeping that fits nothing above: `.gitignore`, editor config, repo metadata.                                               |
| `revert`   | Reverts an earlier commit. The body must name it (`Reverts <sha>`) and say why.                                                 |

Prefer the most specific type. `chore` is the fallback, not the default — a dependency bump is `build(deps)`, a rename
is `refactor`, a Javadoc fix is `docs`.

#### Scope

Pick from the table. If nothing fits, omit the scope rather than inventing one.

| Scope        | Covers                                                                                            |
|--------------|---------------------------------------------------------------------------------------------------|
| `api`        | Public binding surface: annotated sources in `imgui-binding/src/main/java/` + regenerated output.  |
| `native`     | Hand-written JNI under `imgui-binding/src/main/native/` and the native build glue.                 |
| `generator`  | The codegen in `buildSrc/` — Spoon transforms, AST parsing, Gradle tasks.                          |
| `vendor`     | Submodule pointer bumps under `include/`, and the patches in `patches/` that keep them compiling.  |
| `lwjgl3`     | `imgui-lwjgl3` — the GLFW, OpenGL 3, and SDL3 backends.                                            |
| `app`        | `imgui-app` — the `Application` wrapper and bundled natives.                                        |
| `example`    | The `example` module.                                                                              |
| `deps`       | Dependency version bumps. Always paired with `build` (`build(deps)`).                               |
| `ci`         | GitHub Actions workflows, issue/PR templates, Dependabot config.                                    |
| `gradle`     | Build scripts, the wrapper, publishing setup.                                                       |
| `readme`     | `README.md` only.                                                                                   |
| `agents`     | `AGENTS.md`, `CLAUDE.md`, and `.claude/` rules.                                                     |
| `contributing` | This file.                                                                                        |

A submodule bump that also exposes new Java API is two commits — `vendor` for the pointer + regenerated AST, `api` for
the new surface — or better, two PRs.

#### Summary

* imperative, present tense: "add" not "added" nor "adds"
* don't capitalize the first letter
* no period at the end

### Commit message body

Same tense rules as the summary.

Explain **why**, not what — the diff already shows what changed. Name the upstream commit, the issue, the incident, or
the constraint that forced the change. When behavior changes, contrast old and new.

A 2–5 bullet list works well for multi-part changes.

### Commit message footer

The footer carries breaking-change and deprecation notes, issue references, and AI co-authorship trailers.

```
BREAKING CHANGE: <breaking change summary>
<BLANK LINE>
<breaking change description + migration instructions>
<BLANK LINE>
<BLANK LINE>
Fixes #<issue number>
```

or

```
DEPRECATED: <what is deprecated>
<BLANK LINE>
<deprecation description + recommended update path>
<BLANK LINE>
<BLANK LINE>
Closes #<pr number>
```

A `BREAKING CHANGE:` footer is **mandatory** whenever the header carries `!`. The summary line is short; the description
below must tell a downstream user exactly what to change in their code. This matters more here than in most projects —
the project version tracks Dear ImGui (see [Releases](#releases)), so the version number alone can never signal a break.

A `DEPRECATED:` footer is used when API is kept working for a release with a pointer to its replacement. Pair it with
the `@Deprecated` annotation and a `{@link ...}` in the Javadoc.

## Pull requests

1. Fork the repo (or branch off `main`, with direct push access).
2. Keep the PR to [one topic](#3-one-topic-per-pull-request).
3. Make sure `./gradlew buildAll` passes locally.
4. Open the PR against `main`.

### Title

Write a short, **descriptive** title for the whole change — do **not** paste the first commit's header. The title is
what a reviewer scans in the PR list, so it should read as plain prose describing the outcome, not as a
`<type>(<scope>): …` commit line.

- Sentence case, no trailing period, aim for under ~72 characters.
- Describe the whole PR, not just its first or largest commit.
- No `type(scope):` prefix — the **type is carried by the [label](#labels)**, not the title.

Examples — `feat(lwjgl3): add SDL3 backend` (commit) becomes **"Add an SDL3 backend to imgui-lwjgl3"** (PR title);
`fix(api): correct ImVec2 swap signature` becomes **"Fix reversed x/y in the ImVec2 swap overload"**.

PRs are squash-merged, so the maintainer writes the squash subject as a proper
[commit header](#commit-message-header) at merge time — the PR title is for humans reading the list, the commit subject
is for the history.

### Body

`.github/pull_request_template.md` pre-fills the body. Fill in what applies:

- **Summary** — the user-visible change and why it exists. Always required.
- **Type of change** — tick the matching checkbox.
- **Notes for reviewer** — design decisions, deliberate scope boundaries, follow-ups left for later. Omit if none.
- `Closes #<n>` / `Fixes #<n>` at the bottom — GitHub links and closes the issue on merge.

### Labels

Apply exactly one **type** label. There is one per commit type, so the mapping is direct:

| Commit type | Label      |
|-------------|------------|
| `feat`      | `feat`     |
| `fix`       | `fix`      |
| `perf`      | `perf`     |
| `refactor`  | `refactor` |
| `docs`      | `docs`     |
| `test`      | `test`     |
| `build`     | `build`    |
| `chore`     | `chore`    |
| `revert`    | `revert`   |

`build(deps)` takes **`deps`** instead of `build` — dependency bumps are their own review category, and Dependabot
applies that label on its own.

Then add **`breaking-change`** on top of the type label whenever the PR carries a `BREAKING CHANGE:` footer or a `!` in
the header. The version number can't signal a break (see [Releases](#releases)), so this label is how a breaking change
stays visible in the PR list and in the release notes.

`bug`, `question`, `missing binding`, `invalid`, and `wontfix` are issue-triage labels — not for PRs. Don't invent
labels; add one to the repo first if a genuinely new category shows up.

### What CI does

On every PR: builds the Java side (`buildAll`, which includes javadoc), then builds the native library for Linux,
Windows, and macOS. All four jobs must be green.

Two jobs run only on `main`, never on PRs or forks:

- `update-bin` — rebuilds `bin/` natives when the binding hash changed, and commits them.
- `release` — runs on `v*` tags only.

CI failures are reproducible locally. Start with `gh pr checks <n>` to find the failing job, then
`gh run view --job <job-id> --log`. Don't iterate by pushing to CI.

### Review

CI catches the mechanical problems — compilation, Checkstyle, javadoc, native builds on all three platforms. Review
focuses on what it can't judge: anything touching `src/generated/java/`, new dependencies in `imgui-binding`, Java 9+
APIs in consumed modules, and runtime behavior that no static check covers (a JNI signature change, a blank font atlas).

## Releases

Releases are cut by the maintainer. This section documents the procedure and the versioning rules that affect what you
put in commit messages.

### Versioning

The project version **tracks Dear ImGui**, it is not independent SemVer. A tag is the upstream version followed by an
imgui-java counter — `v<dear-imgui-version>.<build>`:

- The first three segments mirror the Dear ImGui release the binding ships, patch included.
- The fourth counts imgui-java's own releases on that upstream version, starting at `0`.

So `v1.92.7.1` is the second imgui-java release built on Dear ImGui `1.92.7`.

| imgui-java tag | Dear ImGui   |
|----------------|--------------|
| `v1.92.7.1`    | `1.92.7`     |
| `v1.92.0`      | `1.92.7`     |
| `v1.90.0`      | `1.90.9`     |
| `v1.89.0`      | `1.89.9`     |
| `v1.87.0` … `v1.87.7` | `1.87` — eight imgui-java releases on one upstream version |

Tags up to and including `v1.92.0` used a three-segment form in which only `MAJOR.MINOR` mirrored upstream and `PATCH`
counted imgui-java releases — which is why `v1.92.0` does not reveal that it ships Dear ImGui `1.92.7`. The fourth
segment closes that gap, at the cost of the third segment changing meaning from `v1.92.7.1` onwards. Ordering is
unaffected: Maven ranks `1.92.0 < 1.92.7.1 < 1.93.0.0`.

Consequence: **the version number cannot signal a breaking change.** Breaking changes are signalled by `!` in the commit
header, a `BREAKING CHANGE:` footer, the [`breaking-change` label](#labels) on the PR, and a dedicated section in the
GitHub release notes. Nothing else surfaces them.

There is no version file to bump. `build.gradle` derives the version from `git describe --tags` (leading `v` stripped),
so **the tag is the version of record**.

### Release notes

Every release gets hand-written notes on the draft. Start from GitHub's **Generate release notes** button and rewrite
what it produces — ship it raw only on a pure patch release with no user-visible change.

Sections, in this order. Only `What's Changed`, `List of changes`, `Thanks` and the changelog line are mandatory; the
rest appear when there is something to put in them.

| Section                     | When                                                              |
|-----------------------------|-------------------------------------------------------------------|
| `## What's Changed` + lead   | always — 1–3 sentences, and the lead always names the Dear ImGui version the release ships |
| `### ⚠️ Breaking changes`    | if anything breaks callers — always first, with migration instructions |
| `### Highlights`             | when the release has a headline; bold lead-in per bullet, detail nested underneath |
| `### Known limitations`      | when something notable is deliberately not implemented; if unchanged from the previous release, say so and link it rather than repeating |
| `### Migration notes`        | when calling code must change — before/after table, plus links to upstream release notes and the compare range |
| `### List of changes`        | always — one line per merged PR |
| `### Contributors`           | when someone outside the maintainer contributed; name what each person did |
| `## New Contributors`        | keep GitHub's auto-generated block verbatim when it produced one |
| `### Thanks`                 | always — fixed block, see below |
| `**Full Changelog**: <compare link>` | always — last line of the notes |

Writing rules:

- **Write for the consumer, not the committer.** "Native artifacts target Java 8 again", not "fix release target in
  `build.gradle`". Each line answers what changes for someone who depends on the library.
- **Change lines** read `* <what changed> by @user in <full PR url>`. Credit external contributors; drop `by @` on the
  maintainer's own PRs. Use full URLs, not `#123` — that is what the generator emits.
- **Breaking changes** get ⚠️ on the change line *and* the dedicated section. The version number cannot signal them,
  see [Versioning](#versioning).
- **Build and tooling bumps** go in a `<details>` block, with an explicit note that they do not reach the published
  artifacts and that `imgui-binding` still ships with zero runtime dependencies. Collapse repeated bumps of one
  dependency into a single line with the full range.
- **Submodule bumps** always link both the upstream release notes and the compare range.
- **Thanks** is a fixed block — reproduce it verbatim, in every release:
  ```md
  ### Thanks

  **Thanks to all contributors and users for your valuable feedback and support!
  You can support the project's development with a donation - your contribution helps keep it growing and improving.**

  [![ko-fi](https://ko-fi.com/img/githubbutton_sm.svg)](https://ko-fi.com/P5P5BF17Q)
  ```

The release title is the tag (`v1.92.7.1`). The draft stays a draft until the notes are proofread.

### Cutting a release

1. Make sure `main` is green and `bin/` is current — the `update-bin` job must have run after the last binding change.
   Releasing on stale natives ships a library whose Java and native sides disagree.
2. Create an empty commit named after the version and push it. Every release tag points at one of these, so the release
   gets a commit of its own instead of riding on whatever change happened to land last.
   ```bash
   git commit --allow-empty -m v1.92.7.1
   git push origin main
   ```
3. Tag that commit and push the tag:
   ```bash
   git tag v1.92.7.1
   git push origin v1.92.7.1
   ```
4. The `release` job runs on the tag: builds Java, builds all three natives, publishes every module and native
   classifier to Maven Central via `buildSrc/scripts/publish.sh`, then opens a **draft** GitHub release with
   `java-libraries.zip` and `native-libraries.zip` attached.
5. Write the release notes on the draft following [Release notes](#release-notes), proofread them, then publish.
6. Verify the artifacts appear on Maven Central under `io.github.spair`.

## Contributing with AI agents

AI coding agents (Claude Code, Copilot, Cursor, Codex, Gemini, etc.) are welcome to assist with contributions.
[`AGENTS.md`](AGENTS.md) is the canonical guidance for how they should work in this repo — golden rules, codegen
workflow, build commands, gotchas — with the detailed rules in `.claude/rules/`.

Two extra rules apply on top of the regular contribution flow:

1. **You are responsible for the change.** The agent is a tool — review the diff, run the build, run the example when
   natives are involved, and hold the PR to the same bar as a hand-written one. "The agent did it" is not a defense for
   a broken or low-quality patch.
2. **Every AI-assisted commit must be attributed via a `Co-authored-by` trailer.** This applies whether the agent wrote
   the whole commit or just a substantial part of it.

### `Co-authored-by` trailer

Add a `Co-authored-by` line to the [commit footer](#commit-message-footer) for any commit an AI agent helped produce.
Use the **short, family-level name** of the model — not the specific version — followed by the vendor noreply email.

```
Co-authored-by: <Model Family> <<vendor-noreply-email>>
```

| Model used                                 | Trailer                                          |
|--------------------------------------------|--------------------------------------------------|
| Claude Opus / Sonnet / Haiku (any version) | `Co-authored-by: Claude <noreply@anthropic.com>` |
| GitHub Copilot                             | `Co-authored-by: Copilot <copilot@github.com>`   |
| OpenAI Codex / GPT                         | `Co-authored-by: Codex <noreply@openai.com>`     |
| Google Gemini                              | `Co-authored-by: Gemini <noreply@google.com>`    |

Agent tooling often emits more than this — a versioned model name, a session URL, a "Generated with …" line. **Strip
all of it** and leave the family-level trailer. One `Co-authored-by` line per agent, at the very end of the message,
separated from the body by a blank line.

Full example:

```
fix(api): correct ImVec2 swap signature

The previous overload reversed x and y when round-tripping through
the native bridge. Restore the upstream argument order.

Fixes #123

Co-authored-by: Claude <noreply@anthropic.com>
```

Because PRs are squash-merged, verify the trailer survives into the final squash message — GitHub collects co-author
trailers from the squashed commits, but a hand-edited squash body can drop them.

## Reporting bugs

Open an issue with the matching template:

- [**Bug report**](https://github.com/SpaiR/imgui-java/issues/new?template=bug_report.yml) — something is broken.
- [**Missing bindings**](https://github.com/SpaiR/imgui-java/issues/new?template=missing_bindings.yml) — an upstream
  function, flag, or struct field is not exposed in Java. This is the right template for "ImGui has X, imgui-java
  doesn't".

The templates ask for the version, what happened, and a reproducer. Beyond those fields, the reports that get fixed
fastest include:

- the imgui-java version **and** the backend you use (imgui-app, imgui-lwjgl3 + GLFW, SDL3, or your own)
- a minimal `ImGui.*` call sequence that triggers it — a runnable snippet beats a description
- the OS and JDK
- the full stack trace, especially for `UnsatisfiedLinkError` and native crashes
- what you expected versus what happened, and anything you already tried

## License

By contributing, you agree that your contributions are licensed under the same
[MIT License](https://choosealicense.com/licenses/mit/) that covers the project. Contact the maintainer if that is a
concern.

Participation is governed by the [Code of Conduct](docs/CODE_OF_CONDUCT.md).
