package be.julienbastin.customizablegacha.config;

import be.julienbastin.customizablegacha.CustomizableGacha;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.SerializableAs;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.logging.Level;

@SerializableAs("MultiDraw")
public class MultiDraw implements ConfigurationSerializable {

    private static final String PRICE_KEY = "price";
    private static final String QUANTITY_KEY = "quantity";

    private Integer price;
    private Integer quantity;

    public MultiDraw(Map<?, ?> valueMap) {
        valueMap.forEach((key, val) -> {
            if(key.equals(PRICE_KEY) && val instanceof Integer i) this.price = i;
            if(key.equals(QUANTITY_KEY) && val instanceof Integer i) this.quantity = i;
        });
    }

    @Override
    public @NotNull Map<String, Object> serialize() {
        return Map.of(
                PRICE_KEY, price,
                QUANTITY_KEY, quantity
        );
    }

    public boolean isValid() {
        if(this.price == null || this.price <= 0) {
            CustomizableGacha.LOGGER.log(Level.WARNING, "multi-draw price not valid");
            return false;
        }
        if(this.quantity == null || this.quantity <= 0) {
            CustomizableGacha.LOGGER.log(Level.WARNING, "multi-draw quantity not valid");
            return false;
        }
        return true;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "MultiDraw{" +
                "price=" + price +
                ", quantity=" + quantity +
                '}';
    }
}
