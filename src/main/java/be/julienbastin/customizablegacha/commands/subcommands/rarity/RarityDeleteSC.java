package be.julienbastin.customizablegacha.commands.subcommands.rarity;

import be.julienbastin.customizablegacha.CustomizableGacha;
import be.julienbastin.customizablegacha.commands.subcommands.SubCommand;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class RarityDeleteSC extends SubCommand {

    public static final String PERMISSION = "czgacha.rarity.delete";

    public RarityDeleteSC(String parentCommand, CustomizableGacha plugin) {
        super(parentCommand, plugin, PERMISSION);
    }

    @Override
    public @NotNull String getValue() {
        return "delete";
    }

    @Override
    public @NotNull String description() {
        return "Delete existing rarity";
    }

    @Override
    public boolean perform(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(args.length != 1) {
            sender.sendMessage("Usage : /czgacha rarity delete <rarity-id>");
            return false;
        }
        if(!this.plugin.getRarities().containsKey(args[0])) {
            sender.sendMessage(ChatColor.RED + "Rarity does not exist");
            sender.sendMessage("Usage : /czgacha rarity delete <rarity-id>");
            return false;
        }
        this.plugin.getRarities().remove(args[0]);
        this.plugin.getConfig().set("rarities", this.plugin.getRarities().values().stream().toList());
        this.plugin.saveConfig();
        sender.sendMessage("Rarity deleted!");
        return true;
    }

    @Override
    public @Nullable List<String> autoComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(args.length == 1) {
            return this.plugin.getRarities().keySet().stream().map(Object::toString).toList();
        }
        return null;
    }
}
