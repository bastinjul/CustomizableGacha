package be.julienbastin.customizablegacha.commands.subcommands.draw;

import be.julienbastin.customizablegacha.CustomizableGacha;
import be.julienbastin.customizablegacha.commands.subcommands.draw.prize.MultiPriceSC;
import be.julienbastin.customizablegacha.commands.subcommands.draw.quantity.MultiQuantitySC;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public non-sealed class MultiSC extends DrawSC {

    public static final String PERMISSION = "czgacha.multi.draw";

    public MultiSC(String parentCommand, CustomizableGacha plugin) {
        super(parentCommand, plugin, PERMISSION);
        this.subCommands = List.of(
                new MultiPriceSC(syntax(), plugin),
                new MultiQuantitySC(syntax(), plugin)
        );
    }

    @Override
    public @NotNull String getValue() {
        return "multi";
    }

    @Override
    public @NotNull String description() {
        return "Multi Pack Draw";
    }

    @Override
    public boolean perform(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        return super.perform(sender, command, label, args);
    }

    @Override
    public @Nullable List<String> autoComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        return super.autoComplete(sender, command, label, args);
    }

    @Override
    protected Integer getPrice() {
        return this.plugin.getMultiDraw().getPrice();
    }

    @Override
    protected Integer getNeededFreeSpace() {
        return this.plugin.getGachaConfiguration().getMinimumFreeSpaceForMultiDraw();
    }

    @Override
    protected int getDrawQuantity() {
        return this.plugin.getMultiDraw().getQuantity();
    }

    @Override
    protected String subCommandUsage() {
        return "Usage : /czgacha multi [price <price> | quantity <quantity]";
    }

    @Override
    protected String drawUsage() {
        return "Usage : /czgacha multi";
    }
}
