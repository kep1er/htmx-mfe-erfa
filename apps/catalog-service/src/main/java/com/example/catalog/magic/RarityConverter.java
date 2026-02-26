package com.example.catalog.magic;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class RarityConverter implements AttributeConverter<Rarity, String> {

    @Override
    public String convertToDatabaseColumn(Rarity attribute) {
        if (attribute == null) {
            return null;
        }
        return attribute.value();
    }

    @Override
    public Rarity convertToEntityAttribute(String dbData) {
        if (dbData == null || dbData.isBlank()) {
            return null;
        }
        return Rarity.fromValue(dbData);
    }
}
