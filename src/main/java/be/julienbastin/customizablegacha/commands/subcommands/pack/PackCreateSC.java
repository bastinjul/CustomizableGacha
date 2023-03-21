package be.julienbastin.customizablegacha.commands.subcommands.pack;

import be.julienbastin.customizablegacha.CustomizableGacha;
import be.julienbastin.customizablegacha.commands.subcommands.SubCommand;
import be.julienbastin.customizablegacha.config.Pack;
import be.julienbastin.customizablegacha.config.Rarity;
import org.apache.commons.lang3.StringUtils;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class PackCreateSC extends SubCommand {

    public static final String PERMISSION = "czgacha.pack.create";

    public PackCreateSC(String parentCommand, CustomizableGacha plugin) {
        super(parentCommand, plugin, PERMISSION);
    }

    @Override
    public @NotNull String getValue() {
        return "create";
    }

    @Override
    public @NotNull String description() {
        return "Create a new pack";
    }

    @Override
    public boolean perform(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(args.length != 3) {
            return this.sendUsageMessage(sender, "Usage : /czgacha pack create <rarity> <item name> <quantity>");
        }
        Rarity rarity = null;
        if(!this.plugin.getRarities().containsKey(args[0])) {
            return this.sendUsageMessage(sender, "Incorrect rarity");
        } else {
            rarity = this.plugin.getRarities().get(args[0]);
        }
        Material material = Material.getMaterial(args[1]);
        if(material == null) return this.sendUsageMessage(sender, "Incorrect material");
        int quantity = -1;
        try {
            quantity = Integer.parseInt(args[2]);
            if(quantity <= 0 || quantity > 640) {
                return sendUsageMessage(sender, "Quantity should be between 1 and 640");
            }
        } catch (NumberFormatException e) {
            return sendUsageMessage(sender, "You must enter a number as quantity");
        }
        Pack pack = new Pack()
                .id(this.plugin.getNextPackId())
                .addItemStack(new ItemStack(material, quantity))
                .rarityShortName(rarity.getShortname())
                .rarity(rarity);
        this.plugin.getPacks().put(pack.getId(), pack);
        this.plugin.getConfig().set("packs", this.plugin.getPacks().values().stream().toList());
        this.plugin.saveConfig();
        return true;
    }

    private boolean sendUsageMessage(@NotNull CommandSender sender, @NotNull String message) {
        sender.sendMessage(message);
        return false;
    }

    @Override
    public @Nullable List<String> autoComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(args.length == 1) {
            return this.plugin.getRarities().keySet().stream().toList();
        } else if(args.length == 2) {
            return Arrays
                    .stream(Material.values())
                    .map(Enum::name)
                    .filter(s -> {
                        if(StringUtils.isBlank(args[1])) {
                            return true;
                        } else {
                            return s.startsWith(args[1]);
                        }
                    }).toList();
        } else if(args.length == 3 && StringUtils.isBlank(args[2])) {
            return Collections.singletonList("<quantity>");
        }
        return Collections.emptyList();
    }
}
