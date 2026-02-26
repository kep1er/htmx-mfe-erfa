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

## Dev Mode (Fast Feedback for Thymeleaf)
Run each Spring Boot app with the `dev` profile to disable Thymeleaf template cache and enable DevTools restart/livereload:

```bash
cd apps/catalog-service
mvn spring-boot:run -Dspring-boot.run.profiles=dev
```

```bash
cd apps/order-service
mvn spring-boot:run -Dspring-boot.run.profiles=dev
```

### IntelliJ Settings (for auto-restart)
- Enable: `Settings > Build, Execution, Deployment > Compiler > Build project automatically`
- Enable Registry flag: `compiler.automake.allow.when.app.running`
- Keep `dev` profile active in each Spring Boot run configuration.
- After template/code changes, trigger `Build Project` if restart does not happen automatically.

Note: Docker Compose + Playwright remains the E2E mode. Dev profile is for fast local iteration.

## Run Full Stack (Docker Compose)
```bash
docker compose up -d --build
```

This starts:
- `postgres`
- `catalog-service` (container)
- `order-service` (container)
- `nginx`

## E2E Smoke Tests (Playwright)
### Prerequisites / Start Stack
```bash
docker compose up -d --build
```

### One-Command Local E2E (Cross-Platform)
```bash
cd tests/e2e
npm install
npm run install:browsers
npm run e2e:all
```

### Windows (PowerShell)
```powershell
cd tests/e2e
npm install
npm run install:browsers
npm run e2e:all
npm run report
```

### Cross-Platform (bash/zsh/sh)
```bash
cd tests/e2e
npm install
npm run install:browsers
npm run e2e:all
npm run report
```

### Optional
```bash
cd tests/e2e
npm run test:headed
npm run e2e:all:down
```
- `e2e:all` leaves the stack running by default.
- `e2e:all:down` tears the stack down after the test run.

### If E2E Fails
```bash
docker compose ps
docker compose logs --tail 200 nginx catalog-service order-service postgres
```

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
- Compose uses `web/nginx/nginx.docker.conf` (proxy by service name).
- `web/nginx/nginx.conf` remains for host-local app mode (`host.docker.internal` proxy targets).
- `catalog-service` Flyway migration is in `apps/catalog-service/src/main/resources/db/migration/V1__init_catalog.sql`.
