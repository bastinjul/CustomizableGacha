package be.julienbastin.customizablegacha.commands.subcommands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

public abstract class SubCommand<T extends CommandSender> {

    protected String parentCommand;
    protected JavaPlugin plugin;

    public SubCommand(String parentCommand, JavaPlugin plugin) {
        this.parentCommand = parentCommand;
        this.plugin = plugin;
    }

    @NotNull
    public abstract String getValue();

    @NotNull
    public abstract String description();

    @NotNull
    public String syntax() {
        return this.parentCommand + " " + this.getValue();
    }

    @NotNull
    public String parentCommand() {
        return this.parentCommand;
    }

    public abstract void perform(@NotNull T sender, @NotNull Command command, @NotNull String label, @NotNull String[] args);

    public JavaPlugin getPlugin() {
        return plugin;
    }
}
