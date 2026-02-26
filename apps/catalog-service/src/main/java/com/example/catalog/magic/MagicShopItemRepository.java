package com.example.catalog.magic;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MagicShopItemRepository extends JpaRepository<MagicShopItem, String> {
    List<MagicShopItem> findAllByOrderByNameAsc();
}
