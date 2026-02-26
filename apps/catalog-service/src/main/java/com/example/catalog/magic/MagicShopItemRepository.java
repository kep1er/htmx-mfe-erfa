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
        where (:q is null
               or lower(i.name) like lower(concat('%', :q, '%'))
               or lower(i.category) like lower(concat('%', :q, '%'))
               or lower(t) like lower(concat('%', :q, '%')))
          and (:rarity is null or i.rarity = :rarity)
          and (:category is null or i.category = :category)
        order by i.name asc
        """)
    List<MagicShopItem> findFiltered(
        @Param("q") String q,
        @Param("rarity") Rarity rarity,
        @Param("category") String category
    );

    @Query("select distinct i.category from MagicShopItem i order by i.category asc")
    List<String> findDistinctCategories();
}
