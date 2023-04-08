package be.julienbastin.customizablegacha;

import be.julienbastin.customizablegacha.commands.GachaCommand;
import be.julienbastin.customizablegacha.config.*;
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

import java.util.Map;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

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

    @Inject
    private GachaCommand gachaCommand;
    @Inject
    private GachaConfiguration gachaConfiguration;

    @Override
    public void onDisable() {
        LOGGER.log(Level.WARNING, "Disabled Version {0}", super.getDescription().getVersion());
    }

    @Override
    public void onEnable() {
        if(!setupEconomy()) {
            this.disablePlugin("Disabled due to no Vault dependency found! " +
                    "You need to add an economy plugin that implements Vault, such as EssentialsX.");
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
        boolean isRarityConfigValid = this.gachaConfiguration.loadRaritiesFromConfiguration();
        boolean isPackConfigValid = this.gachaConfiguration.loadPacksFromConfiguration();
        boolean isMultiDrawValid = this.gachaConfiguration.loadMultiDraw();
        boolean isSingleDrawValid = this.gachaConfiguration.loadSingleDraw();
        if(!isRarityConfigValid || !isPackConfigValid || !isMultiDrawValid || !isSingleDrawValid) {
            this.disablePlugin("Your configuration is invalid! Please check the previous messages to see what is wrong.");
        }
    }

    private void disablePlugin(String message) {
        LOGGER.log(Level.SEVERE, message);
        super.getServer().getPluginManager().disablePlugin(this);
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
        ConfigurationSerialization.registerClass(Pack.class, "Pack");
        ConfigurationSerialization.registerClass(Rarity.class, "Rarity");
        ConfigurationSerialization.registerClass(MultiDraw.class, "MultiDraw");
        ConfigurationSerialization.registerClass(SingleDraw.class, "SingleDraw");
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

    public Map<Integer, Pack> getPacks() {
        return this.gachaConfiguration.getPacks();
    }

    public String getAllPacksString() {
        return this.gachaConfiguration.getPacks()
                .values()
                .stream()
                .map(Pack::toString)
                .collect(Collectors.joining("\n"));
    }

    public String getAllRaritiesString() {
        return this.gachaConfiguration.getRarities()
                .values()
                .stream()
                .map(Object::toString)
                .collect(Collectors.joining("\n"));
    }

    public String getPacksByRarityString(String rarityShortname) {
        return this.gachaConfiguration.getPacks()
                .values()
                .stream()
                .filter(pack -> pack.getRarityShortName().equals(rarityShortname))
                .map(Pack::toString)
                .collect(Collectors.joining("\n"));
    }

    public Integer getNextPackId() {
        Optional<Integer> maxId = this.gachaConfiguration.getPacks()
                .values()
                .stream()
                .map(Pack::getId)
                .max(Integer::compareTo);
        return maxId.map(integer -> integer + 1).orElse(1);
    }

    public GachaConfiguration getGachaConfiguration() {
        return gachaConfiguration;
    }

    public SingleDraw getSingleDraw() {
        return this.gachaConfiguration.getSingleDraw();
    }

    public MultiDraw getMultiDraw() {
        return this.gachaConfiguration.getMultiDraw();
    }

    public Map<String, Rarity> getRarities() {
        return this.gachaConfiguration.getRarities();
    }
}
