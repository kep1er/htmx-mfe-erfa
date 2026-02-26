package com.example.order.web;

import com.example.order.cart.InMemoryCartService;
import java.time.Instant;
import java.util.LinkedHashMap;
import java.util.Map;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class OrderController {

    private final InMemoryCartService inMemoryCartService;

    public OrderController(InMemoryCartService inMemoryCartService) {
        this.inMemoryCartService = inMemoryCartService;
    }

    @GetMapping(path = "/orders/health", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Object>> health() {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("service", "order-service");
        body.put("status", "UP");
        body.put("timestamp", Instant.now().toString());
        return ResponseEntity.ok(body);
    }

    @GetMapping("/orders/health-page")
    public String healthPage(Model model) {
        model.addAttribute("serviceName", "order-service");
        model.addAttribute("status", "UP");
        model.addAttribute("timestamp", Instant.now().toString());
        return "health";
    }

    @GetMapping("/orders/fragments/health")
    public String healthFragment(Model model) {
        model.addAttribute("serviceName", "order-service");
        model.addAttribute("status", "UP");
        model.addAttribute("timestamp", Instant.now().toString());
        return "fragments/health :: healthStatus";
    }

    @GetMapping("/orders/fragments/cart")
    public String cartFragment(Model model) {
        applyCartModel(model);
        return "fragments/cart :: cartSummary";
    }

    @PostMapping("/orders/cart/items")
    public String addCartItem(
        @RequestParam("sku") String sku,
        @RequestParam(name = "qty", defaultValue = "1") int qty,
        Model model
    ) {
        inMemoryCartService.addItem(sku, qty);
        applyCartModel(model);
        return "fragments/cart :: cartSummary";
    }

    private void applyCartModel(Model model) {
        InMemoryCartService.CartSnapshot snapshot = inMemoryCartService.snapshot();
        model.addAttribute("itemCount", snapshot.itemCount());
        model.addAttribute("lines", snapshot.lines());
    }
}
