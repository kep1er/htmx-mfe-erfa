# Architecture

## Purpose
Example webshop monorepo to demonstrate:
- Spring Boot SSR with Thymeleaf fragments
- htmx-driven interactions
- shared native Web Components built with Lit
- Nginx as frontend shell + static server + reverse proxy

## Monorepo Structure (intended)
- `apps/catalog-service` — product/catalog fragments, persisted with PostgreSQL
- `apps/order-service` — cart/order fragments, in-memory initially
- `web/nginx` — `index.html`, static assets, nginx config
- `web/tailwind` — Tailwind CLI setup that builds CSS into nginx assets
- `packages/ui-components` — shared native Web Components built with Lit
- `docs` — project memory / handoff docs

## Technology Stack
- Java 25
- Spring Boot 4.0.x
- Maven
- Thymeleaf
- htmx
- Lit (native Web Components)
- Tailwind CSS
- PostgreSQL
- Flyway
- Nginx
- Docker Compose (local infra)

## Port Allocation (stable unless explicitly changed)
- Nginx: `8080`
- Catalog service: `8081`
- Order service: `8082`
- PostgreSQL: `5432`

## Service Responsibilities (stable unless explicitly changed)
### Catalog Service
Owns:
- product/catalog pages/fragments
- product data access
- Flyway migrations and seed data for catalog domain

### Order Service
Owns:
- cart/order pages/fragments
- in-memory cart behavior initially (persistence may come later)

## Frontend Model
- Nginx serves the frontend shell (`index.html`)
- htmx loads and updates HTML fragments from backend services
- Spring Boot apps render fragments with Thymeleaf
- Shared UI elements can be rendered/enhanced with Lit Web Components
- Tailwind provides styling (global shell + non-shadow styles)

## Proxy Path Conventions
- `/catalog/**` is owned by `catalog-service` via nginx reverse proxy to `http://host.docker.internal:8081`
- `/orders/**` is owned by `order-service` via nginx reverse proxy to `http://host.docker.internal:8082`
- `/assets/**` is served directly by nginx static files

## Stable Contracts
These must not change without documenting in `docs/DECISIONS.md`:
- port numbers
- route ownership by service
- nginx proxy path mapping
- htmx target IDs / fragment IDs (once introduced)
- location of `packages/ui-components`

## Current Status
- Bootstrap Step 1 implemented:
  - both Spring Boot apps created with Thymeleaf and health endpoints/pages
  - catalog persistence baseline added (PostgreSQL + Flyway schema/seed)
  - nginx shell + htmx + proxy routing wired
  - Tailwind CLI setup added and output integrated into nginx assets
  - Lit demo component added in `packages/ui-components` and served by nginx
  - Docker Compose added for nginx + postgres
