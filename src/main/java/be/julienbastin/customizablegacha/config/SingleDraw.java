package be.julienbastin.customizablegacha.config;

import be.julienbastin.customizablegacha.CustomizableGacha;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.SerializableAs;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.logging.Level;

@SerializableAs("SingleDraw")
public class SingleDraw implements ConfigurationSerializable {

    private static final String PRICE_KEY = "price";

    private Integer price;

    public SingleDraw(Map<?, ?> valueMap) {
        valueMap.forEach((key, val) -> {
            if(key.equals(PRICE_KEY) && val instanceof Integer i) this.price = i;
        });
    }

    @Override
    public @NotNull Map<String, Object> serialize() {
        return Map.of(PRICE_KEY, price);
    }

    public boolean isValid() {
        if(this.price == null || this.price <= 0) {
            CustomizableGacha.LOGGER.log(Level.WARNING, "single-draw price is not valid");
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

    @Override
    public String toString() {
        return "SingleDraw{" +
                "price=" + price +
                '}';
    }
}
