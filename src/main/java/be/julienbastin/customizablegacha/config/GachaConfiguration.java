package be.julienbastin.customizablegacha.config;

import be.julienbastin.customizablegacha.CustomizableGacha;
import com.google.inject.Inject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

import static be.julienbastin.customizablegacha.CustomizableGacha.LOGGER;

public class GachaConfiguration {

    private CustomizableGacha plugin;

    private Map<Integer, Pack> packs;
    private Map<String, Rarity> rarities;

    @Inject
    public GachaConfiguration(CustomizableGacha plugin) {
        this.plugin = plugin;
    }

    public boolean loadRaritiesFromConfiguration() {
        this.rarities = new HashMap<>();
        return this.loadConfiguration("rarities");
    }

    public boolean loadPacksFromConfiguration() {
        this.packs = new HashMap<>();
        return this.loadConfiguration("packs");
    }

    private boolean loadConfiguration(String path) {
        List<?> objectList = plugin.getConfig().getList(path);
        boolean isConfigValid = true;
        if(objectList != null) {
            for(Object o : objectList) {
                if(o instanceof Map<?, ?> map) {
                    if(path.equals("packs") && !this.fillPackList(map)) {
                        isConfigValid = false;
                    } else if(path.equals("rarities") && this.fillRarityList(map)) {
                        isConfigValid = false;
                    }
                }
            }
        }
        return isConfigValid;
    }

    private boolean fillPackList(Map<?, ?> map) {
        Pack pack = new Pack(map, plugin);
        if(pack.isValueMapValid()) {
            this.packs.put(pack.getId(), pack);
        } else {
            LOGGER.log(Level.SEVERE, "Invalid pack {0}", map);
            return false;
        }
        return true;
    }

    private boolean fillRarityList(Map<?, ?> map) {
        Rarity rarity = new Rarity(map, plugin);
        if(rarity.isValueMapValid()) {
            this.rarities.put(rarity.getShortname(), rarity);
        } else {
            LOGGER.log(Level.SEVERE, "Invalid rarity {0}", map);
            return false;
        }
        return true;
    }

    public Map<Integer, Pack> getPacks() {
        return packs;
    }

    public void setPacks(Map<Integer, Pack> packs) {
        this.packs = packs;
    }

    public Map<String, Rarity> getRarities() {
        return rarities;
    }

    public void setRarities(Map<String, Rarity> rarities) {
        this.rarities = rarities;
    }
}
