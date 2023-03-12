package be.julienbastin.customizablegacha.commands.subcommands;

import be.julienbastin.customizablegacha.CustomizableGacha;
import be.julienbastin.customizablegacha.commands.GachaTabExecutor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

public abstract class SubCommand extends GachaTabExecutor {

    protected String parentCommand;
    protected CustomizableGacha plugin;
    protected String permission;

    public SubCommand(String parentCommand, CustomizableGacha plugin) {
        super(Collections.emptyList());
        this.parentCommand = parentCommand;
        this.plugin = plugin;
    }

    public SubCommand(String parentCommand, CustomizableGacha plugin, String permission) {
        super(Collections.emptyList());
        this.parentCommand = parentCommand;
        this.plugin = plugin;
        this.permission = permission;
    }

    public SubCommand(String parentCommand, CustomizableGacha plugin, String permission, List<SubCommand> subCommands) {
        super(subCommands);
        this.parentCommand = parentCommand;
        this.plugin = plugin;
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

    public boolean perform(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        return super.onCommand(sender, command, label, args);
    }

    @Nullable
    public List<String> autoComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        return super.onTabComplete(sender, command, label, args);
    }

    public JavaPlugin getPlugin() {
        return plugin;
    }

    public List<SubCommand> getSubCommands() {
        return subCommands;
    }

    public String getPermissions() {
        return permission;
    }

    public void setPermissions(String permission) {
        this.permission = permission;
    }

    public Predicate<CommandSender> hasCompleteSubCommandPermissions() {
        if(this.subCommands != null) {
            return this.subCommands.stream()
                    .map(SubCommand::hasCompleteSubCommandPermissions)
                    .reduce(x -> false, Predicate::or)
                    .or(hasPermissions());
        }
        return this.hasPermissions();
    }

    public Predicate<CommandSender> hasPermissions() {
        return (c) -> c.hasPermission(permission);
    }

    public String selfSyntaxDescription(CommandSender commandSender) {
        if(hasPermissions().test(commandSender)) {
            return syntax() + " :: " + description() + "\n";
        }
        return "";
    }

    public String subCommandSyntaxDescription(CommandSender commandSender) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(selfSyntaxDescription(commandSender));
        if(this.subCommands != null) {
            this.subCommands.forEach(sc -> stringBuilder.append(sc.subCommandSyntaxDescription(commandSender)));
        }
        return stringBuilder.toString();
    }
}
