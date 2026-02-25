# Webshop Monorepo (Demo)

Demo webshop monorepo with:
- `catalog-service` (Spring Boot + Thymeleaf + PostgreSQL + Flyway)
- `order-service` (Spring Boot + Thymeleaf, in-memory placeholder)
- Nginx shell (`index.html`) with htmx
- shared Lit component package in `packages/ui-components`
- Tailwind CSS build pipeline

## Ports
- Nginx: `8080`
- Catalog service: `8081`
- Order service: `8082`
- PostgreSQL: `5432`

## Prerequisites
- Java `25`
- Maven `3.9+`
- Node.js `20+` and npm
- Docker Desktop (or Docker Engine + Compose)

## Run (Bootstrap Step 1)
1. Start infrastructure:
   ```bash
   docker compose up -d postgres nginx
   ```
2. Build UI component asset (copies to Nginx assets):
   ```bash
   cd packages/ui-components
   npm run build
   cd ../..
   ```
3. Install and build Tailwind CSS:
   ```bash
   cd web/tailwind
   npm install
   npm run build
   cd ../..
   ```
4. Run Spring Boot apps in two terminals:
   ```bash
   cd apps/catalog-service
   mvn spring-boot:run
   ```
   ```bash
   cd apps/order-service
   mvn spring-boot:run
   ```
5. Open `http://localhost:8080`.

## Quick Verification
- Nginx shell: `http://localhost:8080`
- Catalog health JSON: `http://localhost:8080/catalog/health`
- Order health JSON: `http://localhost:8080/orders/health`
- Catalog health page: `http://localhost:8081/catalog/health-page`
- Order health page: `http://localhost:8082/orders/health-page`
- Actuator health:
  - `http://localhost:8081/actuator/health`
  - `http://localhost:8082/actuator/health`

## Notes
- Nginx proxies to local app processes via `host.docker.internal`.
- `catalog-service` Flyway migration is in `apps/catalog-service/src/main/resources/db/migration/V1__init_catalog.sql`.
