package be.julienbastin.customizablegacha.config;

import be.julienbastin.customizablegacha.CustomizableGacha;
import be.julienbastin.customizablegacha.utils.ListUtils;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.logging.Level;

import static be.julienbastin.customizablegacha.CustomizableGacha.LOGGER;

public class Pack extends ConfigurationModel {

    private static final String ID_KEY = "id";
    private static final String ITEMS_KEY = "items";
    private static final String RARITY_KEY = "rarity";

    private Integer id;
    private List<ItemStack> itemStackList;
    private String rarityShortName;
    private Rarity rarity;

    public Pack(Map<?, ?> valueMap, CustomizableGacha plugin) {
        super(
                valueMap,
                Map.of(
                        ID_KEY, Pack::isIdValid,
                        ITEMS_KEY, Pack::isItemStackValid,
                        RARITY_KEY, Pack::isRarityValid
                ),
                plugin
        );
    }

    @Override
    public @NotNull Map<String, Object> serialize() {
        return Map.of(
                ID_KEY, id,
                ITEMS_KEY, itemStackList,
                RARITY_KEY, rarity.getShortname()
        );
    }

    @Override
    protected void fillValues() {
        final Map<String, Consumer<Object>> setters = Map.of(
                ID_KEY, val -> this.id = val instanceof Integer i ? i : null,
                ITEMS_KEY, val -> this.itemStackList = val instanceof List<?> list ? ListUtils.loadList(list) : null,
                RARITY_KEY, val -> this.rarityShortName = val instanceof String s ? s : null
        );
        valueMap.forEach((key1, value) -> {
            if (key1 instanceof String key && setters.containsKey(key)) {
                setters.get(key).accept(value);
            }
        });
        this.rarity = this.plugin.getRarities().get(this.rarityShortName);
        this.rarity.addPack(this);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public static boolean isIdValid(Object value, CustomizableGacha plugin) {
        if (!(value instanceof Integer)) {
            LOGGER.log(Level.WARNING, "Pack's id should be a number. Got value {0}", value);
            return false;
        }
        return true;
    }

    public List<ItemStack> getItemStackList() {
        return itemStackList;
    }

    public void setItemStackList(List<ItemStack> itemStackList) {
        this.itemStackList = itemStackList;
    }

    public static boolean isItemStackValid(Object value, CustomizableGacha plugin) {
        if(value instanceof List<?> list) {
            boolean areAllInstanceOfItemStack = true;
            for(Object o : list) {
                if(o instanceof Map<?,?> map) {
                    try {
                        ItemStack.deserialize((Map<String, Object>) map);
                    } catch (IllegalArgumentException e) {
                        LOGGER.log(Level.WARNING, "Impossible to find item {0}", map);
                        areAllInstanceOfItemStack = false;
                    }
                }
            }
            return areAllInstanceOfItemStack;
        }
        LOGGER.log(Level.WARNING, "Pack's items should be a list of ItemStack. Got value {0}", value);
        return false;
    }

    public String getRarityShortName() {
        return rarityShortName;
    }

    public void setRarityShortName(String rarityShortName) {
        this.rarityShortName = rarityShortName;
    }

    public static boolean isRarityValid(Object value, CustomizableGacha plugin) {
        if (value instanceof String s) {
            if(plugin.getRarities().keySet().stream()
                    .filter(rarity -> rarity.equals(s))
                    .findFirst()
                    .isEmpty()) {
                LOGGER.log(Level.WARNING, "Pack's rarity should be defined in rarities section. Unknown rarity {0}", s);
                return false;
            }
        } else {
            LOGGER.log(Level.WARNING, "Pack's rarity should be a String. Got value {0}", value);
            return false;
        }
        return true;
    }

    public Rarity getRarity() {
        return rarity;
    }

    public void setRarity(Rarity rarity) {
        this.rarity = rarity;
    }

    @Override
    public String toString() {
        return "[id=" + id + "," +
                "items=" + itemStackList + "," +
                "rarity=" + rarity.getName() + "]";
    }
}
