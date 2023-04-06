package be.julienbastin.customizablegacha.commands.subcommands.draw.prize;

import be.julienbastin.customizablegacha.CustomizableGacha;
import be.julienbastin.customizablegacha.commands.subcommands.SubCommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class SinglePriceSC extends SubCommand {

    public static final String PERMISSION = "czgacha.single.price";

    public SinglePriceSC(String parentCommand, CustomizableGacha plugin) {
        super(parentCommand, plugin, PERMISSION);
    }

    @Override
    public @NotNull String getValue() {
        return "price";
    }

    @Override
    public @NotNull String description() {
        return "Single draw price configuration";
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
            this.plugin.getSingleDraw().setPrice(price);
            this.plugin.getConfig().set("single-draw", this.plugin.getSingleDraw());
            this.plugin.saveConfig();
            return true;
        }
        sender.sendMessage("Usage : /czgacha single price <price>");
        return false;
    }

    @Override
    public @Nullable List<String> autoComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(args.length == 1) return List.of("<price>");
        return null;
    }
}
