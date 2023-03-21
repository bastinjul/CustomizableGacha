package be.julienbastin.customizablegacha.config;

import be.julienbastin.customizablegacha.CustomizableGacha;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;
import java.util.logging.Level;

import static be.julienbastin.customizablegacha.CustomizableGacha.LOGGER;

public abstract class ConfigurationModel implements ConfigurationSerializable {

    protected final Map<?, ?> valueMap;
    protected final Map<Object, Predicate<Object>> validators;
    protected final boolean isValueMapValid;

    protected CustomizableGacha plugin;
    protected Set<Object> keySet;
    protected ConfigurationModel() {
        this.valueMap = null;
        this.validators = null;
        this.isValueMapValid = false;
    }

    protected ConfigurationModel(@NotNull Map<?, ?> valueMap, @NotNull Map<Object, Predicate<Object>> validators, Set<Object> keySet) {
        this.valueMap = valueMap;
        this.validators = validators;
        this.keySet = keySet;
        this.isValueMapValid = checkIfValueMapIsValid();
        if(this.isValueMapValid) {
            this.fillValues();
        }
    }

    protected abstract void fillValues();

    private boolean checkIfValueMapIsValid() {
        boolean areAllKeysPresent = this.keySet.containsAll(validators.keySet());
        if(!areAllKeysPresent) {
            LOGGER.log(
                    Level.WARNING,
                    "The following keys should be present {0} for object {1}",
                    new Object[]{validators.keySet(), valueMap});
            return false;
        }
        return validators.entrySet()
                .stream()
                .map(this::isValueMapEntryValid)
                .reduce(true, Boolean::logicalAnd);
    }

    private boolean isValueMapEntryValid(Map.Entry<Object, Predicate<Object>> validatorEntry) {
        return validatorEntry.getValue().test(valueMap.get(validatorEntry.getKey()));
    }

    public boolean isValueMapValid() {
        return isValueMapValid;
    }

    public CustomizableGacha getPlugin() {
        return plugin;
    }

    public void setPlugin(CustomizableGacha plugin) {
        this.plugin = plugin;
    }
}
