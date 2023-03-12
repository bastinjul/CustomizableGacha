package be.julienbastin.customizablegacha;

import be.julienbastin.customizablegacha.commands.GachaCommand;
import be.julienbastin.customizablegacha.config.Pack;
import be.julienbastin.customizablegacha.config.Rarity;
import com.google.inject.Inject;
import com.google.inject.Injector;
import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.plugin.PluginLogger;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.java.annotation.dependency.Dependency;
import org.bukkit.plugin.java.annotation.dependency.SoftDependency;
import org.bukkit.plugin.java.annotation.plugin.*;
import org.bukkit.plugin.java.annotation.plugin.author.Author;
import org.bukkit.plugin.java.annotation.plugin.author.Authors;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

@Plugin(name = "CustomizableGacha", version = "1.0.0-SNAPSHOT")
@Description(value = "Plugin to create customizable gacha draws")
@Authors({
    @Author("bastinjul (Julien Bastin)")
})
@Website("https://julienbastin.be")
@ApiVersion(ApiVersion.Target.v1_19)
@LogPrefix("CustomizableGacha")
@Dependency(value = "Vault")
@SoftDependency(value = "Vault")
public class CustomizableGacha extends JavaPlugin {

    public static final Logger LOGGER = PluginLogger.getLogger("CustomizableGacha");
    private static Economy econ = null;
    private static Permission permission = null;
    private static Chat chat = null;
    private List<Pack> packs;
    private List<Rarity> rarities;

    @Inject
    private GachaCommand gachaCommand;

    @Override
    public void onDisable() {
        LOGGER.log(Level.WARNING, "Disabled Version {0}", super.getDescription().getVersion());
    }

    @Override
    public void onEnable() {
        if(!setupEconomy()) {
            LOGGER.log(Level.SEVERE, "Disabled due to no Vault dependency found! " +
                    "You need to add an economy plugin that implements Vault, such as EssentialsX");
            super.getServer().getPluginManager().disablePlugin(this);
            return;
        }
        setupPermissions(); //if mandatory, disable plugin
        setupChat(); //if mandatory, disable plugin
        BinderModule binderModule = new BinderModule(this);
        Injector injector = binderModule.createInjector();
        injector.injectMembers(this);
        saveDefaultConfig();
        registerSerializers();
        registerCommands();
        if(!loadRaritiesFromConfiguration() && !loadPacksFromConfiguration()) {
            super.getServer().getPluginManager().disablePlugin(this);
        }
    }

    private void registerCommands() {
        this.getCommand(GachaCommand.getCommand()).setExecutor(this.gachaCommand);
    }

    private boolean setupEconomy() {
        if(super.getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = super.getServer().getServicesManager().getRegistration(Economy.class);
        if(rsp == null) {
            return false;
        }
        econ = rsp.getProvider();
        return true;
    }

    private boolean setupPermissions() {
        RegisteredServiceProvider<Permission> rsp = super.getServer().getServicesManager().getRegistration(Permission.class);
        if(rsp == null) return false;
        permission = rsp.getProvider();
        return true;
    }

    private boolean setupChat() {
        RegisteredServiceProvider<Chat> rsp = super.getServer().getServicesManager().getRegistration(Chat.class);
        if(rsp == null) return false;
        chat = rsp.getProvider();
        return true;
    }

    private void registerSerializers() {
        ConfigurationSerialization.registerClass(Pack.class);
        ConfigurationSerialization.registerClass(Rarity.class);
    }

    private boolean loadRaritiesFromConfiguration() {
        this.rarities = new ArrayList<>();
        boolean isValid = this.loadConfiguration("rarities");
        LOGGER.log(Level.INFO, "List of rarities : {0}", this.rarities);
        return isValid;
    }

    private boolean loadPacksFromConfiguration() {
        this.packs = new ArrayList<>();
        boolean isValid = this.loadConfiguration("packs");
        LOGGER.log(Level.INFO, "List of packs : {0}", this.packs);
        return isValid;
    }

    private boolean loadConfiguration(String path) {
        List<?> objectList = getConfig().getList(path);
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
        Pack pack = new Pack(map, this);
        if(pack.isValueMapValid()) {
            this.packs.add(pack);
        } else {
            LOGGER.log(Level.SEVERE, "Invalid pack {0}", map);
            return false;
        }
        return true;
    }

    private boolean fillRarityList(Map<?, ?> map) {
        Rarity rarity = new Rarity(map, this);
        if(rarity.isValueMapValid()) {
            this.rarities.add(rarity);
        } else {
            LOGGER.log(Level.SEVERE, "Invalid rarity {0}", map);
            return false;
        }
        return true;
    }

    public static Economy getEcon() {
        return econ;
    }

    public static Permission getPermission() {
        return permission;
    }

    public static Chat getChat() {
        return chat;
    }
}
