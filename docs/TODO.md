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

## Next (Do Not Implement Yet)
- [ ] Add a lightweight script to start required services and run `tests/e2e` smoke tests in one command
- [ ] Improve preflight to optionally validate `/catalog/health` and `/orders/health` before specs run
- [ ] Implement one interactive catalog filter htmx slice and extend smoke tests for it

## Later
- [ ] Persist cart/order data (if desired)
- [ ] Add reusable shared Lit components for product card / cart badge
- [ ] Add integration tests for fragment endpoints
- [ ] Improve styling and design consistency
- [ ] Add containerization for both Spring Boot apps in Compose
- [ ] Add CI checks (build/test/lint)

## Blocked / Questions
- [ ] Confirm whether to containerize both Spring apps in the next step or keep local-run mode for one more iteration
