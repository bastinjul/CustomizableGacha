package be.julienbastin.customizablegacha.config;

import be.julienbastin.customizablegacha.CustomizableGacha;
import be.julienbastin.customizablegacha.utils.ListUtils;
import org.bukkit.configuration.serialization.SerializableAs;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.function.Consumer;
import java.util.logging.Level;

import static be.julienbastin.customizablegacha.CustomizableGacha.LOGGER;

@SerializableAs("Pack")
public class Pack extends ConfigurationModel {

    private static final String ID_KEY = "id";
    private static final String ITEMS_KEY = "items";
    public static final String RARITY_KEY = "rarity";

    private Integer id;
    private List<ItemStack> itemStackList;
    private String rarityShortName;
    private Rarity rarity;

    public Pack() {}

    public Pack(Map<?, ?> valueMap) {
        super(
                valueMap,
                Map.of(
                        ID_KEY, Pack::isIdValid,
                        ITEMS_KEY, Pack::isItemStackValid
                ),
                Set.of(ID_KEY, ITEMS_KEY, RARITY_KEY)
        );
    }

    @Override
    public @NotNull Map<String, Object> serialize() {
        return Map.of(
                ID_KEY, id,
                ITEMS_KEY, itemStackList,
                RARITY_KEY, rarityShortName
        );
    }

    @Override
    protected void fillValues() {
        final Map<String, Consumer<Object>> setters = Map.of(
                ID_KEY, val -> this.id = val instanceof Integer i ? i : null,
                ITEMS_KEY, val -> this.itemStackList = val instanceof List<?> list ? ListUtils.loadItemStackList(list) : null,
                RARITY_KEY, val -> this.rarityShortName = val instanceof String s ? s : null
        );
        valueMap.forEach((key1, value) -> {
            if (key1 instanceof String key && setters.containsKey(key)) {
                setters.get(key).accept(value);
            }
        });
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public static boolean isIdValid(Object value) {
        if (!(value instanceof Integer)) {
            LOGGER.log(Level.WARNING, "Pack's id should be a number. Got value {0}", value);
            return false;
        }
        return true;
    }

    public Pack id(Integer id) {
        this.id = id;
        return this;
    }

    public List<ItemStack> getItemStackList() {
        return itemStackList;
    }

    public void setItemStackList(List<ItemStack> itemStackList) {
        this.itemStackList = itemStackList;
    }

    public static boolean isItemStackValid(Object value) {
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

    public Pack addItemStack(ItemStack itemStack) {
        if(this.itemStackList == null) this.itemStackList = new ArrayList<>();
        this.itemStackList.add(itemStack);
        return this;
    }

    public Pack addAllItemStack(ItemStack... itemStackList) {
        if(this.itemStackList == null) this.itemStackList = new ArrayList<>();
        this.itemStackList.addAll(Arrays.asList(itemStackList));
        return this;
    }

    public String getRarityShortName() {
        return rarityShortName;
    }

    public void setRarityShortName(String rarityShortName) {
        this.rarityShortName = rarityShortName;
    }

    public boolean checkRarityAndAddItToPackIfValid(Object value, CustomizableGacha plugin) {
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

    public Pack rarityShortName(String rarityShortName) {
        this.rarityShortName = rarityShortName;
        return this;
    }

    public Rarity getRarity() {
        return rarity;
    }

    public void setRarity(Rarity rarity) {
        this.rarity = rarity;
    }

    public Pack rarity(Rarity rarity) {
        this.rarity = rarity;
        return this;
    }

    @Override
    public String toString() {
        return "[id=" + id + "," +
                "items=" + itemStackList + "," +
                "rarity=" + rarityShortName + "]";
    }
}
