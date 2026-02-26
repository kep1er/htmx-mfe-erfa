# CODEX Working Agreement

## Purpose

This repository is an example webshop monorepo used to demonstrate:

- Spring Boot SSR with Thymeleaf fragments
- htmx-driven interactions
- shared native Web Components built with Lit
- Nginx as frontend shell/static server/reverse proxy

## Core Rules (Always Follow)

1. Always read these files before making changes:
    - `docs/PROJECT_STATE.md`
    - `docs/TODO.md`

2. Read these additional files only when needed:
    - `docs/SESSION_LOG.md` when there is uncertainty, conflicting context, or user request
    - `docs/ARCHITECTURE.md` when deeper architecture detail is required
    - `docs/DECISIONS.md` when validating/changing prior technical decisions

3. Start each session with:
    - a short plan
    - assumptions (if any)

4. Implement only the requested scope.
    - Do not add extra features.
    - Do not jump ahead to the next step.

5. Keep the project runnable after each step.

6. Prefer small, incremental changes over broad refactors.

7. Do not change stable contracts without documenting the reason in `docs/DECISIONS.md`.
   Stable contracts include:
    - ports
    - route ownership by service
    - nginx proxy paths
    - htmx target IDs / fragment IDs (once introduced)

8. If something is unspecified:
    - choose the simplest default
    - document it in `docs/DECISIONS.md`

9. Preserve the existing folder structure unless a change is required.

10. Prefer the simplest toolchain.
    - Avoid introducing extra monorepo tooling unless necessary.
    - `packages/ui-components` should remain a standalone package unless workspaces are clearly needed.

11. Do not refactor unrelated code.

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

- `docs/PROJECT_STATE.md` (compact current-state snapshot)
- `docs/TODO.md` (completed items + next small steps)
- `docs/SESSION_LOG.md` (only when uncertainty, conflicts, or user request require a full narrative log)
- `docs/DECISIONS.md` (only if a design/technical choice was made)

## Git / Commit Rules

- Default: **one prompt/task = one commit**
- Use Conventional Commit messages with these types by default: `feat`, `fix`, `chore`, `test`
- Commit only after the scoped task is runnable, or after documenting exactly why verification could not be completed
- Do not bundle unrelated changes into the same commit
- At the end of each session, include the commit hash(es) in the handoff summary
- If git is unavailable in the environment, provide exact commit command(s) and message(s) to run manually

## .gitignore Discipline (Required)

Before staging/committing any changes:

1) Read `.gitignore` and ensure no ignored files are staged.
2) Run:
    - `git status --short`
    - `git status --ignored --short`
3) If a generated/build output is being committed, either:
    - update `.gitignore` to ignore it, and
    - if it was already tracked, stop tracking it with `git rm --cached <path>` (keeps the file locally).
4) When introducing a new tool that produces build output (Tailwind, Playwright, Lit build, etc.), proactively propose
   `.gitignore` updates as part of the task.
5) Keep `.run/` committed (do not add it to `.gitignore`), so teammates can run configs easily.

## Codex Handoff Copy (Required)

At end of every task:

1) Output the final handoff in chat wrapped with these exact delimiters:
    - `[HANDOFF START]`
    - `[HANDOFF END]`

2) Use this exact section order:
    - `Session: YYYY-MM-DD - <slug>`
    - `Goal:`
    - `What changed:`
    - `Commands:`
    - `Verification:`
    - `Commit:` (hash + message) OR `No commit`
    - `Files in commit:`
    - `Notes:` (must never include instructions; only results)
        - `Unrelated local changes:` list file names, or `None`

3) Overwrite `docs/codex/LATEST.md` with the exact same handoff text as chat (including delimiters and section order).
    - This is required even if the task fails, is blocked, or tests cannot run.

4) Use plain text paths only (no local hyperlinks like `C:\...`).
5) Do not commit `docs/codex/LATEST.md`.

## Clean Working Tree Rule (Required)

- Before commit, run `git status` and confirm only scoped task files are staged.
- Do not include unrelated changes in the commit.
- After commit, working tree should be clean. If unrelated pre-existing changes remain, leave them uncommitted and list
  them in the handoff.
- Ensure that any docker containers started are shut down again.
