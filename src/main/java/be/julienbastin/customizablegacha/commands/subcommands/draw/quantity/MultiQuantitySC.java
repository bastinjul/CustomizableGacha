package be.julienbastin.customizablegacha.commands.subcommands.draw.quantity;

import be.julienbastin.customizablegacha.CustomizableGacha;
import be.julienbastin.customizablegacha.commands.subcommands.SubCommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class MultiQuantitySC extends SubCommand {

    public static final String PERMISSION = "czgacha.multi.quantity";

    public MultiQuantitySC(String parentCommand, CustomizableGacha plugin) {
        super(parentCommand, plugin, PERMISSION);
    }

    @Override
    public @NotNull String getValue() {
        return "quantity";
    }

    @Override
    public @NotNull String description() {
        return "Quantity of pack for a multi draw configuration";
    }

    @Override
    public boolean perform(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(args.length == 1) {
            Integer quantity = null;
            try {
                quantity = Integer.parseInt(args[0]);
            } catch (NumberFormatException e) {
                sender.sendMessage("Not a correct quantity.");
                return false;
            }
            this.plugin.getMultiDraw().setQuantity(quantity);
            this.plugin.getConfig().set("multi-draw", this.plugin.getMultiDraw());
            this.plugin.saveConfig();
            return true;
        }
        sender.sendMessage("Usage : /czgacha multi quantity <quantity>");
        return false;
    }

    @Override
    public @Nullable List<String> autoComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(args.length == 1) return List.of("<quantity>");
        return null;
    }
}
