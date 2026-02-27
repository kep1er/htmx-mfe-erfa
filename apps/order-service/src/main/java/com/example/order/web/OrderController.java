package com.example.order.web;

import com.example.order.cart.InMemoryCartService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.Instant;
import java.util.LinkedHashMap;
import java.util.Map;

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

    @GetMapping("/orders/fragments/cart-badge")
    public String cartBadgeFragment(Model model) {
        model.addAttribute("itemCount", inMemoryCartService.snapshot().itemCount());
        return "fragments/cart-badge :: badge";
    }

    @PostMapping("/orders/cart/items")
    public String addCartItem(
            @RequestParam("sku") String sku,
            @RequestParam(name = "qty", defaultValue = "1") int qty,
            Model model,
            HttpServletResponse response
    ) {
        inMemoryCartService.addItem(sku, qty);
        applyCartModel(model);
        triggerCartChanged(response);
        return "fragments/cart :: cartSummary";
    }

    @PostMapping("/orders/cart/items/{sku}/increment")
    public String incrementCartItem(@PathVariable String sku, Model model, HttpServletResponse response) {
        inMemoryCartService.increment(sku);
        applyCartModel(model);
        triggerCartChanged(response);
        return "fragments/cart :: cartSummary";
    }

    @PostMapping("/orders/cart/items/{sku}/decrement")
    public String decrementCartItem(@PathVariable String sku, Model model, HttpServletResponse response) {
        inMemoryCartService.decrement(sku);
        applyCartModel(model);
        triggerCartChanged(response);
        return "fragments/cart :: cartSummary";
    }

    @PostMapping("/orders/cart/items/{sku}/remove")
    public String removeCartItem(@PathVariable String sku, Model model, HttpServletResponse response) {
        inMemoryCartService.remove(sku);
        applyCartModel(model);
        triggerCartChanged(response);
        return "fragments/cart :: cartSummary";
    }

    private void applyCartModel(Model model) {
        InMemoryCartService.CartSnapshot snapshot = inMemoryCartService.snapshot();
        model.addAttribute("itemCount", snapshot.itemCount());
        model.addAttribute("lines", snapshot.lines());
    }

    private void triggerCartChanged(HttpServletResponse response) {
        // TODO(EX05): Add HX-Trigger response header to broadcast {"cart:changed": true} on cart mutations.
    }
}
