package be.julienbastin.customizablegacha.config.models;

import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

public class Rarity implements ConfigurationSerializable {

    private String shortname;
    private String name;
    private Integer probability;

    @Override
    public @NotNull Map<String, Object> serialize() {
        return Map.of(
                "shortname", shortname,
                "name", name,
                "probability", probability
        );
    }

    public String getShortname() {
        return shortname;
    }

    public void setShortname(String shortname) {
        this.shortname = shortname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getProbability() {
        return probability;
    }

    public void setProbability(Integer probability) {
        this.probability = probability;
    }
}
