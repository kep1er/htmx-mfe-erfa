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

## Dev/Infra Mode (Docker infra + host-run Spring apps)
Start dev infra in Docker:

```bash
docker compose --profile infra up -d
```
This starts: `postgres`, `nginx-dev`, and the `tailwind` watcher.

Run each Spring Boot app on host with the `dev` profile (Thymeleaf cache off + DevTools restart/livereload):

```bash
cd apps/catalog-service
mvn spring-boot:run -Dspring-boot.run.profiles=dev
```

```bash
cd apps/order-service
mvn spring-boot:run -Dspring-boot.run.profiles=dev
```

Open: `http://localhost:8080`

### IntelliJ Settings (for auto-restart)
- Enable: `Settings > Build, Execution, Deployment > Compiler > Build project automatically`
- Enable Registry flag: `compiler.automake.allow.when.app.running`
- Keep `dev` profile active in each Spring Boot run configuration.
- Set each Spring Boot run configuration working directory to its app folder:
  - `apps/catalog-service`
  - `apps/order-service`
- After template/code changes, trigger `Build Project` if restart does not happen automatically.

## Full/E2E Mode (all containers)
`infra` and `full` profiles cannot run at the same time because both publish host port `8080` (`nginx-dev` vs `nginx`).
Before starting full mode, stop infra mode:

```bash
docker compose --profile infra down
```

Start full stack in Docker:

```bash
docker compose --profile full up -d --build
```

In full mode, `catalog-service` and `order-service` are internal-only (no host ports `8081`/`8082`).
Access services through nginx on `http://localhost:8080`.

Run Playwright:
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
- Host-run apps only (infra mode):
  - Catalog health page: `http://localhost:8081/catalog/health-page`
  - Order health page: `http://localhost:8082/orders/health-page`
  - Actuator health:
    - `http://localhost:8081/actuator/health`
    - `http://localhost:8082/actuator/health`

## Notes
- Compose `full` profile uses `web/nginx/nginx.docker.conf` (proxy by service name).
- Compose `infra` profile uses `web/nginx/nginx.dev.conf` (proxy to `host.docker.internal` for host-run apps).
- `web/nginx/nginx.conf` remains available for host-local app mode compatibility.
- `catalog-service` Flyway migration is in `apps/catalog-service/src/main/resources/db/migration/V1__init_catalog.sql`.
