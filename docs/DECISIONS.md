# Decisions

> Record architecture and implementation decisions here.
> Keep entries short and practical.

## Template
- **Date**: YYYY-MM-DD
- **Decision**: ...
- **Why**: ...
- **Impact**: ...

---

## Initial Constraints (Pre-Codex)
- **Date**: 2026-02-25
- **Decision**: Use monorepo with 2 Spring Boot apps + Nginx shell + shared Lit components package
- **Why**: Demonstrate SSR fragments across multiple applications with shared native Web Components
- **Impact**: Requires clear route ownership and stable proxy conventions

- **Date**: 2026-02-25
- **Decision**: Use htmx + Thymeleaf for server-rendered fragment interactions
- **Why**: Keep frontend simple and SSR-first while still enabling dynamic UX
- **Impact**: Fragment IDs and htmx contracts become important stable interfaces

- **Date**: 2026-02-25
- **Decision**: Use PostgreSQL + Flyway (catalog first)
- **Why**: Show realistic persistence while keeping order/cart simple initially
- **Impact**: `catalog-service` gets DB config/migrations early; `order-service` remains in-memory initially

- **Date**: 2026-02-25
- **Decision**: Pin Spring Boot to `4.0.2`
- **Why**: Latest stable patch in the `4.0.x` line during this bootstrap session
- **Impact**: Both services use the same baseline and can be upgraded together in later steps

- **Date**: 2026-02-25
- **Decision**: Compose runs only `postgres` and `nginx`; Spring apps run locally via Maven
- **Why**: Smallest runnable setup for incremental demo progress without adding app Dockerfiles yet
- **Impact**: `nginx` proxies to `host.docker.internal` ports `8081` and `8082`

- **Date**: 2026-02-25
- **Decision**: Reserve proxy path ownership as `/catalog/**` and `/orders/**`
- **Why**: Keep route ownership explicit and stable from the first slice
- **Impact**: htmx calls and service endpoints use these prefixes consistently

- **Date**: 2026-02-25
- **Decision**: Keep `packages/ui-components` standalone with a minimal Node copy build
- **Why**: Avoid extra monorepo tooling while still producing a built JS asset served by nginx
- **Impact**: `npm run build` writes to both `packages/ui-components/dist` and `web/nginx/assets/ui-components`

- **Date**: 2026-02-25
- **Decision**: Tailwind build source is `web/tailwind/input.css` and output is `web/nginx/assets/css/app.css`
- **Why**: Directly couples shell styles to nginx static serving with minimal complexity
- **Impact**: Tailwind rebuild is required after class changes in shell/templates

- **Date**: 2026-02-25
- **Decision**: Move local compose runtime to full-stack containers (postgres + catalog-service + order-service + nginx)
- **Why**: Enable self-contained local E2E execution without requiring manual Maven app startup
- **Impact**: Compose now builds/runs both Spring Boot services and wires service-to-service networking internally

- **Date**: 2026-02-25
- **Decision**: Keep two nginx configs: `nginx.docker.conf` (compose runtime) and `nginx.conf` (host-local app mode)
- **Why**: Preserve local-host development flexibility while making compose runtime deterministic
- **Impact**: Compose mounts `nginx.docker.conf`; local-host mode can still target `host.docker.internal`

- **Date**: 2026-02-25
- **Decision**: Add `tests/e2e` Node-based `e2e:all` orchestration script
- **Why**: Provide one cross-platform command to compose up/build, wait for nginx, and run Playwright
- **Impact**: Local E2E flow becomes a single command; stack is left running by default unless `--down` is used

- **Date**: 2026-02-26
- **Decision**: Implement cart write flow in `order-service` as in-memory SKU/qty lines with `POST /orders/cart/items` returning the cart Thymeleaf fragment
- **Why**: Deliver the first cross-service htmx vertical slice with minimal domain complexity
- **Impact**: Catalog fragment can post directly to `/orders/cart/items` and update `#cart-fragment` without page reload; cart state resets on service restart

- **Date**: 2026-02-26
- **Decision**: Run Tailwind watch in compose `infra` profile via dedicated `tailwind` service
- **Why**: Remove manual Tailwind watch startup from local dev loop
- **Impact**: `docker compose --profile infra up -d` now includes CSS watcher behavior and writes to `web/nginx/assets/css/app.css`

- **Date**: 2026-02-26
- **Decision**: Model `MagicShopItem` rich arrays with JPA `@ElementCollection` tables and map dimensions/charges as embedded columns
- **Why**: Keep persistence relational and PostgreSQL-friendly for this incremental step, avoiding JSONB complexity
- **Impact**: Catalog now has normalized tables (`magic_shop_items` + collection tables) and simple API-ready mapping for upcoming item list/detail UI work

- **Date**: 2026-02-26
- **Decision**: Move catalog shell fragment contract from legacy `Product` to `MagicShopItem` and add detail fragment route `GET /catalog/fragments/items/{id}`
- **Why**: Align UI rendering with new catalog domain while keeping htmx-driven incremental SSR flow
- **Impact**: `/catalog/fragments/products` now renders `MagicShopItem` fields and add-to-cart posts `sku=item.id`; nginx route ownership remains unchanged under `/catalog/**`

- **Date**: 2026-02-26
- **Decision**: Keep category options in the existing `/catalog/fragments/products` Thymeleaf fragment model instead of adding a separate categories endpoint
- **Why**: Smallest incremental approach for htmx filter UI with no additional route contract
- **Impact**: Catalog filter form and list are rendered/swapped together in one fragment; controller now provides `categories` and current filter values for fragment re-rendering
