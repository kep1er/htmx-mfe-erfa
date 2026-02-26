const CATALOG_PATH = "/";
const CART_PATH = "/cart";
const HEALTH_PATH = "/health";
const CATALOG_FRAGMENT_URL = "/catalog/fragments/products";
const CART_FRAGMENT_URL = "/orders/fragments/cart";
const HEALTH_FRAGMENT_URL = "/fragments/health-view.html";

const appMain = document.querySelector("#app-main");
const topbarForm = document.querySelector("#topbar-catalog-form");
const searchInput = document.querySelector("#topbar-search-input");
const rarityInput = document.querySelector("#topbar-rarity-input");
const categoryInput = document.querySelector("#topbar-category-input");
const clearFiltersButton = document.querySelector("#topbar-clear-filters");
const navHome = document.querySelector("#nav-home");
const pillButtons = Array.from(document.querySelectorAll("[data-pill-key][data-pill-value]"));

function cleanParams(params) {
    const clean = new URLSearchParams(params);
    for (const [key, value] of clean.entries()) {
        if (value == null || String(value).trim() === "") {
            clean.delete(key);
        }
    }
    return clean;
}

function topbarParams() {
    const params = new URLSearchParams();
    params.set("q", searchInput?.value ?? "");
    params.set("rarity", rarityInput?.value ?? "");
    params.set("category", categoryInput?.value ?? "");
    return cleanParams(params);
}

function syncTopbarFromParams(params) {
    if (searchInput) {
        searchInput.value = params.get("q") ?? "";
    }
    if (rarityInput) {
        rarityInput.value = params.get("rarity") ?? "";
    }
    if (categoryInput) {
        categoryInput.value = params.get("category") ?? "";
    }
    updatePillStates();
}

function updatePillStates() {
    const activeRarity = rarityInput?.value ?? "";
    const activeCategory = categoryInput?.value ?? "";

    pillButtons.forEach((button) => {
        const key = button.dataset.pillKey;
        const value = button.dataset.pillValue;
        const isActive = key === "rarity"
            ? value === activeRarity
            : value === activeCategory;
        button.classList.toggle("bg-slate-900", isActive);
        button.classList.toggle("text-white", isActive);
        button.classList.toggle("border-slate-900", isActive);
        button.classList.toggle("border-slate-300", !isActive);
        button.setAttribute("aria-pressed", String(isActive));
    });
}

function htmxLoad(url) {
    if (!appMain || !window.htmx) {
        return;
    }
    window.htmx.ajax("GET", url, {
        target: "#app-main",
        swap: "innerHTML"
    });
}

function loadCatalogFromUrlParams() {
    const params = cleanParams(new URLSearchParams(window.location.search));
    syncTopbarFromParams(params);
    const query = params.toString();
    htmxLoad(query ? `${CATALOG_FRAGMENT_URL}?${query}` : CATALOG_FRAGMENT_URL);
}

function loadCartView() {
    htmxLoad(CART_FRAGMENT_URL);
}

function loadHealthView() {
    htmxLoad(HEALTH_FRAGMENT_URL);
}

function loadRouteFromLocation() {
    const path = window.location.pathname;
    if (path === CART_PATH) {
        loadCartView();
        return;
    }
    if (path === HEALTH_PATH) {
        loadHealthView();
        return;
    }
    loadCatalogFromUrlParams();
}

function replaceCatalogBrowserUrl() {
    const params = topbarParams();
    const query = params.toString();
    const nextUrl = query ? `${CATALOG_PATH}?${query}` : CATALOG_PATH;
    window.history.replaceState({}, "", nextUrl);
}

window.addEventListener("DOMContentLoaded", () => {
    if (topbarForm) {
        topbarForm.addEventListener("submit", () => {
            updatePillStates();
        });
    }

    if (clearFiltersButton && searchInput && rarityInput && categoryInput && topbarForm) {
        clearFiltersButton.addEventListener("click", () => {
            searchInput.value = "";
            rarityInput.value = "";
            categoryInput.value = "";
            updatePillStates();
            topbarForm.requestSubmit();
        });
    }

    pillButtons.forEach((button) => {
        button.addEventListener("click", () => {
            const key = button.dataset.pillKey;
            const value = button.dataset.pillValue ?? "";
            if (key === "rarity" && rarityInput) {
                rarityInput.value = rarityInput.value === value ? "" : value;
            }
            if (key === "category" && categoryInput) {
                categoryInput.value = categoryInput.value === value ? "" : value;
            }
            updatePillStates();
            topbarForm?.requestSubmit();
        });
    });

    if (navHome && searchInput && rarityInput && categoryInput) {
        navHome.addEventListener("click", () => {
            searchInput.value = "";
            rarityInput.value = "";
            categoryInput.value = "";
            updatePillStates();
        });
    }

    document.body.addEventListener("htmx:afterRequest", (event) => {
        const source = event.detail?.elt;
        if (!(source instanceof Element)) {
            return;
        }
        if (source.id === "topbar-catalog-form") {
            replaceCatalogBrowserUrl();
        }
    });

    window.addEventListener("popstate", () => {
        loadRouteFromLocation();
    });

    loadRouteFromLocation();
});
