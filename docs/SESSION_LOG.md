# Session Log

## Session Template
### YYYY-MM-DD - Session N
**Goal**
- ...

**Implemented**
- ...

**Decisions / Assumptions**
- ...

**Known Issues / Follow-ups**
- ...

**Verification**
- Commands run:
    - ...
- Manual checks:
    - ...

**Suggested Next Step**
- ...

### 2026-02-25 - Session 1
**Goal**
- Bootstrap runnable first slice: 2 Spring Boot services, nginx shell/proxy, catalog DB baseline, Tailwind, and one Lit component.

**Implemented**
- Created `catalog-service` (Spring Boot 4.0.2, Thymeleaf, JPA, Flyway, PostgreSQL config, health routes, product fragment).
- Created `order-service` (Spring Boot 4.0.2, Thymeleaf, in-memory placeholder cart fragment, health routes).
- Added Flyway migration `V1__init_catalog.sql` with `products` schema and demo seed records.
- Added nginx config for static shell + proxy routes `/catalog/**` and `/orders/**`.
- Built shell `index.html` with htmx fragment loading and Lit component usage.
- Added Tailwind CLI config and built CSS output to nginx assets.
- Initialized `packages/ui-components` and built `shop-badge.js` into dist + nginx assets.
- Added Docker Compose for `postgres` and `nginx`.
- Rewrote README with exact bootstrap run commands and verification URLs.

**Decisions / Assumptions**
- Spring Boot pinned to `4.0.2`.
- Apps run locally via Maven for now; Compose only handles nginx/postgres.
- nginx proxies to local apps using `host.docker.internal`.
- `packages/ui-components` kept standalone (no npm workspaces).

**Known Issues / Follow-ups**
- No cart behavior implemented yet (placeholder fragment only).
- No app containers in Compose yet (intentionally deferred).
- Lit component currently imports Lit from CDN URL.

**Verification**
- Commands run:
    - `npm run build` in `packages/ui-components`
    - `npm install` in `web/tailwind`
    - `npm run build` in `web/tailwind`
- Manual checks:
    - Not executed in this session (services not started end-to-end here).

**Suggested Next Step**
- Implement first real catalog htmx vertical slice: `/catalog/fragments/products` filtering/search interaction from server-rendered Thymeleaf.

### 2026-02-25 - Session 2
**Goal**
- Add minimal Playwright E2E foundation and enforce commit-per-prompt workflow rules.

**Implemented**
- Updated `docs/CODEX_WORKING_AGREEMENT.md` Git rules with explicit one-prompt-one-commit + conventional commit requirements.
- Added standalone Playwright package under `tests/e2e` (no npm workspaces).
- Added Playwright config (`playwright.config.ts`) with Chromium project and HTML report output.
- Added smoke tests for:
  - shell title
  - `shop-badge[label="Bootstrap Step 1"]`
  - dynamic replacement of loading placeholders for `#catalog-health`, `#order-health`, and catalog products fragment area
  - direct HTTP status checks for `/catalog/health` and `/orders/health`
- Updated `README.md` with exact Playwright commands for Windows and cross-platform usage.

**Decisions / Assumptions**
- Keep E2E runner isolated in `tests/e2e` to avoid monorepo tooling expansion.
- Target Chromium only for initial smoke coverage.
- Smoke tests assume stack is already running at `http://localhost:8080`.

**Known Issues / Follow-ups**
- End-to-end execution depends on local runtime prerequisites (Maven services + nginx/postgres).

**Verification**
- Commands run:
    - `npm install` in `tests/e2e`
    - `npm run install:browsers` in `tests/e2e`
    - `npm test -- --list` in `tests/e2e`
- Manual checks:
    - Not executed in this session (browser-based validation depends on running services).

**Suggested Next Step**
- Add one CI workflow job that starts the local stack and runs `tests/e2e` smoke tests headlessly.

### 2026-02-25 - Session 3
**Goal**
- Improve Playwright E2E reliability with a fast preflight stack check.

**Implemented**
- Added `tests/e2e/global-setup.ts` to probe `http://localhost:8080` with retry (~15s total).
- Wired `globalSetup` into `tests/e2e/playwright.config.ts`.
- Added fail-fast error message with exact startup commands:
  - `docker compose up -d postgres nginx`
  - `cd apps/catalog-service && mvn spring-boot:run`
  - `cd apps/order-service && mvn spring-boot:run`
- Updated `README.md` E2E section with a short "Prerequisites / Start Stack" snippet.

**Decisions / Assumptions**
- Preflight checks only nginx shell reachability at `http://localhost:8080`; deeper route behavior remains covered by existing smoke tests.
- Retry window set to 15 seconds to stay within the requested 10-20 second range.

**Known Issues / Follow-ups**
- If stack is stopped, test run exits before executing specs (intentional behavior).

