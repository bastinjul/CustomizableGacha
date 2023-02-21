package be.julienbastin.customizablegacha.commands.subcommands.rarity;

import be.julienbastin.customizablegacha.commands.subcommands.SubCommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class RarityGetSC extends SubCommand {

    public static final String PERMISSION = "czgacha.rarity.consult";

    public RarityGetSC(String parentCommand, JavaPlugin plugin) {
        super(parentCommand, plugin, PERMISSION);
    }

    @Override
    public @NotNull String getValue() {
        return "get";
    }

    @Override
    public @NotNull String description() {
        return "Get information on an existing rarity";
    }

    @Override
    public void perform(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        //TODO
    }

    @Override
    public @Nullable List<String> autoComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        //TODO
        return null;
    }
}