package be.julienbastin.customizablegacha.config;

import be.julienbastin.customizablegacha.CustomizableGacha;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.function.BiPredicate;
import java.util.logging.Level;

import static be.julienbastin.customizablegacha.CustomizableGacha.LOGGER;

public abstract class ConfigurationModel implements ConfigurationSerializable {

    protected final Map<?, ?> valueMap;
    protected final Map<Object, BiPredicate<Object, CustomizableGacha>> validators;
    protected final boolean isValueMapValid;
    protected CustomizableGacha customizableGacha;

    protected ConfigurationModel(@NotNull Map<?, ?> valueMap, @NotNull Map<Object, BiPredicate<Object, CustomizableGacha>> validators, CustomizableGacha customizableGacha) {
        this.valueMap = valueMap;
        this.validators = validators;
        this.customizableGacha = customizableGacha;
        this.isValueMapValid = checkIfValueMapIsValid();
        if(this.isValueMapValid) {
            this.fillValues();
        }
    }

    protected abstract void fillValues();

    private boolean checkIfValueMapIsValid() {
        boolean areAllKeysPresent = this.valueMap.keySet().containsAll(validators.keySet());
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

    private boolean isValueMapEntryValid(Map.Entry<Object, BiPredicate<Object, CustomizableGacha>> validatorEntry) {
        return validatorEntry.getValue().test(valueMap.get(validatorEntry.getKey()), this.customizableGacha);
    }

    public boolean isValueMapValid() {
        return isValueMapValid;
    }
}
