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

### 2026-02-26 - Session 11
**Goal**
- Enable a faster local dev feedback loop ("HMR-like") for both Spring Boot apps using DevTools and a `dev` profile.

**Implemented**
- Added `spring-boot-devtools` (runtime, optional) to:
  - `apps/catalog-service/pom.xml`
  - `apps/order-service/pom.xml`
- Added `application-dev.properties` in both apps with:
  - `spring.thymeleaf.cache=false`
  - `spring.devtools.restart.enabled=true`
  - `spring.devtools.livereload.enabled=true`
- Removed `spring.thymeleaf.cache=false` from default `application.properties` in both apps to keep defaults production-safe.
- Updated `README.md` with:
  - dev-mode run commands using `-Dspring-boot.run.profiles=dev`
  - IntelliJ auto-restart settings
  - explicit note that Docker Compose + Playwright remains the E2E path.

**Decisions / Assumptions**
- Keep DevTools behavior profile-scoped (`dev`) rather than globally enabled.
- Do not change E2E/runtime architecture in this task.

**Known Issues / Follow-ups**
- Existing unrelated local changes were present before this task and were left uncommitted.

**Verification**
- Commands run:
    - `git status --short`
    - `Get-Content apps/catalog-service/src/main/resources/application.properties`
    - `Get-Content apps/order-service/src/main/resources/application.properties`
    - `Get-Content apps/catalog-service/src/main/resources/application-dev.properties`
    - `Get-Content apps/order-service/src/main/resources/application-dev.properties`
- Manual checks:
    - Confirmed dev profile files exist with Thymeleaf cache disabled and DevTools restart/livereload enabled.
    - Confirmed default app properties no longer force Thymeleaf cache off.

**Suggested Next Step**
- Add one tiny script/command aliases to start both services in `dev` profile together for faster local startup.

### 2026-02-26 - Session 12
**Goal**
- Add compose profiles for infra-only local dev (`postgres` + nginx) without breaking full containerized E2E mode.

**Implemented**
- Updated `docker-compose.yml` with profile-based runtime modes:
  - `infra`: `postgres` + `nginx-dev`
  - `full`: `postgres` + `catalog-service` + `order-service` + `nginx`
- Added `web/nginx/nginx.dev.conf` for host-run Spring apps:
  - `/catalog/**` -> `http://host.docker.internal:8081`
  - `/orders/**` -> `http://host.docker.internal:8082`
- Kept `web/nginx/nginx.docker.conf` unchanged for full containerized mode (service-name proxies).
- Updated `tests/e2e/scripts/e2e-all.mjs` to bootstrap stack with `docker compose --profile full up -d --build`.
- Updated `README.md` with exact commands for:
  - dev/infra mode (`docker compose --profile infra up -d` + host Maven runs with `dev` profile)
  - full/E2E mode (`docker compose --profile full up -d --build` + Playwright run commands)

**Decisions / Assumptions**
- `nginx` service name remains the full-mode proxy; `nginx-dev` is infra-only to keep proxy configs explicit.
- `postgres` participates in both profiles to avoid duplicate DB service definitions.

**Known Issues / Follow-ups**
- Existing unrelated local changes were present before this task and were left uncommitted.

**Verification**
- Commands run:
    - `docker compose --profile infra config --services`
    - `docker compose --profile full config --services`
    - `git status --short`
- Manual checks:
    - Confirmed `infra` includes only `postgres` and `nginx-dev`.
    - Confirmed `full` includes `postgres`, both app containers, and `nginx`.

**Suggested Next Step**
- Add container healthchecks for `catalog-service` and `order-service`, then switch nginx `depends_on` to `service_healthy`.

### 2026-02-26 - Session 13
**Goal**
- Implement F-001 add-to-cart cross-service flow via htmx between catalog and order-service.

**Implemented**
- Added `POST /orders/cart/items` in `order-service` to accept `sku` and `qty` (`qty` default `1`) and return updated cart fragment.
- Added in-memory cart service `InMemoryCartService` with SKU-based line aggregation and item count snapshot.
- Updated order cart fragment to render:
  - item count
  - line items (`sku x qty`)
  - empty-state text when cart has no lines
