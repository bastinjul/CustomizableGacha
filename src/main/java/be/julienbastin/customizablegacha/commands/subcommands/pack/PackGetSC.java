package be.julienbastin.customizablegacha.commands.subcommands.pack;

import be.julienbastin.customizablegacha.CustomizableGacha;
import be.julienbastin.customizablegacha.commands.subcommands.SubCommand;
import be.julienbastin.customizablegacha.commands.subcommands.pack.get.PackGetAllSC;
import be.julienbastin.customizablegacha.commands.subcommands.pack.get.PackGetByIdSC;
import be.julienbastin.customizablegacha.commands.subcommands.pack.get.PackGetByRaritySC;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class PackGetSC extends SubCommand {

    public static final String PERMISSION = "czgacha.pack.consult.*";

    public PackGetSC(String parentCommand, CustomizableGacha plugin) {
        super(parentCommand, plugin, PERMISSION);
        super.subCommands = List.of(
            new PackGetAllSC(syntax(), plugin),
            new PackGetByRaritySC(syntax(), plugin),
            new PackGetByIdSC(syntax(), plugin)
        );
    }

    @Override
    public @NotNull String getValue() {
        return "get";
    }

    @Override
    public @NotNull String description() {
        return "Get information on existing pack";
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
