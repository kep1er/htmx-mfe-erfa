# CODEX Working Agreement

## Purpose
This repository is an example webshop monorepo used to demonstrate:
- Spring Boot SSR with Thymeleaf fragments
- htmx-driven interactions
- shared native Web Components built with Lit
- Nginx as frontend shell/static server/reverse proxy

## Core Rules (Always Follow)
1. Read these files before making changes:
    - `docs/ARCHITECTURE.md`
    - `docs/DECISIONS.md`
    - `docs/SESSION_LOG.md`
    - `docs/TODO.md`

2. Start each session with:
    - a short plan
    - assumptions (if any)

3. Implement only the requested scope.
    - Do not add extra features.
    - Do not jump ahead to the next step.

4. Keep the project runnable after each step.

5. Prefer small, incremental changes over broad refactors.

6. Do not change stable contracts without documenting the reason in `docs/DECISIONS.md`.
   Stable contracts include:
    - ports
    - route ownership by service
    - nginx proxy paths
    - htmx target IDs / fragment IDs (once introduced)

7. If something is unspecified:
    - choose the simplest default
    - document it in `docs/DECISIONS.md`

8. Preserve the existing folder structure unless a change is required.

9. Prefer the simplest toolchain.
    - Avoid introducing extra monorepo tooling unless necessary.
    - `packages/ui-components` should remain a standalone package unless workspaces are clearly needed.

10. Do not refactor unrelated code.

## Technology Intent
- Monorepo with 2 Spring Boot apps + 1 Nginx server
- htmx for partial page updates
- Thymeleaf for server-rendered HTML fragments
- Lit for shared native Web Components
- Tailwind CSS for styling
- PostgreSQL + Flyway for persistence (catalog first)
- Demo-focused implementation (not production-hardening yet)

## Test Execution Rule
- Before running E2E tests, verify the stack is reachable (preflight).
- If the stack is not running, do not execute E2E tests. Instead, document the required startup commands.
- E2E tests should fail fast with a clear message when prerequisites are not met.

## Documentation Updates Required After Each Session
Update:
- `docs/SESSION_LOG.md` (what changed, assumptions, issues)
- `docs/TODO.md` (completed items + next small steps)
- `docs/DECISIONS.md` (only if a design/technical choice was made)

## Git / Commit Rules
- Default: **one prompt/task = one commit**
- Use Conventional Commit messages with these types by default: `feat`, `fix`, `chore`, `test`
- Commit only after the scoped task is runnable, or after documenting exactly why verification could not be completed
- Do not bundle unrelated changes into the same commit
- At the end of each session, include the commit hash(es) in the handoff summary
- If git is unavailable in the environment, provide exact commit command(s) and message(s) to run manually

## Codex Handoff Artifact (Required)
At the end of every prompt/task:
1) Create or update a handoff file under `docs/codex/` named:
   `YYYY-MM-DD_<slug>.md`
2) Use the exact delimiter format:
   - `[CODEX HANDOFF START]`
   - `[CODEX HANDOFF END]`
3) Required sections inside the handoff block (in order):
   - Session / Goal
   - What changed
   - Commands to run (build/run/test)
   - Verification performed (what was actually run)
   - Commit hash(es)
   - Working tree status
   - Changed files
   - Known issues / follow-ups
   - Suggested next prompt (single step)
4) Keep handoff blocks easy to copy (plain Markdown, no nested structures).
5) Start from `docs/codex/TEMPLATE.md` unless the user asks for a custom format.

## Clean Working Tree Rule (Required)
- Before commit, run `git status` and confirm only scoped task files are staged.
- Do not include unrelated changes in the commit.
- After commit, working tree should be clean. If unrelated pre-existing changes remain, leave them uncommitted and list them in the handoff.

## Standard Chat Header (Required)
Final chat response must use this order:

[USER NOTES START]
- optional user notes / environment observations
[USER NOTES END]

[CODEX HANDOFF START]
Session: YYYY-MM-DD - <slug>
Goal:
- ...

What changed:
- ...

Commands to run:
- ...

Verification performed:
- ...

Commit hash(es):
- ...

Working tree status:
- ...

Changed files:
- ...

Known issues / follow-ups:
- ...

Suggested next prompt:
- ...
[CODEX HANDOFF END]
