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
  const catalogFilter = page.locator("[data-testid='catalog-filter']");
  const catalogItems = page.locator("[data-testid='catalog-item']");
  const catalogEmpty = page.locator("[data-testid='catalog-empty']");
  await expect(catalogFilter).toBeVisible();
  await expect
    .poll(async () => (await catalogItems.count()) + (await catalogEmpty.count()))
    .toBeGreaterThan(0);

  const searchInput = page.locator("#catalog-search-input");
  const raritySelect = page.locator("#catalog-rarity-select");
  const categorySelect = page.locator("#catalog-category-select");
  await expect(searchInput).toBeVisible();
  await expect(raritySelect).toBeVisible();
  await expect(categorySelect).toBeVisible();
  const typeSearch = async (value: string) => {
    await searchInput.click();
    await searchInput.press("Control+A");
    await searchInput.type(value);
  };

  await raritySelect.selectOption("uncommon");
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

  const categoryOptions = categorySelect.locator("option");
  const firstCategoryValue = await categoryOptions.nth(1).getAttribute("value");
  expect(firstCategoryValue).not.toBeNull();
  const categoryValue =
    (await categorySelect.locator("option[value='Curios']").count()) > 0
      ? "Curios"
      : firstCategoryValue!;
  await categorySelect.selectOption(categoryValue);
  await expect
    .poll(async () => {
      const count = await catalogItems.count();
      if (count === 0) {
        return false;
      }
      for (let i = 0; i < count; i += 1) {
        const itemText = (await catalogItems.nth(i).innerText()).toLowerCase();
        if (!itemText.includes(categoryValue.toLowerCase()) || !itemText.includes("uncommon")) {
          return false;
        }
      }
      return true;
    })
    .toBe(true);

  await typeSearch("Weather");
  await expect(catalogProducts).toContainText("Pocket Weather Jar");

  await typeSearch("zzzz-no-such-item");
  await expect(catalogEmpty).toBeVisible();

  await typeSearch("Weather");
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
