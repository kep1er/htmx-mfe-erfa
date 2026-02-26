package com.example.catalog.magic;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MagicShopItemRepository extends JpaRepository<MagicShopItem, String> {
    List<MagicShopItem> findAllByOrderByNameAsc();

    @Query("""
        select distinct i
        from MagicShopItem i
        left join i.tags t
        where (:qPattern is null
               or lower(i.name) like :qPattern
               or lower(i.category) like :qPattern
               or lower(t) like :qPattern)
          and (:rarity is null or i.rarity = :rarity)
          and (:category is null or i.category = :category)
        order by i.name asc
        """)
    List<MagicShopItem> findFiltered(
        @Param("qPattern") String qPattern,
        @Param("rarity") Rarity rarity,
        @Param("category") String category
    );

    @Query("select distinct i.category from MagicShopItem i order by i.category asc")
    List<String> findDistinctCategories();
}
