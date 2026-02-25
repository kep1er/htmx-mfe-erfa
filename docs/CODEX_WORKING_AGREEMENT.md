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

## Documentation Updates Required After Each Session
Update:
- `docs/SESSION_LOG.md` (what changed, assumptions, issues)
- `docs/TODO.md` (completed items + next small steps)
- `docs/DECISIONS.md` (only if a design/technical choice was made)

## Git / Commit Rules
- Default commit granularity: **one prompt/task = one commit**
- If a task contains multiple independent runnable milestones, split into multiple commits
- Commit only after completing the scoped task and performing the requested verification steps (or documenting why verification could not be run)
- Use clear commit messages (prefer Conventional Commits), e.g.:
   - `feat(catalog): add htmx product filter fragment`
   - `chore(web): add nginx proxy route for catalog fragments`
   - `test(e2e): add playwright smoke test for shell load`
- Do not bundle unrelated changes into the same commit
- At the end of each session, include the commit hash(es) in the handoff summary (if commits were created)
- If git is unavailable in the environment, provide the exact commit command(s) and message(s) to run manually

## Response / Handoff Format (at end of each session)
Provide:
- what was done
- files changed
- exact run/test commands
- manual verification steps
- suggested next prompt (single small step only)