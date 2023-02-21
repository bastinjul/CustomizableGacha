package be.julienbastin.customizablegacha.commands.subcommands.rarity;

import be.julienbastin.customizablegacha.commands.subcommands.SubCommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class RarityModifySC extends SubCommand {

    public static final String PERMISSION = "czgacha.rarity.modify";

    public RarityModifySC(String parentCommand, JavaPlugin plugin) {
        super(parentCommand, plugin, PERMISSION);
    }

    @Override
    public @NotNull String getValue() {
        return "modify";
    }

    @Override
    public @NotNull String description() {
        return "Modify probability of rarities";
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