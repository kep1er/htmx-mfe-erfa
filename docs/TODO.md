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

## Next (Do Not Implement Yet)
- [ ] Add first interactive htmx behavior in catalog (e.g., product filter input hitting a Thymeleaf fragment endpoint)
- [ ] Add minimal in-memory add-to-cart endpoint in `order-service` (still no persistence)
- [ ] Wire one end-to-end htmx action from catalog item to cart refresh fragment

## Later
- [ ] Persist cart/order data (if desired)
- [ ] Add reusable shared Lit components for product card / cart badge
- [ ] Add integration tests for fragment endpoints
- [ ] Improve styling and design consistency
- [ ] Add containerization for both Spring Boot apps in Compose
- [ ] Add CI checks (build/test/lint)

## Blocked / Questions
- [ ] Confirm whether to containerize both Spring apps in the next step or keep local-run mode for one more iteration