**Verification**
- Commands run:
    - `npm test` in `tests/e2e`
- Manual checks:
    - Confirmed fast failure and error text includes startup commands when stack is not running.

**Suggested Next Step**
- Add a single helper script to start the stack and then run Playwright smoke tests.

### 2026-02-25 - Session 4
**Goal**
- Containerize both Spring Boot apps and enable a fully self-contained local E2E flow.

**Implemented**
- Added multi-stage Dockerfiles for:
  - `apps/catalog-service/Dockerfile`
  - `apps/order-service/Dockerfile`
- Added app-level `.dockerignore` files to keep Docker build contexts small.
- Updated `docker-compose.yml` to run full stack:
  - `postgres`
  - `catalog-service`
  - `order-service`
  - `nginx`
- Added docker-oriented nginx config `web/nginx/nginx.docker.conf` and switched compose to mount it.
- Preserved host-local nginx config (`web/nginx/nginx.conf`) for non-compose app mode.
- Added Node-based one-command E2E orchestrator:
  - `tests/e2e/scripts/e2e-all.mjs`
  - `npm run e2e:all`
  - `npm run e2e:all:down`
- Updated README and architecture/decision/todo docs for compose-based local runtime.

**Decisions / Assumptions**
- Keep published host ports (`8081`, `8082`) for easier local inspection while still using internal service-name routing from nginx.
- `e2e:all` leaves stack running by default; explicit `--down` variant handles teardown.

**Known Issues / Follow-ups**
- Existing unrelated local modification in `docs/CODEX_WORKING_AGREEMENT.md` was intentionally left out of this task commit.

**Verification**
- Commands run:
    - `docker compose up -d --build`
    - `cd tests/e2e && npm run e2e:all`
    - `docker compose ps`
- Manual checks:
    - Compose services confirmed running on stable ports.
    - Playwright smoke tests passed against compose-backed nginx.

**Suggested Next Step**
- Add container healthchecks for `catalog-service` and `order-service` and tighten compose dependency readiness.

### 2026-02-25 - Session 5
**Goal**
- Introduce a repository handoff artifact format so each session summary is saved and easy to copy.

**Implemented**
- Updated `docs/CODEX_WORKING_AGREEMENT.md` with:
  - required handoff filename pattern `docs/codex/YYYY-MM-DD_<slug>.md`
  - required delimiter format `[CODEX HANDOFF START]` / `[CODEX HANDOFF END]`
  - required section order
  - clean working tree rule before/after commit
  - required standard chat header with `[USER NOTES START] ... [USER NOTES END]`
- Added `docs/codex/TEMPLATE.md` with the standardized copy-ready handoff structure.
- Updated `docs/TODO.md` to track completion and next small steps for handoff adoption.

**Decisions / Assumptions**
- Keep handoff format minimal Markdown-only for copy/paste reliability.
- Do not add feature/code changes in this session.

**Known Issues / Follow-ups**
- None in scope; this session was documentation-only.

**Verification**
- Commands run:
    - `git status --short` (pre-commit scoping check)
- Manual checks:
    - Confirmed new template path and agreement sections are present and aligned.

**Suggested Next Step**
- Add one dated handoff file in `docs/codex/` for the next feature prompt and enforce using the template.

### 2026-02-25 - Session 6
**Goal**
- Backfill the missing dated Codex handoff artifact file and harden rules so it cannot be skipped.

**Implemented**
- Backfilled `docs/codex/2026-02-25_codex-handoff-artifact-rules.md` with the prior session handoff content using required delimiters.
- Updated `docs/CODEX_WORKING_AGREEMENT.md` to enforce:
  - missing dated handoff file means task is incomplete and must not be committed
  - required `Handoff file: <path>` line in final handoff block
- Updated `docs/codex/TEMPLATE.md` to include `Handoff file: ...`.
- Updated `docs/TODO.md` for completion and next-step tracking.

**Decisions / Assumptions**
- Kept this session documentation-only (no feature/runtime changes).

**Known Issues / Follow-ups**
- None in scope.

**Verification**
- Commands run:
    - `git status --short` (pre-commit scoping check)
- Manual checks:
    - Confirmed backfilled file exists at the required dated path and uses required delimiters.

**Suggested Next Step**
- Enforce a per-session checklist item in prompts: create handoff file before commit and verify path in final response.

### 2026-02-25 - Session 7
**Goal**
- Simplify Codex handoff artifacts to one always-updated file: `docs/codex/LATEST.md`.

**Implemented**
- Replaced handoff guidance in `docs/CODEX_WORKING_AGREEMENT.md` with single-file workflow (`docs/codex/LATEST.md`).
- Updated required handoff/header references to `Handoff file: docs/codex/LATEST.md`.
- Updated `docs/codex/TEMPLATE.md` to match the LATEST file format (no dated filename).
- Updated `docs/codex/LATEST.md` with a short placeholder handoff block using required delimiters.
- Updated `docs/TODO.md` to remove dated-file next-step wording.

