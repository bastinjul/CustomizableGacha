package be.julienbastin.customizablegacha;

import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.plugin.PluginLogger;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Level;
import java.util.logging.Logger;

public class CustomizableGacha extends JavaPlugin {

    public static final Logger LOGGER = PluginLogger.getLogger("CustomizableGacha");
    private static Economy econ = null;
    private static Permission permission = null;
    private static Chat chat = null;

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
