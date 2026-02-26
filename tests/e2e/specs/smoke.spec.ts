import { expect, test } from "@playwright/test";

test("shell smoke checks", async ({ page }) => {
  await page.goto("http://localhost:8080/");

  await expect(page).toHaveTitle(/Webshop Demo Shell/);

  const shopBadge = page.locator("shop-badge[label='Bootstrap Step 1']");
  await expect(shopBadge).toHaveCount(1);

  const appMain = page.locator("#app-main");
  await expect(appMain).toBeVisible();
  await expect(appMain).toContainText("Magical Items");

  const catalogItems = page.locator("[data-testid='catalog-item']");
  const catalogEmpty = page.locator("[data-testid='catalog-empty']");
  await expect
    .poll(async () => (await catalogItems.count()) + (await catalogEmpty.count()))
    .toBeGreaterThan(0);

  const searchInput = page.locator("#topbar-search-input");
  const uncommonPill = page.locator("button[data-pill-key='rarity'][data-pill-value='uncommon']");
  await expect(searchInput).toBeVisible();
  await expect(uncommonPill).toBeVisible();
  await uncommonPill.click();
  await expect
    .poll(async () => {
      const count = await catalogItems.count();
      if (count === 0) {
        return false;
      }
      for (let i = 0; i < count; i += 1) {
        const itemText = (await catalogItems.nth(i).innerText()).toLowerCase();
        if (!itemText.includes("uncommon")) {
          return false;
        }
      }
      return true;
    })
    .toBe(true);

  await searchInput.fill("Weather");
  await searchInput.press("Enter");
  await expect(appMain).toContainText("Pocket Weather Jar");

  const detailButton = page.locator("button[data-item-id='itm_001']");
  await expect(detailButton).toBeVisible();
  await detailButton.click();

  const catalogDetail = page.locator("#catalog-detail");
  await expect(catalogDetail).toBeVisible();
  await expect(catalogDetail).toContainText("Pocket Weather Jar (Mini)");
  await expect(catalogDetail).toContainText(/Skywright/i);

  const weatherCatalogItem = page
    .locator("[data-testid='catalog-item']")
    .filter({ hasText: "Pocket Weather Jar (Mini)" });
  await expect(weatherCatalogItem).toHaveCount(1);
  const addToCartButton = weatherCatalogItem.locator("button", { hasText: "Add to cart" });
  await expect(addToCartButton).toBeVisible();
  const addToCartResponse = page.waitForResponse(
    (response) =>
      response.url().includes("/orders/cart/items") &&
      response.request().method() === "POST",
  );
  await addToCartButton.click();
  await addToCartResponse;

  const cartNav = page.locator("#nav-cart");
  await expect(cartNav).toBeVisible();
  await cartNav.click();

  const cartFragment = page.locator("#cart-fragment");
  await expect(cartFragment).toBeVisible();
  await expect(cartFragment).toContainText("Items:");

  const cartSummary = cartFragment.locator("[data-testid='item-summary'][data-item-id='itm_001']");
  const cartSummaryPlaceholder = cartFragment.locator(
    "[data-testid='cart-item-summary-placeholder'][data-item-id='itm_001']",
  );
  await expect
    .poll(async () => (await cartSummary.count()) + (await cartSummaryPlaceholder.count()))
    .toBeGreaterThan(0);
  if ((await cartSummaryPlaceholder.count()) > 0) {
    await cartSummaryPlaceholder.first().scrollIntoViewIfNeeded();
  }
  await expect(cartSummary).toContainText("Pocket Weather Jar (Mini)");

  await searchInput.fill("Moonlit");
  await searchInput.press("Enter");
  await expect(appMain).toContainText("Moonlit Compass");
  await expect(appMain).not.toContainText("Cart is empty.");

  const healthNav = page.locator("#nav-health");
  await expect(healthNav).toBeVisible();
  await healthNav.click();
  await expect(page).toHaveURL(/\/health$/);
  const healthView = page.locator("[data-testid='health-view']");
  await expect(healthView).toBeVisible();
  await expect(healthView).toContainText("Catalog Health");
  await expect(healthView).toContainText("Order Health");
  await expect
    .poll(async () => (await page.locator("#catalog-health").innerText()).trim())
    .not.toBe("Loading catalog health...");
  await expect
    .poll(async () => (await page.locator("#order-health").innerText()).trim())
    .not.toBe("Loading order health...");
});

test("health endpoints return 200", async ({ request }) => {
  const catalog = await request.get("http://localhost:8080/catalog/health");
  const orders = await request.get("http://localhost:8080/orders/health");

  expect(catalog.status()).toBe(200);
  expect(orders.status()).toBe(200);
});
