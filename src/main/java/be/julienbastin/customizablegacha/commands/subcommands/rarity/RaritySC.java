package be.julienbastin.customizablegacha.commands.subcommands.rarity;

import be.julienbastin.customizablegacha.CustomizableGacha;
import be.julienbastin.customizablegacha.commands.subcommands.SubCommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class RaritySC extends SubCommand {

    public static final String PERMISSION = "czgacha.rarity.*";

    public RaritySC(String parentCommand, CustomizableGacha plugin) {
        super(parentCommand, plugin, PERMISSION);
        this.subCommands = List.of(
                new RarityCreateSC(syntax(), plugin),
                new RarityDeleteSC(syntax(), plugin),
                new RarityModifySC(syntax(), plugin),
                new RarityGetSC(syntax(), plugin)
        );
    }

    @Override
    public @NotNull String getValue() {
        return "rarity";
    }

    @Override
    public @NotNull String description() {
        return "Rarity management commands";
    }

    @Override
    public boolean perform(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        return super.perform(sender, command, label, args);
    }

    @Override
    public @Nullable List<String> autoComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        return super.autoComplete(sender, command, label, args);
    }
}