- Updated catalog products fragment to render `Add to cart` buttons/forms that post to `/orders/cart/items` and target `#cart-fragment`.
- Added `id="cart-fragment"` in shell HTML so cart updates can be targeted from catalog htmx interactions.
- Extended Playwright smoke test:
  - clicks first `Add to cart`
  - asserts cart fragment content changes afterward.
- Updated Playwright global setup startup hint to use `docker compose --profile full up -d --build`.

**Decisions / Assumptions**
- Cart remains in-memory for this step; no persistence added.
- Cart lines display SKU + qty only (no pricing math yet) to keep scope minimal.

**Known Issues / Follow-ups**
- Existing unrelated local changes were present before this task and were left uncommitted.

**Verification**
- Commands run:
    - `mvn -DskipTests compile` (catalog-service)
    - `mvn -DskipTests compile` (order-service)
    - `cd tests/e2e && npm run e2e:all`
- Manual checks:
    - Playwright passed with new add-to-cart cart-change assertion in full profile mode.

**Suggested Next Step**
- Add cart line remove/decrement endpoint and htmx action in cart fragment.

### 2026-02-26 - Session 14
**Goal**
- Reconcile recent manual dev workflow changes and add Tailwind watcher to compose `infra` profile.

**Implemented**
- Reconciled manual dev changes into repo state:
  - `web/nginx/nginx.dev.conf` now serves `/assets/` with no-cache headers for local iteration
  - both app `application-dev.properties` use filesystem template prefix (`spring.thymeleaf.prefix=file:src/main/resources/templates/`)
  - README now documents IntelliJ run configuration working directories per service app folder
- Kept related manual UI/template sync updates together (cart fragment outerHTML swap alignment and regenerated Tailwind CSS output).
- Added `tailwind` service to `docker-compose.yml` under `infra` profile:
  - image `node:20-alpine`
  - command `npm ci && npm run watch -- --poll`
  - repo bind mount plus named volume `tailwind-node-modules`
- Updated README dev instructions to clarify `docker compose --profile infra up -d` now starts `postgres`, `nginx-dev`, and `tailwind` watcher.

**Decisions / Assumptions**
- Keep Tailwind watcher as compose-managed infra in dev mode only (`infra` profile).
- Use polling in watcher command for cross-platform file change reliability.

**Known Issues / Follow-ups**
- Existing unrelated local changes were present before this task and were left uncommitted.

**Verification**
- Commands run:
    - `git status --short`
    - `docker compose --profile infra config --services`
- Manual checks:
    - Confirmed infra service list includes `postgres`, `tailwind`, and `nginx-dev`.

**Suggested Next Step**
- Add `docker compose --profile infra logs -f tailwind` troubleshooting snippet to README.

### 2026-02-26 - Session 15
**Goal**
- Introduce the catalog `MagicShopItem` persistence domain with Flyway V2 seed data and minimal JSON verification endpoints.

**Implemented**
- Added catalog domain model under `apps/catalog-service/src/main/java/com/example/catalog/magic`:
  - `MagicShopItem` entity (`magic_shop_items`)
  - `Rarity` enum with lowercase string storage via `RarityConverter`
  - embedded value objects `Dimensions` and `Charges`
  - element-collection mappings for tags/effects/limitations/instructions/warnings/materials/related item IDs
- Added `MagicShopItemRepository` and `MagicShopItemService` with:
  - `listAll()`
  - `getById(id)`
- Added JSON endpoints in `MagicShopItemApiController`:
  - `GET /catalog/api/items` (summary projection)
  - `GET /catalog/api/items/{id}` (full detail projection)
- Added Flyway migration `V2__magic_shop_items.sql`:
  - creates `magic_shop_items` and all required element-collection tables
  - applies non-negative constraints for numeric inventory/size/charges fields
  - seeds 6 magical items including full rich sample for `itm_001`

