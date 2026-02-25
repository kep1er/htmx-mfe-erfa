package com.example.order.web;

import java.time.Instant;
import java.util.LinkedHashMap;
import java.util.Map;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class OrderController {

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
        model.addAttribute("itemCount", 0);
        model.addAttribute("total", "$0.00");
        return "fragments/cart :: cartSummary";
    }
}
