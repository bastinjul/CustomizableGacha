package be.julienbastin.customizablegacha.config.models;

import org.bukkit.Material;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

public class Pack implements ConfigurationSerializable {

    private Integer id;
    private Material item;
    private Integer quantity;
    private String rarityShortName;
    private Rarity rarity;

    public Pack(Map<?, ?> valueMap) {
        valueMap.forEach((key1, value) -> {
            if (key1 instanceof String key) {
                switch (key) {
                    case "id" -> {
                        if (value instanceof Integer l) {
                            this.id = l;
                        }
                    }
                    case "item" -> {
                        if (value instanceof String s) {
                            this.item = Material.getMaterial(s);
                        }
                    }
                    case "quantity" -> {
                        if (value instanceof Integer l) {
                            this.quantity = l;
                        }
                    }
                    case "rarity" -> {
                        if (value instanceof String s) {
                            this.rarityShortName = s;
                        }
                    }
                }
            }
        });
    }

    @Override
    public @NotNull Map<String, Object> serialize() {
        return Map.of(
                "id", id,
                "item", item.name(),
                "quantity", quantity,
                "rarity", rarity.getShortname()
        );
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Material getItem() {
        return item;
    }

    public void setItem(Material item) {
        this.item = item;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getRarityShortName() {
        return rarityShortName;
    }

    public void setRarityShortName(String rarityShortName) {
        this.rarityShortName = rarityShortName;
    }

    public Rarity getRarity() {
        return rarity;
    }

    public void setRarity(Rarity rarity) {
        this.rarity = rarity;
    }

    @Override
    public String toString() {
        return "Pack{" +
                "id=" + id +
                ", item=" + item +
                ", quantity=" + quantity +
                ", rarityShortName='" + rarityShortName + '\'' +
                ", rarity=" + rarity +
                '}';
    }
}
