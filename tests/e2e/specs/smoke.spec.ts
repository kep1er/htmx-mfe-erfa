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

  const catalogProducts = page.locator("#catalog-products");
  await expect(catalogProducts).toBeVisible();
  await expect
    .poll(async () => (await catalogProducts.innerText()).trim())
    .not.toBe("Loading catalog products...");
  await expect(catalogProducts).toContainText("Magical Items");

  const searchInput = page.locator("#catalog-search-input");
  const raritySelect = page.locator("#catalog-rarity-select");
  await expect(searchInput).toBeVisible();
  await expect(raritySelect).toBeVisible();

  await searchInput.fill("Weather");
  await expect(catalogProducts).toContainText("Pocket Weather Jar");

  await searchInput.fill("zzzz-no-such-item");
  await expect(catalogProducts).toContainText("No magical items found for the current filters.");

  await searchInput.fill("Weather");
  await raritySelect.selectOption("uncommon");
  await expect(catalogProducts).toContainText("Pocket Weather Jar");

  const detailButton = page.locator("button[data-item-id='itm_001']");
  await expect(detailButton).toBeVisible();
  await detailButton.click();

  const catalogDetail = page.locator("#catalog-detail");
  await expect(catalogDetail).toBeVisible();
  await expect(catalogDetail).toContainText("Pocket Weather Jar (Mini)");
  await expect(catalogDetail).toContainText(/Skywright/i);

  const cartFragment = page.locator("#cart-fragment");
  await expect(cartFragment).toBeVisible();
  await expect
    .poll(async () => (await cartFragment.innerText()).trim())
    .not.toBe("Loading cart placeholder...");

  const cartBeforeAdd = (await cartFragment.innerText()).trim();
  const addToCartButton = page.locator("button", { hasText: "Add to cart" }).first();
  await expect(addToCartButton).toBeVisible();
  await addToCartButton.click();

  await expect
    .poll(async () => (await cartFragment.innerText()).trim())
    .not.toBe(cartBeforeAdd);
  await expect(cartFragment).toContainText("Items:");
});

test("health endpoints return 200", async ({ request }) => {
  const catalog = await request.get("http://localhost:8080/catalog/health");
  const orders = await request.get("http://localhost:8080/orders/health");

  expect(catalog.status()).toBe(200);
  expect(orders.status()).toBe(200);
});
