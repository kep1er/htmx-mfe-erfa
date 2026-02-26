# PROJECT_STATE

Last updated: 2026-02-26

## Current architecture + profiles
- Monorepo: `apps/catalog-service`, `apps/order-service`, `web/nginx`, `web/tailwind`, `packages/ui-components`, `tests/e2e`.
- Catalog service: Spring Boot + Thymeleaf + PostgreSQL/Flyway; owns catalog fragments/data.
- Order service: Spring Boot + Thymeleaf + in-memory cart; owns cart fragment/actions.
- Shell: Nginx serves `web/nginx/html/index.html` and static assets; htmx loads SSR fragments.
- `infra` profile: `postgres` + `nginx-dev` + `tailwind`; run Spring apps on host with `dev` profile.
- `full` profile: `postgres` + both Spring containers + `nginx`; app containers are internal-only behind nginx.

## Stable contracts (routes, fragment IDs, key endpoints)
- Route ownership: `/catalog/**` -> catalog-service, `/orders/**` -> order-service, `/assets/**` -> nginx.
- Ports: nginx `8080`, catalog `8081`, order `8082`, postgres `5432`.
- Stable htmx/shell targets: `#app-main`, `#cart-fragment`, `#catalog-detail`.
- Stable test/UI hooks: `data-testid="app-main"`, `data-testid="nav-cart"`, `data-testid="pill-rarity-uncommon"`.
- Catalog endpoints:
  - `GET /catalog/fragments/products`
  - `GET /catalog/fragments/items/{id}`
  - `GET /catalog/fragments/item-summary/{id}`
  - `GET /catalog/health`
  - `GET /catalog/api/items`, `GET /catalog/api/items/{id}`
- Order endpoints:
  - `GET /orders/fragments/cart`
  - `POST /orders/cart/items`
  - `POST /orders/cart/items/{sku}/increment|decrement|remove`
  - `GET /orders/health`

## Current UI shell routing summary
- Shell routes are client-side view swaps in `web/nginx/assets/js/app.js`.
- `/` loads catalog fragment (with query params `q`, `rarity`, `category`).
- `/cart` loads order cart fragment.
- `/health` loads static shell fragment `web/nginx/html/fragments/health-view.html`.
- Top bar search/filter always targets catalog and keeps URL query in sync.
- `popstate` replays route loading to keep browser navigation consistent.

## Next 3 planned steps
1. Add one-command helper for host-run Spring dev mode (both services with `dev` profile).
2. Add a cross-platform helper script to start both Spring apps in separate terminals.
3. Add a dedicated Playwright test file for catalog filtering permutations (keep smoke test small).

## Known pitfalls
- Profiles: `infra` and `full` both bind host port `8080`; stop one profile before starting the other.
- Port expectations: in `full`, app ports `8081`/`8082` are not published to host; use nginx on `8080`.
- Asset caching: use `nginx.dev.conf` (no-store headers) for host-dev; stale CSS/JS usually means wrong nginx config/profile.
- Tailwind: CSS updates require the `tailwind` watcher (`docker compose --profile infra up -d`) or manual rebuild.
- Healthchecks: full profile startup depends on curl-based checks for `/catalog/health` and `/orders/health`; if containers stay `unhealthy`, inspect service logs and endpoint readiness.
