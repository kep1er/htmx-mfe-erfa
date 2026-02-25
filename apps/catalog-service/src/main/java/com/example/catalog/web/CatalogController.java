package com.example.catalog.web;

import com.example.catalog.product.ProductRepository;
import java.time.Instant;
import java.util.LinkedHashMap;
import java.util.Map;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CatalogController {

    private final ProductRepository productRepository;

    public CatalogController(ProductRepository productRepository) {
        this.productRepository = productRepository;
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
    public String productFragment(Model model) {
        model.addAttribute("products", productRepository.findAllByOrderByIdAsc());
        return "fragments/products :: productList";
    }
}
