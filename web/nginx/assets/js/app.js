const CATALOG_PATH = "/";
const CART_PATH = "/cart";
const HEALTH_PATH = "/health";
const CATALOG_FRAGMENT_URL = "/catalog/fragments/products";
const CART_FRAGMENT_URL = "/orders/fragments/cart";
const HEALTH_FRAGMENT_URL = "/fragments/health-view.html";

const appMain = document.querySelector("#app-main");
const topbarForm = document.querySelector("#topbar-catalog-form");
const searchInput = document.querySelector("#topbar-search-input");
const rarityInputs = Array.from(document.querySelectorAll("input[name='rarity']"));
const categoryInputs = Array.from(document.querySelectorAll("input[name='category']"));
const clearFiltersButton = document.querySelector("#topbar-clear-filters");
const navHome = document.querySelector("#nav-home");

function cleanParams(params) {
    const clean = new URLSearchParams(params);
    for (const [key, value] of clean.entries()) {
        if (value == null || String(value).trim() === "") {
            clean.delete(key);
        }
    }
    return clean;
}

function selectedRadioValue(radios) {
    const checkedRadio = radios.find((radio) => radio.checked);
    return checkedRadio ? checkedRadio.value : "";
}

function setSelectedRadioValue(radios, value) {
    let found = false;
    radios.forEach((radio) => {
        const shouldCheck = radio.value === value;
        radio.checked = shouldCheck;
        if (shouldCheck) {
            found = true;
        }
    });

    if (!found && radios.length > 0) {
        const defaultRadio = radios.find((radio) => radio.value === "") ?? radios[0];
        defaultRadio.checked = true;
    }
}

function topbarParams() {
    const params = new URLSearchParams();
    params.set("q", searchInput?.value ?? "");
    params.set("rarity", selectedRadioValue(rarityInputs));
    params.set("category", selectedRadioValue(categoryInputs));
    return cleanParams(params);
}

function syncTopbarFromParams(params) {
    if (searchInput) {
        searchInput.value = params.get("q") ?? "";
    }
    setSelectedRadioValue(rarityInputs, params.get("rarity") ?? "");
    setSelectedRadioValue(categoryInputs, params.get("category") ?? "");
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
    if (clearFiltersButton && searchInput && topbarForm) {
        clearFiltersButton.addEventListener("click", () => {
            searchInput.value = "";
            setSelectedRadioValue(rarityInputs, "");
            setSelectedRadioValue(categoryInputs, "");
            topbarForm.requestSubmit();
        });
    }

    if (navHome && searchInput) {
        navHome.addEventListener("click", () => {
            searchInput.value = "";
            setSelectedRadioValue(rarityInputs, "");
            setSelectedRadioValue(categoryInputs, "");
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