**Decisions / Assumptions**
- Stored rarity values as lowercase strings (`common|uncommon|rare|legendary`) to match schema contract.
- Used relational collection tables instead of JSONB for arrays.

**Known Issues / Follow-ups**
- Local `mvn` executable is unavailable in this shell; compile verification was run via Docker build.
- Ports `8081`/`8082` were already occupied by local processes, so runtime API verification used temporary container port `18081`.

**Verification**
- Commands run:
    - `docker compose --profile full build catalog-service`
    - `docker run -d --name webshop-catalog-verify --network webshop-net -p 18081:8081 -e SPRING_DATASOURCE_URL=jdbc:postgresql://postgres:5432/webshop -e SPRING_DATASOURCE_USERNAME=webshop -e SPRING_DATASOURCE_PASSWORD=webshop -e SPRING_FLYWAY_ENABLED=true -e SPRING_JPA_HIBERNATE_DDL_AUTO=validate htmx-mfe-erfa-catalog-service`
    - `Invoke-RestMethod http://localhost:18081/catalog/api/items`
    - `Invoke-RestMethod http://localhost:18081/catalog/api/items/itm_001`
    - `docker rm -f webshop-catalog-verify`
- Manual checks:
    - Confirmed item list returns seeded records including `itm_001`.
    - Confirmed detail endpoint for `itm_001` returns rich fields (lore/effects/limitations/instructions/warnings/materials/dimensions/related/releasedAt/attunement/charges).

**Suggested Next Step**
- Build the catalog page/fragment wiring for magical items and adapt the existing add-to-cart form fields without breaking F-001 flow.

### 2026-02-26 - Session 16
**Goal**
- Switch catalog Thymeleaf fragments from legacy product model to `MagicShopItem`, add htmx-loaded rich item detail, and keep add-to-cart contract intact.

**Implemented**
- Updated `CatalogController` to load `/catalog/fragments/products` from `MagicShopItemService`.
- Added new detail fragment endpoint: `GET /catalog/fragments/items/{id}`.
- Updated catalog list fragment to render `MagicShopItem` fields (name/category/rarity/price/stock).
- Kept add-to-cart integration with `order-service` intact by posting `sku=item.id` and `qty=1` to `/orders/cart/items`.
- Added per-item "View details" htmx action targeting `#catalog-detail`.
- Added new Thymeleaf fragment template `fragments/item-detail.html` for rich detail sections (description/lore/effects/materials/dimensions/warnings), rendered conditionally when present.
- Updated nginx shell `index.html` with `#catalog-detail` container in catalog panel.
- Extended Playwright smoke test to open details for `itm_001` and assert detail content, while still validating cart updates.

**Decisions / Assumptions**
- Detail route stays under existing `/catalog/**` ownership as `/catalog/fragments/items/{id}`.
- Kept order-service contract unchanged (`sku` stays a string form field) and mapped it from `MagicShopItem.id`.

**Known Issues / Follow-ups**
- Unrelated local changes existed before this task (`docs/CODEX_WORKING_AGREEMENT.md`, `.run/`) and were intentionally left uncommitted.

**Verification**
- Commands run:
    - `docker compose --profile full up -d --build`
    - `cd tests/e2e && npm run e2e:all`
- Manual checks:
    - Playwright smoke suite passed, including new detail-view assertions and add-to-cart cart update assertion.

**Suggested Next Step**
- Add a minimal catalog filter/search fragment interaction (htmx query parameter) over `MagicShopItem` list results.

### 2026-02-26 - Session 17
**Goal**
- Add catalog search and filters (query + rarity + category) via htmx while preserving add-to-cart and detail interactions.

**Implemented**
- Added filtering support in `MagicShopItemRepository`:
  - `findFiltered(q, rarity, category)` query matching query text against name/category/tags and applying optional rarity/category filters.
  - `findDistinctCategories()` for category select options.
- Extended `MagicShopItemService` with:
  - `listFiltered(q, rarity, category)`
  - `listCategories()`
- Updated `/catalog/fragments/products` controller endpoint to accept `q`, `rarity`, `category` and pass:
  - filtered items
  - distinct categories
  - current filter values for selected/filled state
