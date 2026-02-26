package com.example.catalog.web;

import com.example.catalog.magic.Charges;
import com.example.catalog.magic.Dimensions;
import com.example.catalog.magic.MagicShopItem;
import com.example.catalog.magic.MagicShopItemService;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/catalog/api/items")
public class MagicShopItemApiController {

    private final MagicShopItemService magicShopItemService;

    public MagicShopItemApiController(MagicShopItemService magicShopItemService) {
        this.magicShopItemService = magicShopItemService;
    }

    @GetMapping
    public List<MagicShopItemSummaryResponse> listItems() {
        return magicShopItemService.listAll()
            .stream()
            .map(MagicShopItemApiController::toSummary)
            .toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<MagicShopItemDetailResponse> getItem(@PathVariable String id) {
        return magicShopItemService.getById(id)
            .map(MagicShopItemApiController::toDetail)
            .map(ResponseEntity::ok)
            .orElseGet(() -> ResponseEntity.notFound().build());
    }

    private static MagicShopItemSummaryResponse toSummary(MagicShopItem item) {
        return new MagicShopItemSummaryResponse(
            item.getId(),
            item.getName(),
            item.getPrice(),
            item.getStock(),
            item.getCategory(),
            item.getRarity().value(),
            item.getImage(),
            item.getTags().stream().sorted().toList()
        );
    }

    private static MagicShopItemDetailResponse toDetail(MagicShopItem item) {
        return new MagicShopItemDetailResponse(
            item.getId(),
            item.getName(),
            item.getPrice(),
            item.getStock(),
            item.getCategory(),
            item.getRarity().value(),
            item.getDescription(),
            item.getImage(),
            item.getTags().stream().sorted().toList(),
            item.getLore(),
            item.getEffects().stream().sorted().toList(),
            item.getLimitations().stream().sorted().toList(),
            item.getInstructions().stream().sorted().toList(),
            item.getWarnings().stream().sorted().toList(),
            item.getMaker(),
            item.getOrigin(),
            item.getMaterials().stream().sorted().toList(),
            item.getWeightGrams(),
            toDimensions(item.getDimensions()),
            item.getRelatedItemIds().stream().sorted().toList(),
            item.getReleasedAt(),
            item.isAttunementRequired(),
            toCharges(item.getCharges())
        );
    }

    private static DimensionsResponse toDimensions(Dimensions dimensions) {
        if (dimensions == null) {
            return null;
        }
        return new DimensionsResponse(
            dimensions.getLengthMm(),
            dimensions.getWidthMm(),
            dimensions.getHeightMm()
        );
    }

    private static ChargesResponse toCharges(Charges charges) {
        if (charges == null) {
            return null;
        }
        return new ChargesResponse(charges.getMax(), charges.getRecharge());
    }

    public record MagicShopItemSummaryResponse(
        String id,
        String name,
        BigDecimal price,
        Integer stock,
        String category,
        String rarity,
        String image,
        List<String> tags
    ) {
    }

    public record MagicShopItemDetailResponse(
        String id,
        String name,
        BigDecimal price,
        Integer stock,
        String category,
        String rarity,
        String description,
        String image,
        List<String> tags,
        String lore,
        List<String> effects,
        List<String> limitations,
        List<String> instructions,
        List<String> warnings,
        String maker,
        String origin,
        List<String> materials,
        Integer weightGrams,
        DimensionsResponse dimensions,
        List<String> relatedItemIds,
        OffsetDateTime releasedAt,
        boolean attunementRequired,
        ChargesResponse charges
    ) {
    }

    public record DimensionsResponse(
        Integer lengthMm,
        Integer widthMm,
        Integer heightMm
    ) {
    }

    public record ChargesResponse(
        Integer max,
        String recharge
    ) {
    }
}
