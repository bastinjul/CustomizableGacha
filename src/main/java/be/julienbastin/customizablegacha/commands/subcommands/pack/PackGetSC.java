package be.julienbastin.customizablegacha.commands.subcommands.pack;

import be.julienbastin.customizablegacha.commands.subcommands.SubCommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class PackGetSC extends SubCommand {

    public static final String PERMISSION = "czgacha.pack.consult";

    public PackGetSC(String parentCommand, JavaPlugin plugin) {
        super(parentCommand, plugin, PERMISSION);
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
        //TODO
        return true;
    }

    @Override
    public @Nullable List<String> autoComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        //TODO
        return null;
    }
}
