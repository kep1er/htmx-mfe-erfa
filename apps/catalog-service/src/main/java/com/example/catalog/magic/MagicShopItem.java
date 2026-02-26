package com.example.catalog.magic;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "magic_shop_items")
public class MagicShopItem {

    @Id
    @Column(nullable = false, length = 64)
    private String id;

    @Column(nullable = false, length = 255)
    private String name;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    @Column(nullable = false)
    private Integer stock;

    @Column(nullable = false, length = 120)
    private String category;

    @Column(nullable = false, length = 16)
    private Rarity rarity;

    @Column(columnDefinition = "text")
    private String description;

    @Column(length = 512)
    private String image;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "magic_shop_item_tags", joinColumns = @JoinColumn(name = "item_id"))
    @Column(name = "tag", nullable = false, length = 120)
    private Set<String> tags = new LinkedHashSet<>();

    @Column(columnDefinition = "text")
    private String lore;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "magic_shop_item_effects", joinColumns = @JoinColumn(name = "item_id"))
    @Column(name = "effect", nullable = false, columnDefinition = "text")
    private Set<String> effects = new LinkedHashSet<>();

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "magic_shop_item_limitations", joinColumns = @JoinColumn(name = "item_id"))
    @Column(name = "limitation", nullable = false, columnDefinition = "text")
    private Set<String> limitations = new LinkedHashSet<>();

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "magic_shop_item_instructions", joinColumns = @JoinColumn(name = "item_id"))
    @Column(name = "instruction", nullable = false, columnDefinition = "text")
    private Set<String> instructions = new LinkedHashSet<>();

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "magic_shop_item_warnings", joinColumns = @JoinColumn(name = "item_id"))
    @Column(name = "warning", nullable = false, columnDefinition = "text")
    private Set<String> warnings = new LinkedHashSet<>();

    @Column(length = 255)
    private String maker;

    @Column(length = 255)
    private String origin;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "magic_shop_item_materials", joinColumns = @JoinColumn(name = "item_id"))
    @Column(name = "material", nullable = false, length = 255)
    private Set<String> materials = new LinkedHashSet<>();

    @Column(name = "weight_grams")
    private Integer weightGrams;

    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "lengthMm", column = @Column(name = "dimensions_length_mm")),
        @AttributeOverride(name = "widthMm", column = @Column(name = "dimensions_width_mm")),
        @AttributeOverride(name = "heightMm", column = @Column(name = "dimensions_height_mm"))
    })
    private Dimensions dimensions;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "magic_shop_item_related", joinColumns = @JoinColumn(name = "item_id"))
    @Column(name = "related_item_id", nullable = false, length = 64)
    private Set<String> relatedItemIds = new LinkedHashSet<>();

    @Column(name = "released_at")
    private OffsetDateTime releasedAt;

    @Column(name = "attunement_required", nullable = false)
    private boolean attunementRequired;

    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "max", column = @Column(name = "charges_max")),
        @AttributeOverride(name = "recharge", column = @Column(name = "charges_recharge", length = 120))
    })
    private Charges charges;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Rarity getRarity() {
        return rarity;
    }

    public void setRarity(Rarity rarity) {
        this.rarity = rarity;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Set<String> getTags() {
        return tags;
    }

    public void setTags(Set<String> tags) {
        this.tags = tags;
    }

    public String getLore() {
        return lore;
    }

    public void setLore(String lore) {
        this.lore = lore;
    }

    public Set<String> getEffects() {
        return effects;
    }

    public void setEffects(Set<String> effects) {
        this.effects = effects;
    }

    public Set<String> getLimitations() {
        return limitations;
    }

    public void setLimitations(Set<String> limitations) {
        this.limitations = limitations;
    }

    public Set<String> getInstructions() {
        return instructions;
    }

    public void setInstructions(Set<String> instructions) {
        this.instructions = instructions;
    }

    public Set<String> getWarnings() {
        return warnings;
    }

    public void setWarnings(Set<String> warnings) {
        this.warnings = warnings;
    }

    public String getMaker() {
        return maker;
    }

    public void setMaker(String maker) {
        this.maker = maker;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public Set<String> getMaterials() {
        return materials;
    }

    public void setMaterials(Set<String> materials) {
        this.materials = materials;
    }

    public Integer getWeightGrams() {
        return weightGrams;
    }

    public void setWeightGrams(Integer weightGrams) {
        this.weightGrams = weightGrams;
    }

    public Dimensions getDimensions() {
        return dimensions;
    }

    public void setDimensions(Dimensions dimensions) {
        this.dimensions = dimensions;
    }

    public Set<String> getRelatedItemIds() {
        return relatedItemIds;
    }

    public void setRelatedItemIds(Set<String> relatedItemIds) {
        this.relatedItemIds = relatedItemIds;
    }

    public OffsetDateTime getReleasedAt() {
        return releasedAt;
    }

    public void setReleasedAt(OffsetDateTime releasedAt) {
        this.releasedAt = releasedAt;
    }

    public boolean isAttunementRequired() {
        return attunementRequired;
    }

    public void setAttunementRequired(boolean attunementRequired) {
        this.attunementRequired = attunementRequired;
    }

    public Charges getCharges() {
        return charges;
    }

    public void setCharges(Charges charges) {
        this.charges = charges;
    }
}
