package be.julienbastin.customizablegacha.config;

import be.julienbastin.customizablegacha.CustomizableGacha;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.logging.Level;

import static be.julienbastin.customizablegacha.CustomizableGacha.LOGGER;

public class Rarity extends ConfigurationModel {

    private static final String SHORTNAME_KEY = "shortname";
    private static final String NAME_KEY = "name";
    private static final String PROBABILITY_KEY = "probability";

    private String shortname;
    private String name;
    private Integer probability;
    private final List<Pack> packs;

    public Rarity(@NotNull Map<?, ?> valueMap, CustomizableGacha plugin) {
        super(
                valueMap,
                Map.of(
                        SHORTNAME_KEY, Rarity::isShortNameValid,
                        NAME_KEY, Rarity::isNameValid,
                        PROBABILITY_KEY, Rarity::isProbabilityValid
                ),
                plugin
        );
        this.packs = new ArrayList<>();
    }

    @Override
    public @NotNull Map<String, Object> serialize() {
        return Map.of(
                SHORTNAME_KEY, shortname,
                NAME_KEY, name,
                PROBABILITY_KEY, probability
        );
    }

    @Override
    protected void fillValues() {
        final  Map<String, Consumer<Object>> setters = Map.of(
                SHORTNAME_KEY, val -> this.shortname = val instanceof String s ? s : null,
                NAME_KEY, val -> this.name = val instanceof String s ? s : null,
                PROBABILITY_KEY, val -> this.probability = val instanceof Integer i ? i : null
        );
        valueMap.forEach((key1, value) -> {
            if (key1 instanceof String key && setters.containsKey(key)) {
                setters.get(key).accept(value);
            }
        });
    }

    public String getShortname() {
        return shortname;
    }

    public void setShortname(String shortname) {
        this.shortname = shortname;
    }

    public static boolean isShortNameValid(Object value, CustomizableGacha plugin) {
        if(!(value instanceof String)) {
            LOGGER.log(Level.WARNING, "Rarity's shortname should be a String. Got value {0}", value);
            return false;
        }
        return true;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static boolean isNameValid(Object value, CustomizableGacha plugin) {
        if(!(value instanceof String)) {
            LOGGER.log(Level.WARNING, "Rarity's name should be a String. Got value {0}", value);
            return false;
        }
        return true;
    }

    public Integer getProbability() {
        return probability;
    }

    public void setProbability(Integer probability) {
        this.probability = probability;
    }

    public static boolean isProbabilityValid(Object value, CustomizableGacha plugin) {
        if(!(value instanceof Integer i) || i < 0 || i > 100) {
            LOGGER.log(Level.WARNING, "Rarity's probability should be a number between 0 and 100. Got value {0}", value);
            return false;
        }
        return true;
    }

    @NotNull
    public List<Pack> getPacks() {
        return packs;
    }

    public void addPack(@NotNull Pack pack) {
        this.packs.add(pack);
    }

    @Override
    public String toString() {
        return "Rarity{" +
                "shortname='" + shortname + '\'' +
                ", name='" + name + '\'' +
                ", probability=" + probability +
                '}';
    }
}
