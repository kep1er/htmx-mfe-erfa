import { spawn } from "node:child_process";
import path from "node:path";
import { fileURLToPath } from "node:url";

const __filename = fileURLToPath(import.meta.url);
const __dirname = path.dirname(__filename);
const testsDir = path.resolve(__dirname, "..");
const repoRoot = path.resolve(testsDir, "..", "..");

const BASE_URL = "http://localhost:8080";
const WAIT_TIMEOUT_MS = 60_000;
const WAIT_INTERVAL_MS = 2_000;

const shouldDown = process.argv.includes("--down");

function sleep(ms) {
  return new Promise((resolve) => setTimeout(resolve, ms));
}

function runCommand(command, cwd) {
  return new Promise((resolve, reject) => {
    const child = spawn(command, {
      cwd,
      shell: true,
      stdio: "inherit"
    });

    child.on("error", reject);
    child.on("close", (code) => {
      if (code === 0) {
        resolve();
        return;
      }
      reject(new Error(`Command failed (${code}): ${command}`));
    });
  });
}

async function runBestEffort(command, cwd) {
  try {
    await runCommand(command, cwd);
  } catch (error) {
    const message = error instanceof Error ? error.message : String(error);
    console.warn(`Continuing after best-effort command failure: ${message}`);
  }
}

async function waitForUrl(url) {
  const startedAt = Date.now();
  let lastError = "unknown error";

  while (Date.now() - startedAt < WAIT_TIMEOUT_MS) {
    try {
      const response = await fetch(url, { method: "GET" });
      if (response.status < 500) {
        console.log(`Stack is reachable: ${url} (HTTP ${response.status})`);
        return;
      }
      lastError = `HTTP ${response.status}`;
    } catch (error) {
      lastError = error instanceof Error ? error.message : String(error);
    }

    await sleep(WAIT_INTERVAL_MS);
  }

  throw new Error(
    `Timed out waiting for ${url} within ${WAIT_TIMEOUT_MS / 1000}s. Last error: ${lastError}`
  );
}

async function main() {
  let composeStarted = false;
  try {
    await runBestEffort("docker compose --profile infra down", repoRoot);
    await runCommand("docker compose --profile full up -d --build", repoRoot);
    composeStarted = true;
    await waitForUrl(BASE_URL);
    await runCommand("npx playwright test", testsDir);
  } finally {
    if (shouldDown && composeStarted) {
      await runCommand("docker compose down", repoRoot);
    } else if (!shouldDown) {
      console.log("Stack remains running. Use `docker compose down` when done.");
    }
  }
}

main().catch((error) => {
  console.error(error.message);
  process.exit(1);
});
