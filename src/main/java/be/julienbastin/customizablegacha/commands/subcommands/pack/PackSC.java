package be.julienbastin.customizablegacha.commands.subcommands.pack;

import be.julienbastin.customizablegacha.commands.subcommands.SubCommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class PackSC extends SubCommand{

    public static final String PERMISSION = "czgacha.pack.*";

    public PackSC(String parentCommand, JavaPlugin plugin) {
        super(parentCommand, plugin, PERMISSION);
        this.subCommands = List.of(
                new PackCreateSC(syntax(), plugin),
                new PackDeleteSC(syntax(), plugin),
                new PackGetSC(syntax(), plugin),
                new PackModifySC(syntax(), plugin)
        );
    }

    @Override
    public @NotNull String getValue() {
        return "pack";
    }

    @Override
    public @NotNull String description() {
        return "Pack configuration command";
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
