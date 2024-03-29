package be.julienbastin.customizablegacha.utils;

import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ListUtils {

    public static List<ItemStack> loadItemStackList(List<?> list) {
        List<ItemStack> ret = new ArrayList<>();
        for(Object o : list) {
            if(o instanceof Map<?, ?> map) {
                ret.add(ItemStack.deserialize((Map<String, Object>) map));
            } else if(o instanceof ItemStack itemStack) {
                ret.add(itemStack);
            }
        }
        return ret;
    }
}
