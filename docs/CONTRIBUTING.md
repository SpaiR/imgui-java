# Contributing to imgui-java
We love your input! We want to make contributing to this project as easy and transparent as possible, whether it's:

- Reporting a bug
- Discussing the current state of the code
- Submitting a fix
- Proposing new features
- Becoming a maintainer

## We Develop with Github
We use github to host code, to track issues and feature requests, as well as accept pull requests.

## We Use [Github Flow](https://docs.github.com/en/get-started/quickstart/github-flow), So All Code Changes Happen Through Pull Requests
Pull requests are the best way to propose changes to the codebase (we use [GitHub Flow](https://docs.github.com/en/get-started/quickstart/github-flow)). We actively welcome your pull requests:

1. Fork the repo and create your branch from `main`.
2. Make sure your code lints.
3. Issue that pull request!

## Commit Message Format

*This specification is inspired by and adapts 
the [Angular commit message format](https://github.com/angular/angular/blob/92113d73dd38d0285e25e8d678c240cf4aa8834a/CONTRIBUTING.md#commit) 
and [Conventional Commits](https://www.conventionalcommits.org/en/v1.0.0/) practises.*

We have very precise rules over how our Git commit messages must be formatted.
This format leads to **easier to read commit history**.

Each commit message consists of a **header**, a **body**, and a **footer**.


```
<header>
<BLANK LINE>
<body>
<BLANK LINE>
<footer>
```

The `header` is mandatory and must conform to the [Commit Message Header](#commit-header) format.

The `body` is mandatory for all commits except for those of type "docs".
When the body is present it must be at least 20 characters long and must conform to the [Commit Message Body](#commit-body) format.

The `footer` is optional. The [Commit Message Footer](#commit-footer) format describes what the footer is used for and the structure it must have.


#### <a name="commit-header"></a>Commit Message Header

```
<type>(<scope>): <short summary>
  │       │             │
  │       │             └─⫸ Summary in present tense. Not capitalized. No period at the end.
  │       │
  │       └─⫸ Commit Scope: binding|api|generator
  │
  └─⫸ Commit Type: build|ci|docs|feat|fix|perf|refactor|test
```

The `<type>` and `<summary>` fields are mandatory, the `(<scope>)` field is optional.


##### Type

Must be one of the following:

* **feat**: A new feature
* **fix**: A bug fix
* **build**: Changes that affect the build system, CI or external dependencies
* **chore**: Routine tasks, refactoring, updates, minor improvements
* **docs**: Documentation only changes


##### Scope
The following is the list of supported scopes:

* `binding` - changes to the binding code (ex. changes in `imgui.binding` package or library loading in `imgui.ImGui` class)
* `api` - changes to the API (ex. adding binding for another extension or new supported native methods)
* `generator` - changes to the API generator (ex. improved generation code etc.)
* `ci` - changes to the project CI (ex. adding a new CI job)

##### Summary

Use the summary field to provide a succinct description of the change:

* use the imperative, present tense: "change" not "changed" nor "changes"
* don't capitalize the first letter
* no dot (.) at the end


#### <a name="commit-body"></a>Commit Message Body

Just as in the summary, use the imperative, present tense: "fix" not "fixed" nor "fixes".

Explain the motivation for the change in the commit message body. This commit message should explain _why_ you are making the change.
You can include a comparison of the previous behavior with the new behavior in order to illustrate the impact of the change.


#### <a name="commit-footer"></a>Commit Message Footer

The footer can contain information about breaking changes and deprecations and is also the place to reference GitHub issues and other PRs that this commit closes or is related to.
For example:

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

Breaking Change section should start with the phrase "BREAKING CHANGE: " followed by a summary of the breaking change, a blank line, and a detailed description of the breaking change that also includes migration instructions.

Similarly, a Deprecation section should start with "DEPRECATED: " followed by a short description of what is deprecated, a blank line, and a detailed description of the deprecation that also mentions the recommended update path.

## Any contributions you make will be under the MIT License
In short, when you submit code changes, your submissions are understood to be under the same [MIT License](https://choosealicense.com/licenses/mit/) that covers the project. Feel free to contact the maintainers if that's a concern.

## Report bugs using Github's [issues](https://github.com/SpaiR/imgui-java/issues)
We use GitHub issues to track public bugs. Report a bug by [opening a new issue](); it's that easy!

## Write bug reports with detail, background, and sample code
**Great Bug Reports** tend to have:

- A quick summary and/or background
- Steps to reproduce
  - Be specific!
  - Give sample code if you can.
- What you expected would happen
- What actually happens
- Notes (possibly including why you think this might be happening, or stuff you tried that didn't work)

People *love* thorough bug reports. I'm not even kidding.

## Use a Consistent Coding Style
You can run `./gradlew check` to verify your changes beforehand.
