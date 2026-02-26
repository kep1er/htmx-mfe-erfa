package com.example.catalog.web;

import com.example.catalog.magic.MagicShopItemService;
import java.time.Instant;
import java.util.LinkedHashMap;
import java.util.Map;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CatalogController {

    private final MagicShopItemService magicShopItemService;

    public CatalogController(MagicShopItemService magicShopItemService) {
        this.magicShopItemService = magicShopItemService;
    }

    @GetMapping(path = "/catalog/health", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Object>> health() {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("service", "catalog-service");
        body.put("status", "UP");
        body.put("timestamp", Instant.now().toString());
        return ResponseEntity.ok(body);
    }

    @GetMapping("/catalog/health-page")
    public String healthPage(Model model) {
        model.addAttribute("serviceName", "catalog-service");
        model.addAttribute("status", "UP");
        model.addAttribute("timestamp", Instant.now().toString());
        return "health";
    }

    @GetMapping("/catalog/fragments/health")
    public String healthFragment(Model model) {
        model.addAttribute("serviceName", "catalog-service");
        model.addAttribute("status", "UP");
        model.addAttribute("timestamp", Instant.now().toString());
        return "fragments/health :: healthStatus";
    }

    @GetMapping("/catalog/fragments/products")
    public String productFragment(
        @RequestParam(required = false) String q,
        @RequestParam(required = false) String rarity,
        @RequestParam(required = false) String category,
        Model model
    ) {
        model.addAttribute("items", magicShopItemService.listFiltered(q, rarity, category));
        model.addAttribute("categories", magicShopItemService.listCategories());
        model.addAttribute("currentQ", q);
        model.addAttribute("currentRarity", rarity);
        model.addAttribute("currentCategory", category);
        return "fragments/products :: productList";
    }

    @GetMapping("/catalog/fragments/items/{id}")
    public String itemDetailFragment(@PathVariable String id, Model model) {
        model.addAttribute("item", magicShopItemService.getById(id).orElse(null));
        return "fragments/item-detail :: detailCard";
    }
}
