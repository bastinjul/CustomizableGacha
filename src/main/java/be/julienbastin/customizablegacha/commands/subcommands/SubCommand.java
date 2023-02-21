package be.julienbastin.customizablegacha.commands.subcommands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public abstract class SubCommand {

    protected String parentCommand;
    protected JavaPlugin plugin;
    protected List<SubCommand> subCommands;
    protected String permission;

    public SubCommand(String parentCommand, JavaPlugin plugin) {
        this.parentCommand = parentCommand;
        this.plugin = plugin;
    }

    public SubCommand(String parentCommand, JavaPlugin plugin, String permission) {
        this.parentCommand = parentCommand;
        this.plugin = plugin;
        this.permission = permission;
    }

    public SubCommand(String parentCommand, JavaPlugin plugin, String permission, List<SubCommand> subCommands) {
        this.parentCommand = parentCommand;
        this.plugin = plugin;
        this.subCommands = subCommands;
        this.permission = permission;
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

    public abstract void perform(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args);

    @Nullable
    public abstract List<String> autoComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args);

    public JavaPlugin getPlugin() {
        return plugin;
    }

    public List<SubCommand> getSubCommands() {
        return subCommands;
    }

    public void setSubCommands(List<SubCommand> subCommands) {
        this.subCommands = subCommands;
    }

    public String getPermissions() {
        return permission;
    }

    public void setPermissions(String permission) {
        this.permission = permission;
    }
}
