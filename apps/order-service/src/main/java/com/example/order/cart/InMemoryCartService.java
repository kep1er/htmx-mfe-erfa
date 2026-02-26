package com.example.order.cart;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;

@Service
public class InMemoryCartService {

    private final Map<String, Integer> quantitiesBySku = new LinkedHashMap<>();

    public synchronized void addItem(String sku, int qty) {
        if (sku == null || sku.isBlank()) {
            return;
        }

        int safeQty = Math.max(1, qty);
        quantitiesBySku.merge(sku.trim(), safeQty, Integer::sum);
    }

    public synchronized CartSnapshot snapshot() {
        List<CartLine> lines = new ArrayList<>(quantitiesBySku.size());
        int itemCount = 0;

        for (Map.Entry<String, Integer> entry : quantitiesBySku.entrySet()) {
            lines.add(new CartLine(entry.getKey(), entry.getValue()));
            itemCount += entry.getValue();
        }

        return new CartSnapshot(itemCount, lines);
    }

    public record CartSnapshot(int itemCount, List<CartLine> lines) {
    }

    public record CartLine(String sku, int qty) {
    }
}
