import { expect, test } from "@playwright/test";

test("shell smoke checks", async ({ page }) => {
  await page.goto("http://localhost:8080");

  await expect(page).toHaveTitle(/Webshop Demo Shell/);

  const shopBadge = page.locator("shop-badge[label='Bootstrap Step 1']");
  await expect(shopBadge).toHaveCount(1);

  const catalogHealth = page.locator("#catalog-health");
  await expect(catalogHealth).toBeVisible();
  await expect
    .poll(async () => (await catalogHealth.innerText()).trim())
    .not.toBe("Loading catalog health...");

  const orderHealth = page.locator("#order-health");
  await expect(orderHealth).toBeVisible();
  await expect
    .poll(async () => (await orderHealth.innerText()).trim())
    .not.toBe("Loading order health...");

  const catalogProducts = page.locator("div[hx-get='/catalog/fragments/products']");
  await expect(catalogProducts).toBeVisible();
  await expect
    .poll(async () => (await catalogProducts.innerText()).trim())
    .not.toBe("Loading catalog products...");
});

test("health endpoints return 200", async ({ request }) => {
  const catalog = await request.get("http://localhost:8080/catalog/health");
  const orders = await request.get("http://localhost:8080/orders/health");

  expect(catalog.status()).toBe(200);
  expect(orders.status()).toBe(200);
});
