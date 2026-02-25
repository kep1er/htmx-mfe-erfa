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
