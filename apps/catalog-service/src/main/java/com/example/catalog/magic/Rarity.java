package com.example.catalog.magic;

public enum Rarity {
    COMMON("common"),
    UNCOMMON("uncommon"),
    RARE("rare"),
    LEGENDARY("legendary");

    private final String value;

    Rarity(String value) {
        this.value = value;
    }

    public String value() {
        return value;
    }

    public static Rarity fromValue(String value) {
        for (Rarity rarity : values()) {
            if (rarity.value.equalsIgnoreCase(value)) {
                return rarity;
            }
        }
        throw new IllegalArgumentException("Unknown rarity value: " + value);
    }
}
