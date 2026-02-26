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

    public synchronized void increment(String sku) {
        addItem(sku, 1);
    }

    public synchronized void decrement(String sku) {
        if (sku == null || sku.isBlank()) {
            return;
        }

        String key = sku.trim();
        Integer currentQty = quantitiesBySku.get(key);
        if (currentQty == null) {
            return;
        }

        if (currentQty <= 1) {
            quantitiesBySku.remove(key);
            return;
        }

        quantitiesBySku.put(key, currentQty - 1);
    }

    public synchronized void remove(String sku) {
        if (sku == null || sku.isBlank()) {
            return;
        }

        quantitiesBySku.remove(sku.trim());
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
