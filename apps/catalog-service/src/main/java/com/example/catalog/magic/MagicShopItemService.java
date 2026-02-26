package com.example.catalog.magic;

import java.util.List;
import java.util.Locale;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MagicShopItemService {

    private final MagicShopItemRepository magicShopItemRepository;

    public MagicShopItemService(MagicShopItemRepository magicShopItemRepository) {
        this.magicShopItemRepository = magicShopItemRepository;
    }

    @Transactional(readOnly = true)
    public List<MagicShopItem> listAll() {
        return magicShopItemRepository.findAllByOrderByNameAsc();
    }

    @Transactional(readOnly = true)
    public Optional<MagicShopItem> getById(String id) {
        return magicShopItemRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public List<MagicShopItem> listFiltered(String q, String rarity, String category) {
        String normalizedQ = normalize(q);
        String qPattern = toLikePattern(normalizedQ);
        String normalizedCategory = normalize(category);
        Rarity normalizedRarity = parseRarity(rarity);

        if (rarity != null && !rarity.isBlank() && normalizedRarity == null) {
            return List.of();
        }

        return magicShopItemRepository.findFiltered(qPattern, normalizedRarity, normalizedCategory);
    }

    @Transactional(readOnly = true)
    public List<String> listCategories() {
        return magicShopItemRepository.findDistinctCategories();
    }

    private static String normalize(String value) {
        if (value == null) {
            return null;
        }
        String trimmed = value.trim();
        return trimmed.isEmpty() ? null : trimmed;
    }

    private static Rarity parseRarity(String rarity) {
        String normalized = normalize(rarity);
        if (normalized == null) {
            return null;
        }
        try {
            return Rarity.fromValue(normalized);
        } catch (IllegalArgumentException ignored) {
            return null;
        }
    }

    private static String toLikePattern(String normalizedQ) {
        if (normalizedQ == null) {
            return null;
        }
        return "%" + normalizedQ.toLowerCase(Locale.ROOT) + "%";
    }
}
