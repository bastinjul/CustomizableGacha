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

    private SingleDraw singleDraw;

    private MultiDraw multiDraw;

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

    public SingleDraw getSingleDraw() {
        return singleDraw;
    }

    public void setSingleDraw(SingleDraw singleDraw) {
        this.singleDraw = singleDraw;
    }

    public MultiDraw getMultiDraw() {
        return multiDraw;
    }

    public void setMultiDraw(MultiDraw multiDraw) {
        this.multiDraw = multiDraw;
    }

    public boolean loadMultiDraw() {
        Object o = this.plugin.getConfig().get("multi-draw");
        if(o instanceof MultiDraw m && m.isValid()) {
            this.multiDraw = m;
            this.plugin.getLogger().log(Level.INFO, "Multi Draw : {0}", multiDraw);
            return true;
        }
        this.plugin.getLogger().log(Level.WARNING, "multi-draw configuration is not valid");
        return false;
    }

    public boolean loadSingleDraw() {
        Object o = this.plugin.getConfig().get("single-draw");
        if(o instanceof SingleDraw s && s.isValid()) {
            this.singleDraw = s;
            this.plugin.getLogger().log(Level.INFO, "Single Draw : {0}", singleDraw);
            return true;
        }
        this.plugin.getLogger().log(Level.WARNING, "single-draw configuration is not valid");
        return false;
    }
}