- Updated `fragments/products.html` to include:
  - htmx filter form (search input + rarity select + category select)
  - stable update target `#catalog-products`
  - empty-state text when no items match
  - preserved "View details" and add-to-cart behavior
- Updated `web/nginx/html/index.html` with stable container id `catalog-products`.
- Extended Playwright smoke test with filter assertions:
  - query "Weather" returns Pocket Weather Jar
  - nonsense query shows empty-state text
  - rarity "uncommon" keeps expected result
  - existing detail and add-to-cart assertions remain

**Decisions / Assumptions**
- Kept category source in existing products fragment render model instead of adding a new categories API endpoint.
- Kept routing contracts unchanged under `/catalog/**` and `/orders/**`.

**Known Issues / Follow-ups**
- Verification in full container mode is currently blocked on this machine because host port `8082` is already occupied by a local Java process.

**Verification**
- Commands run:
    - `docker compose --profile full up -d --build`
    - `cd tests/e2e && npm run e2e:all`
    - `Get-NetTCPConnection -LocalPort 8082 -State Listen`
    - `docker compose --profile full down`
- Manual checks:
    - Confirmed both verification commands failed for the same reason: bind error on `0.0.0.0:8082`.
    - Confirmed compose resources were shut down after verification attempts.

**Suggested Next Step**
- Resolve host port 8082 conflict for full profile runs (stop local process or make compose app ports internal-only if approved).

### 2026-02-26 - Session 18
**Goal**
- Fix catalog filter query runtime error (`lower(bytea)`) reported during local dev requests.

**Implemented**
- Updated `MagicShopItemRepository.findFiltered(...)` to accept a pre-built `qPattern` and compare with:
  - `lower(i.name) like :qPattern`
  - `lower(i.category) like :qPattern`
  - `lower(t) like :qPattern`
- Updated `MagicShopItemService.listFiltered(...)` to build a lowercase `%...%` pattern before repository call.
- Kept route contracts and filter behavior unchanged.

**Decisions / Assumptions**
- Treated this as a targeted bugfix only; no UI/contract refactors.

**Known Issues / Follow-ups**
- A separate local uncommitted change exists in `apps/catalog-service/src/main/resources/templates/fragments/products.html` (`hx-push-url`) and was intentionally left out of this fix commit.

**Verification**
- Commands run:
    - `docker compose --profile full build catalog-service`
    - `docker compose --profile full up -d postgres`
    - `docker run -d --name webshop-catalog-filter-fix --network webshop-net -p 18081:8081 -e SPRING_DATASOURCE_URL=jdbc:postgresql://postgres:5432/webshop -e SPRING_DATASOURCE_USERNAME=webshop -e SPRING_DATASOURCE_PASSWORD=webshop -e SPRING_FLYWAY_ENABLED=true -e SPRING_JPA_HIBERNATE_DDL_AUTO=validate htmx-mfe-erfa-catalog-service`
    - `Invoke-WebRequest http://localhost:18081/catalog/fragments/products?q=Weather`
    - `Invoke-WebRequest http://localhost:18081/catalog/fragments/products?q=zzzz-no-such-item`
    - `docker rm -f webshop-catalog-filter-fix`
    - `docker compose --profile full down`
- Manual checks:
    - Filtered fragment returns HTTP 200 for matching and empty queries.
    - Response contains expected filtered item text and empty-state text; no SQL error.

**Suggested Next Step**
- Re-run full `tests/e2e` once local host port `8082` is free.

### 2026-02-26 - Session 19
**Goal**
- Ensure rarity/category selection applies catalog filtering even when search query is empty.

**Implemented**
- Updated `apps/catalog-service/src/main/resources/templates/fragments/products.html` filter form trigger:
  - from `hx-trigger="keyup changed delay:300ms"`
  - to `hx-trigger="keyup changed delay:300ms, change"`
- This ensures select controls (`rarity`, `category`) trigger requests without requiring typing in `q`.
- Updated `tests/e2e/specs/smoke.spec.ts`:
  - added explicit response waits for rarity/category-only filter requests
  - kept and stabilized search assertions by dispatching `change` event after filling search input
  - preserved detail-view and add-to-cart checks.

