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
import java.util.Optional;

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
        if(args.length < 4 || (args.length - 1) % 2 != 1) {
            return this.sendUsageMessage(sender, "Usage : /czgacha pack create <rarity> <pack-quantity> [<item name> <item-quantity>]");
        }
        Rarity rarity = null;
        if(!this.plugin.getRarities().containsKey(args[0])) {
            return this.sendUsageMessage(sender, "Incorrect rarity");
        } else {
            rarity = this.plugin.getRarities().get(args[0]);
        }
        Integer quantity = null;
        try {
            quantity = Integer.parseInt(args[1]);
        } catch (NumberFormatException e) {
            return this.sendUsageMessage(sender, "Incorrect pack quantity");
        }
        Pack pack = new Pack()
                .id(this.plugin.getNextPackId())
                .rarityShortName(rarity.getShortname())
                .rarity(rarity)
                .quantity(quantity);
        for(int i = 2; i+1 < args.length; i = i + 2) {
            Optional<ItemStack> optionalItemStack = this.getPackFromArguments(args[i], args[i+1], rarity, sender);
            if(optionalItemStack.isPresent()) {
                pack.addItemStack(optionalItemStack.get());
            } else {
                return false;
            }
        }
        this.plugin.getPacks().put(pack.getId(), pack);
        this.plugin.getConfig().set("packs", this.plugin.getPacks().values().stream().toList());
        this.plugin.saveConfig();
        sender.sendMessage("Pack created!");
        return true;
    }

    private Optional<ItemStack> getPackFromArguments(String itemName, String quantityStr, Rarity rarity, CommandSender sender) {
        Material material = Material.getMaterial(itemName);
        if(material == null) {
            sender.sendMessage("Incorrect material");
            return Optional.empty();
        }
        int quantity = -1;
        try {
            quantity = Integer.parseInt(quantityStr);
            if(quantity <= 0 || quantity > 640) {
                sender.sendMessage("Quantity should be between 1 and 640");
                return Optional.empty();
            }
        } catch (NumberFormatException e) {
            sender.sendMessage("You must enter a number as quantity");
            return Optional.empty();
        }
        return Optional.of(new ItemStack(material, quantity));
    }

    private boolean sendUsageMessage(@NotNull CommandSender sender, @NotNull String message) {
        sender.sendMessage(message);
        return false;
    }

    @Override
    public @Nullable List<String> autoComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(args.length == 1) {
            return this.plugin.getRarities().keySet().stream().toList();
        } else if (args.length == 2) {
            return List.of("<pack-quantity>");
        } else if(args.length % 2 == 1) {
            return Arrays
                    .stream(Material.values())
                    .map(Enum::name)
                    .filter(s -> {
                        if(StringUtils.isBlank(args[args.length - 1])) {
                            return true;
                        } else {
                            return s.startsWith(args[args.length - 1]);
                        }
                    }).toList();
        } else if(StringUtils.isBlank(args[args.length - 1])) {
            return Collections.singletonList("<item-quantity>");
        }
        return Collections.emptyList();
    }
}
