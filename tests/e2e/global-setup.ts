const BASE_URL = "http://localhost:8080";
const TIMEOUT_MS = 15_000;
const INTERVAL_MS = 1_000;

const sleep = (ms: number) => new Promise((resolve) => setTimeout(resolve, ms));

export default async function globalSetup(): Promise<void> {
  const startedAt = Date.now();
  let lastError = "unknown error";

  while (Date.now() - startedAt < TIMEOUT_MS) {
    try {
      const response = await fetch(BASE_URL, { method: "GET" });
      if (response.status < 500) {
        return;
      }
      lastError = `received HTTP ${response.status} from ${BASE_URL}`;
    } catch (error) {
      lastError = error instanceof Error ? error.message : String(error);
    }

    await sleep(INTERVAL_MS);
  }

  throw new Error(
    [
      `Playwright preflight failed: ${BASE_URL} was not reachable within ${TIMEOUT_MS / 1000}s.`,
      `Last error: ${lastError}`,
      "",
      "Start the stack, then rerun E2E tests:",
      "docker compose up -d postgres nginx",
      "cd apps/catalog-service && mvn spring-boot:run",
      "cd apps/order-service && mvn spring-boot:run"
    ].join("\n")
  );
}