**Decisions / Assumptions**
- No backend/filter-query changes required; issue was frontend htmx trigger coverage for select change events.

**Known Issues / Follow-ups**
- Unrelated local change in `docs/CODEX_WORKING_AGREEMENT.md` remains uncommitted.

**Verification**
- Commands run:
    - `docker compose --profile full up -d --build`
    - `cd tests/e2e && npm test`
- Manual checks:
    - Playwright passed (`2 passed`) including filter-only selection behavior.

**Suggested Next Step**
- Add a dedicated Playwright test file for catalog filtering permutations to keep smoke test concise.

### 2026-02-26 - Session 20
**Goal**
- Make compose `full` profile conflict-free by removing host publishing for app ports `8081`/`8082`.

**Implemented**
- Updated `docker-compose.yml` (full profile):
  - removed host port mappings for `catalog-service` (`8081:8081`)
  - removed host port mappings for `order-service` (`8082:8082`)
  - kept nginx on `8080` and postgres on `5432`.
- Updated `README.md`:
  - documented that `infra` and `full` cannot run simultaneously because both use host `8080`
  - added explicit `docker compose --profile infra down` step before full/E2E runs
  - documented that full mode app containers are internal-only and should be accessed via nginx on `8080`
  - clarified host `8081`/`8082` checks are for host-run apps (infra mode), not full mode.
- Updated `tests/e2e/scripts/e2e-all.mjs`:
  - added best-effort `docker compose --profile infra down` before `full up -d --build` to reduce port-8080 conflicts.

**Decisions / Assumptions**
- Keep route/proxy ownership unchanged; nginx in full mode still proxies to `catalog-service:8081` and `order-service:8082` on the internal docker network.

**Known Issues / Follow-ups**
- Unrelated local change in `docs/CODEX_WORKING_AGREEMENT.md` remains uncommitted.

**Verification**
- Commands run:
    - `docker compose --profile full up -d --build`
    - `cd tests/e2e && npm run e2e:all`
    - `docker compose --profile full down`
- Manual checks:
    - Full stack started without needing host ports `8081`/`8082`.
    - `e2e:all` completed successfully against nginx on `8080`.

**Suggested Next Step**
- Add a lightweight `docker compose --profile full config --services` check in docs/scripts for quick profile sanity validation.

### 2026-02-26 - Session 21
**Goal**
- Fix Playwright E2E timeout around catalog category filtering by using DOM-based waits/assertions and stable test hooks.

**Implemented**
- Updated `apps/catalog-service/src/main/resources/templates/fragments/products.html`:
  - added `data-testid="catalog-filter"` on the filter form
  - added `data-testid="catalog-item"` on each rendered catalog list item
  - added `data-testid="catalog-empty"` on the empty-state message.
- Updated `tests/e2e/specs/smoke.spec.ts`:
  - waits for filter UI to be loaded before interacting (`catalog-filter`)
  - waits for list readiness (`catalog-item` present or `catalog-empty` present)
  - removed brittle response URL waits for filter checks
  - validates rarity/category filtering via DOM outcomes
  - stabilized search interactions using typed key events to trigger htmx `keyup` filtering reliably.

**Decisions / Assumptions**
- Kept backend filtering behavior unchanged; this is a test reliability and test-hook-only change.
- Used existing category data and asserted category filtering via rendered item text instead of network interception.

**Known Issues / Follow-ups**
- Unrelated local change in `docs/CODEX_WORKING_AGREEMENT.md` remains uncommitted.

**Verification**
- Commands run:
    - `docker compose --profile full up -d --build`
    - `cd tests/e2e && npm run e2e:all`
    - `docker compose --profile full down`
- Manual checks:
    - Playwright smoke suite passed (`2 passed`) including category/rarity/search assertions and cart update flow.

**Suggested Next Step**
- Split catalog filter assertions into a dedicated Playwright spec file to keep smoke checks focused and fast.

