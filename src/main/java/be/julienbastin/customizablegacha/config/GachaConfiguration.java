package be.julienbastin.customizablegacha.config;

import be.julienbastin.customizablegacha.CustomizableGacha;
import com.google.inject.Inject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

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
        return this.loadConfiguration("rarities") && this.isTotalProbabilityEqualTo100();
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
                if(o instanceof Rarity rarity) {
                    this.rarities.put(rarity.getShortname(), rarity);
                } else if(o instanceof Pack pack) {
                    this.packs.put(pack.getId(), pack);
                    Rarity linkedRarity = this.rarities.get(pack.getRarityShortName());
                    pack.setRarity(linkedRarity);
                    linkedRarity.addPack(pack);
                }
            }
        }
        return isConfigValid;
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

    private boolean isTotalProbabilityEqualTo100() {
       int totalProbability = this.rarities.values()
                .stream()
                .map(Rarity::getProbability)
                .reduce(0, Integer::sum);
        if(totalProbability != 100) {
            this.plugin.getLogger().log(Level.WARNING, "The total probability of the rarities should be equal to 100!");
            this.plugin.getLogger().log(Level.WARNING, "The total probability of your config is equal to " + totalProbability);
            return false;
        }
        return true;
    }
}
