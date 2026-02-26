package com.example.catalog.magic;

import java.util.List;
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
}