### 2026-02-26 - Session 22
**Goal**
- Implement catalog-owned item summary fragment reuse in both catalog list and order cart, with cart lazy-loading summary per line and order-owned qty controls.

**Implemented**
- Added catalog fragment template `apps/catalog-service/src/main/resources/templates/fragments/item-summary.html`:
  - `summary` fragment with single root element
  - stable hooks `data-testid="item-summary"` and `data-item-id`
  - renders name/category/rarity/price/stock (+ image when present)
  - `summaryNotFound` fallback fragment for missing items.
- Added catalog endpoint `GET /catalog/fragments/item-summary/{id}`:
  - returns `item-summary :: summary` for existing item
  - returns HTTP 404 + `summaryNotFound` fragment when item is missing.
- Updated catalog products fragment to reuse the shared summary via `th:replace` and keep existing catalog actions (`View details`, `Add to cart`) unchanged.
- Extended order cart domain/actions:
  - `InMemoryCartService`: `increment`, `decrement` (remove on zero), `remove`
  - `OrderController`: new htmx endpoints
    - `POST /orders/cart/items/{sku}/increment`
    - `POST /orders/cart/items/{sku}/decrement`
    - `POST /orders/cart/items/{sku}/remove`
- Updated order cart fragment:
  - each cart line lazy-loads summary from `/catalog/fragments/item-summary/{sku}` (`hx-trigger="revealed"`, `hx-swap="outerHTML"`)
  - keeps order-owned cart controls for `+`, `-`, and `remove`, each swapping `#cart-fragment` via `outerHTML`.
- Updated Playwright smoke test:
  - add-to-cart now targets `itm_001` explicitly
  - verifies cart includes loaded shared summary (`data-testid="item-summary"` with `data-item-id="itm_001"` and item name text).

**Decisions / Assumptions**
- Used Option A by design: one catalog summary request per visible cart line.
- Missing item summaries return HTTP 404 plus a small fragment to keep cart rendering resilient.

**Known Issues / Follow-ups**
- Unrelated local changes in `docs/CODEX_WORKING_AGREEMENT.md` may exist and should remain outside this task commit if present.

**Verification**
- Commands run:
    - `docker compose --profile full up -d --build`
    - `cd tests/e2e && npm run e2e:all`
    - `docker compose --profile full down`
- Manual checks:
    - Playwright smoke suite passed with cart summary lazy-load assertion.

**Suggested Next Step**
- Add an optional batch summary endpoint in catalog for later optimization once cart lines grow.

### 2026-02-26 - Session 23
**Goal**
- Add service healthchecks for `catalog-service` and `order-service` and tighten compose dependency readiness.

**Implemented**
- Updated `docker-compose.yml`:
  - added `healthcheck` for `catalog-service` probing `GET /catalog/health` on `localhost:8081`
  - added `healthcheck` for `order-service` probing `GET /orders/health` on `localhost:8082`
  - changed `nginx` `depends_on` conditions from `service_started` to `service_healthy` for both app services
- Kept profile/port ownership unchanged (`full` runtime still routes through nginx on `8080`).

**Decisions / Assumptions**
- Used bash `/dev/tcp` HTTP probes in compose healthchecks because the runtime image has `bash` but no `curl`/`wget`.
- Reused existing JSON health endpoints as readiness signals.

**Known Issues / Follow-ups**
- Unrelated local change in `docs/CODEX_WORKING_AGREEMENT.md` may still exist and remains outside this task scope.

**Verification**
- Commands run:
  - `docker compose --profile full config --services`
  - `docker compose --profile full config`
  - `docker compose --profile infra down`
  - `docker compose --profile full up -d --build`
  - `docker compose --profile full ps`
  - `(Invoke-WebRequest -UseBasicParsing http://localhost:8080/catalog/health).StatusCode`
  - `(Invoke-WebRequest -UseBasicParsing http://localhost:8080/orders/health).StatusCode`
- Manual checks:
  - Confirmed `catalog-service` and `order-service` report `healthy` in compose status.
  - Confirmed nginx starts only after both app services reach `healthy`.
  - Confirmed both health endpoints return HTTP `200` through nginx.

