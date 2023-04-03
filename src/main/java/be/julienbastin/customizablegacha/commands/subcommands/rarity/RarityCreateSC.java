package be.julienbastin.customizablegacha.commands.subcommands.rarity;

import be.julienbastin.customizablegacha.CustomizableGacha;
import be.julienbastin.customizablegacha.commands.subcommands.SubCommand;
import be.julienbastin.customizablegacha.config.Rarity;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class RarityCreateSC extends SubCommand {

    public static final String PERMISSION = "czgacha.rarity.create";

    public RarityCreateSC(String parentCommand, CustomizableGacha plugin) {
        super(parentCommand, plugin, PERMISSION);
    }

    @Override
    public @NotNull String getValue() {
        return "create";
    }

    @Override
    public @NotNull String description() {
        return "Create new pack";
    }

    @Override
    public boolean perform(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(args.length != 2) {
            sender.sendMessage("Usage : /czgacha rarity create <name> <shortname>");
            return false;
        }
        if(this.plugin.getRarities().values().stream().anyMatch(r -> r.getName().equals(args[0]))) {
            sender.sendMessage("The name already exists");
            return false;
        }
        if(this.plugin.getRarities().containsKey(args[1])) {
            sender.sendMessage("The shortname already exists");
            return false;
        }
        Rarity rarity = new Rarity()
                .name(args[0])
                .shortname(args[1])
                .probability(0);
        this.plugin.getRarities().put(args[1], rarity);
        this.plugin.getConfig().set("rarities", this.plugin.getRarities().values().stream().toList());
        this.plugin.saveConfig();
        sender.sendMessage(ChatColor.DARK_GREEN + "Rarity created with probability 0!");
        sender.sendMessage("To modify the probability, use the modify functionality.");
        sender.sendMessage(RarityModifySC.getUsage());
        return true;
    }

    @Override
    public @Nullable List<String> autoComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(args.length == 1) {
            return List.of("<name>");
        } else if(args.length == 2) {
            return List.of("<shortname>");
        }
        return null;
    }
}
