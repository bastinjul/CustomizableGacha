package be.julienbastin.customizablegacha.commands.subcommands.draw.prize;

import be.julienbastin.customizablegacha.CustomizableGacha;
import be.julienbastin.customizablegacha.commands.subcommands.SubCommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class MultiPriceSC extends SubCommand {

    public static final String PERMISSION = "czgacha.multi.price";

    public MultiPriceSC(String parentCommand, CustomizableGacha plugin) {
        super(parentCommand, plugin, PERMISSION);
    }

    @Override
    public @NotNull String getValue() {
        return "price";
    }

    @Override
    public @NotNull String description() {
        return "Multi Draw price configuration";
    }

    @Override
    public boolean perform(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(args.length == 1) {
            Integer price = null;
            try {
                price = Integer.parseInt(args[0]);
            } catch (NumberFormatException e) {
                sender.sendMessage("Not a correct price.");
                return false;
            }
            this.plugin.getMultiDraw().setPrice(price);
            this.plugin.getConfig().set("multi-draw", this.plugin.getMultiDraw());
            this.plugin.saveConfig();
            return true;
        }
        sender.sendMessage("Usage : /czgacha multi price <price>");
        return false;
    }

    @Override
    public @Nullable List<String> autoComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(args.length == 1) return List.of("<price>");
        return null;
    }
}
