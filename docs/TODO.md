# TODO

## Now (Current Step)
- [x] Bootstrap both Spring Boot applications (`catalog-service`, `order-service`)
- [x] Add minimal Thymeleaf setup in both apps
- [x] Add health endpoints/pages in both apps
- [x] Configure `catalog-service` with PostgreSQL + Flyway (schema + seed demo products)
- [x] Add Nginx config to serve `index.html` and proxy both services
- [x] Create frontend shell with htmx loaded
- [x] Add Tailwind setup (Node + Tailwind CLI)
- [x] Initialize `packages/ui-components` with Lit and one minimal demo component
- [x] Serve built ui-components JS via Nginx and use component once
- [x] Add `docker-compose.yml` for nginx + postgres (apps optional if simple)
- [x] Add `README.md` with exact run instructions
- [x] Update docs (`ARCHITECTURE`, `DECISIONS`, `SESSION_LOG`, `TODO`)
- [x] Add standalone Playwright package under `tests/e2e`
- [x] Add minimal Playwright config + smoke tests for shell and health endpoints
- [x] Add E2E run/install/report commands to `README.md`
- [x] Update `CODEX_WORKING_AGREEMENT.md` with commit-per-prompt Git workflow rules
- [x] Add Playwright preflight stack check (global setup with fast fail message)
- [x] Add README "Prerequisites / Start Stack" snippet for E2E
- [x] Containerize `catalog-service` and `order-service` with multi-stage Dockerfiles
- [x] Run full stack in `docker-compose.yml` (postgres + both services + nginx)
- [x] Add Node-based one-command E2E runner (`npm run e2e:all`)
- [x] Add Codex handoff artifact rules in `docs/CODEX_WORKING_AGREEMENT.md`
- [x] Add `docs/codex/TEMPLATE.md` for copy-ready session handoffs
- [x] Backfill missing dated handoff artifact `docs/codex/2026-02-25_codex-handoff-artifact-rules.md`
- [x] Harden handoff rules: missing dated handoff file means task is incomplete / no commit
- [x] Simplify handoff artifacts to single file `docs/codex/LATEST.md`
- [x] Make `docs/codex/LATEST.md` canonical with full USER NOTES + CODEX HANDOFF payload
- [x] Add strict staged-file verification rule for `docs/codex/LATEST.md` before commit
- [x] Simplify handoff workflow: local `docs/codex/LATEST.md` copy helper and no commit requirement
- [x] Enforce fixed handoff delimiters/section order and require writing `docs/codex/LATEST.md` even on failure
- [x] Enable Spring Boot DevTools + `dev` profile for faster Thymeleaf feedback loops in both apps
- [x] Update README with dev-mode run instructions and IntelliJ auto-restart notes
- [x] Add compose profile split for `infra` (postgres + nginx-dev) and `full` (postgres + apps + nginx)
- [x] Add `web/nginx/nginx.dev.conf` and update docs/commands for both runtime modes
- [x] Implement F-001 add-to-cart cross-service flow with htmx (`/orders/cart/items` + cart fragment update)
- [x] Extend Playwright smoke test to assert cart fragment changes after Add to cart
- [x] Reconcile manual dev workflow updates (`nginx.dev` no-cache, dev Thymeleaf file prefix, README IntelliJ working dir note)
- [x] Add Tailwind watch service to compose `infra` profile
- [x] Add `MagicShopItem` catalog domain (entity/repository/service) with Flyway V2 schema + seed data
- [x] Add minimal catalog JSON endpoints for magic items (`/catalog/api/items`, `/catalog/api/items/{id}`)
- [x] Switch catalog products fragment to `MagicShopItem` list rendering
- [x] Add catalog rich detail fragment endpoint + Thymeleaf detail partial
- [x] Keep add-to-cart contract intact with `sku=item.id` and verify via Playwright
- [x] Add catalog search + rarity/category filters via htmx on `/catalog/fragments/products`
- [x] Add catalog products empty-state messaging for no filter results
- [x] Extend Playwright smoke coverage for catalog filter interactions
- [x] Fix catalog filter SQL runtime error (`lower(bytea)`) by switching to a pre-built lowercase LIKE pattern parameter
- [x] Fix htmx filter form trigger so rarity/category selection filters without requiring search text
- [x] Remove host port mappings for app containers in compose `full` profile (`8081`/`8082`) to avoid local conflicts
- [x] Update README full/E2E guidance for infra/full exclusivity on port `8080`
- [x] Add best-effort `infra down` pre-step in `tests/e2e/scripts/e2e-all.mjs`
- [x] Add stable catalog filter test hooks (`catalog-filter`, `catalog-item`, `catalog-empty`) in products fragment
- [x] Replace Playwright filter response-URL waits with DOM-based readiness and assertion checks
- [x] Add catalog-owned item summary fragment endpoint (`/catalog/fragments/item-summary/{id}`)
- [x] Reuse catalog item summary in products list and cart line rendering
- [x] Add cart line increment/decrement/remove endpoints and controls with htmx fragment swaps
- [x] Assert lazy-loaded cart item summary for `itm_001` in Playwright smoke test

## Next (Do Not Implement Yet)
- [ ] Add service healthchecks for catalog/order containers and tighten compose dependency readiness
- [ ] Add a one-command helper for host-run Spring dev mode (starts both services with `dev` profile)
- [ ] Add a small cross-platform helper script to start both Spring apps with `dev` profile in separate terminals
- [ ] Add CI workflow to run `npm run e2e:all` (or equivalent split steps) on PRs
- [ ] Add category/rarity filter state reset UX (clear filters action)
- [ ] Add dedicated Playwright test file for catalog filtering permutations (keep smoke test small)
- [ ] Add optional catalog batch summary endpoint for cart optimization (defer until needed)

## Later
- [ ] Persist cart/order data (if desired)
- [ ] Add reusable shared Lit components for product card / cart badge
- [ ] Add integration tests for fragment endpoints
- [ ] Improve styling and design consistency
- [ ] Add CI checks (build/test/lint)

## Blocked / Questions
- [ ] Confirm whether to keep Option A per-line cart summary requests or move to a batched summary fetch when cart size grows
