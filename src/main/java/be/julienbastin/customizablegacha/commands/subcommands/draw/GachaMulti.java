package be.julienbastin.customizablegacha.commands.subcommands.draw;

import be.julienbastin.customizablegacha.commands.subcommands.SubCommand;
import org.bukkit.command.Command;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class GachaMulti extends SubCommand<Player> {

    public static final String PERMISSION = "czgacha.multi";

    public GachaMulti(String parentCommand, JavaPlugin plugin) {
        super(parentCommand, plugin, PERMISSION);
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
    public void perform(@NotNull Player sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        //TODO
    }

    @Override
    public @Nullable List<String> autoComplete(@NotNull Player sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        return null;
    }
}
