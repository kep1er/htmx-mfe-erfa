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

## Next (Do Not Implement Yet)
- [ ] Add a tiny helper command/script to generate the required handoff section order and write `docs/codex/LATEST.md`
- [ ] Add service healthchecks for catalog/order containers and tighten compose dependency readiness
- [ ] Add CI workflow to run `npm run e2e:all` (or equivalent split steps) on PRs

## Later
- [ ] Persist cart/order data (if desired)
- [ ] Add reusable shared Lit components for product card / cart badge
- [ ] Add integration tests for fragment endpoints
- [ ] Improve styling and design consistency
- [ ] Add CI checks (build/test/lint)

## Blocked / Questions
- [ ] Confirm whether to keep publishing app ports `8081` and `8082` on host in compose, or make them internal-only