**Suggested Next Step**
- Add a one-command helper for host-run Spring dev mode (starts both services with `dev` profile).

### 2026-02-26 - Session 24
**Goal**
- Refactor nginx shell into shared top navigation + main content area, move health UI to `/health`, and keep service-owned fragment responsibilities intact.

**Implemented**
- Reworked shell layout in `web/nginx/html/index.html`:
  - replaced old two-panel service boxes with persistent top nav and `<main id="app-main">`
  - added top bar controls (logo, search, rarity/category pills, clear action, cart link, health link)
  - kept Lit badge in header
- Added shell health view fragment `web/nginx/html/fragments/health-view.html` that loads:
  - `/catalog/fragments/health`
  - `/orders/fragments/health`
- Implemented shell soft-navigation + route-aware initial load in `web/nginx/assets/js/app.js`:
  - `/` -> catalog fragment
  - `/cart` -> cart fragment
  - `/health` -> shell health fragment
  - top-bar search/pills always request `/catalog/fragments/products` into `#app-main` and normalize browser URL back to `/` with query params
- Updated catalog products fragment (`apps/catalog-service/.../fragments/products.html`):
  - removed embedded filter form in favor of shell top-bar controls
  - moved detail container into fragment (`#catalog-detail`) so detail loading works when fragment is rendered in `#app-main`
  - changed add-to-cart form swap behavior to `hx-swap="none"` so cart writes still work from catalog view without requiring on-page cart container
- Updated Playwright smoke test (`tests/e2e/specs/smoke.spec.ts`) for new shell UX:
  - validates default catalog load on `/`
  - validates top-bar filtering and detail load
  - validates cart navigation and add-to-cart persistence with cart item summary
  - validates health view navigation via top-bar link and panel loading

**Decisions / Assumptions**
- Kept backend endpoint contracts unchanged; routing changes are shell-side only.
- Kept catalog-owned summary fragment and order-owned cart composition unchanged in ownership.

**Known Issues / Follow-ups**
- Existing pre-session local modifications from Session 23 (`docker-compose.yml`, docs memory files) were already present and remained in the working tree.

**Verification**
- Commands run:
  - `docker compose --profile full up -d --build`
  - `cd tests/e2e && npm run e2e:all` (first run failed once due a timing race around health-nav assertion)
  - `cd tests/e2e && npm run e2e:all` (second run passed after stabilizing top-bar trigger behavior)
  - `docker compose --profile full down`
- Manual checks:
  - Confirmed shell starts on catalog by default.
  - Confirmed cart and health views swap into `#app-main`.
  - Confirmed add-to-cart still results in cart summary rendering from catalog-owned summary fragment.

**Suggested Next Step**
- Add a one-command helper for host-run Spring dev mode (starts both services with `dev` profile).

### 2026-02-26 - Session 25
**Goal**
- Fix compose healthchecks by ensuring curl exists in Spring Boot runtime images while keeping compose app checks curl-based.

**Implemented**
- Updated `apps/catalog-service/Dockerfile` runtime stage:
  - installs curl via `apt-get`
- Updated `apps/order-service/Dockerfile` runtime stage:
  - installs curl via `apt-get`
- Kept `docker-compose.yml` app healthchecks curl-based:
  - `catalog-service`: `curl -fsS http://localhost:8081/catalog/health`
  - `order-service`: `curl -fsS http://localhost:8082/orders/health`

**Decisions / Assumptions**
- `eclipse-temurin:25-jre` runtime image supports apt package installation, so adding curl there is the smallest targeted fix.

**Known Issues / Follow-ups**
- None in scope.

**Verification**
- Commands run:
  - `docker compose --profile full up -d --build`
  - `docker compose --profile full ps`
  - `docker compose --profile full down`
- Manual checks:
  - Confirmed `catalog-service` and `order-service` reached `healthy` state using curl-based healthchecks.

**Suggested Next Step**
- Add a one-command helper for host-run Spring dev mode (starts both services with `dev` profile).