**Decisions / Assumptions**
- Keep one canonical handoff file to reduce operational confusion and missed artifacts.

**Known Issues / Follow-ups**
- None in scope (documentation-only change).

**Verification**
- Commands run:
    - `git status --short` (scoping check before commit)
- Manual checks:
    - Confirmed no dated filename requirement remains in handoff agreement/template.

**Suggested Next Step**
- Keep `docs/codex/LATEST.md` updated at the end of each prompt and mirror the same block in final chat response.

### 2026-02-25 - Session 8
**Goal**
- Make `docs/codex/LATEST.md` the canonical copy source and add strict staged-file verification before commit.

**Implemented**
- Updated `docs/CODEX_WORKING_AGREEMENT.md` to require `docs/codex/LATEST.md` to contain both:
  - `[USER NOTES START] ... [USER NOTES END]`
  - `[CODEX HANDOFF START] ... [CODEX HANDOFF END]`
- Added explicit requirement to start from `docs/codex/TEMPLATE.md`.
- Added strict pre-commit checks:
  - `git diff --cached --name-only` must include `docs/codex/LATEST.md`
  - `docs/codex/LATEST.md` must contain both delimiter blocks
- Overwrote `docs/codex/LATEST.md` with the prior session’s full final chat payload (USER NOTES + CODEX HANDOFF).
- Updated `docs/codex/TEMPLATE.md` and `docs/TODO.md` to align with canonical LATEST workflow.

**Decisions / Assumptions**
- Keep this session documentation-only and avoid feature/runtime changes.

**Known Issues / Follow-ups**
- None in scope.

**Verification**
- Commands run:
    - `git diff --cached --name-only`
    - `Select-String` checks for delimiter markers in `docs/codex/LATEST.md`
- Manual checks:
    - Confirmed `docs/codex/LATEST.md` now contains both USER NOTES and CODEX HANDOFF blocks.

**Suggested Next Step**
- Add a simple pre-commit helper script to automate the LATEST.md staged-file + delimiter verification.

### 2026-02-25 - Session 9
**Goal**
- Simplify handoff workflow so `docs/codex/LATEST.md` is a local copy helper and is not committed.

**Implemented**
- Updated `.gitignore` to ignore `docs/codex/LATEST.md` while keeping `docs/codex/` directory tracked.
- Simplified `docs/CODEX_WORKING_AGREEMENT.md`:
  - removed Standard Chat Header section
  - removed references to `docs/codex/TEMPLATE.md`
  - removed staged/pre-commit verification rules for `LATEST.md`
  - added one clear rule: paste handoff in chat and overwrite `docs/codex/LATEST.md` with same content, but do not commit it
- Removed `docs/codex/TEMPLATE.md` (no longer referenced).
- Updated `docs/TODO.md` for the simplified local-copy workflow.

**Decisions / Assumptions**
- Keep workflow minimal: one local handoff copy file plus chat output, without template or commit gating.

**Known Issues / Follow-ups**
- `docs/codex/LATEST.md` remains a local working file and should stay out of commits.

**Verification**
- Commands run:
    - `git status --short` (scope checks before/after staging)
- Manual checks:
    - Confirmed `docs/CODEX_WORKING_AGREEMENT.md` no longer contains Standard Chat Header or LATEST staged-file verification rules.

**Suggested Next Step**
- Add a tiny convenience script to write the final handoff text to `docs/codex/LATEST.md` after each task.

### 2026-02-25 - Session 10
**Goal**
- Tighten handoff rules with fixed section order and require writing `docs/codex/LATEST.md` even on task failure.

**Implemented**
- Replaced the `Codex Handoff Copy (Required)` section in `docs/CODEX_WORKING_AGREEMENT.md` with a stricter version that:
  - enforces `[HANDOFF START]` / `[HANDOFF END]`
  - defines exact section order
  - requires overwriting `docs/codex/LATEST.md` with the same handoff text even on failure/blockers
  - keeps plain text paths only
  - keeps `docs/codex/LATEST.md` out of commits
- Updated `docs/TODO.md` to track this documentation hardening.

**Decisions / Assumptions**
- Keep this as a docs-only change; no feature/runtime changes.

**Known Issues / Follow-ups**
- `docs/codex/LATEST.md` remains a local working file and should stay uncommitted.

**Verification**
- Commands run:
    - `git status --short`
    - `git diff --cached --name-only`
- Manual checks:
    - Confirmed handoff rule now defines a strict section order and failure-path requirement.

**Suggested Next Step**
- Add an optional local script to auto-generate the handoff skeleton with the required section order.
