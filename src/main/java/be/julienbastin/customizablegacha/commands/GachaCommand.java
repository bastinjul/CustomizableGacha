package be.julienbastin.customizablegacha.commands;

import be.julienbastin.customizablegacha.CustomizableGacha;
import be.julienbastin.customizablegacha.commands.subcommands.SubCommand;
import be.julienbastin.customizablegacha.commands.subcommands.draw.GachaMulti;
import be.julienbastin.customizablegacha.commands.subcommands.draw.GachaSingle;
import com.google.inject.Inject;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionDefault;
import org.bukkit.plugin.java.annotation.command.Commands;
import org.bukkit.plugin.java.annotation.permission.ChildPermission;
import org.bukkit.plugin.java.annotation.permission.Permission;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

@Commands(
        @org.bukkit.plugin.java.annotation.command.Command(name = GachaCommand.COMMAND, usage = "Invalid command",
                desc = "Base Command", aliases = {"czg", "czgacha"})
)
// Global permissions
@Permission(name = "czgacha.*", desc = "Access to all commands", defaultValue = PermissionDefault.OP, children = {
        @ChildPermission(name = GachaSingle.PERMISSION),
        @ChildPermission(name = GachaMulti.PERMISSION),
        @ChildPermission(name = "czgacha.pack.*"),
        @ChildPermission(name = "czgacha.rarity.*")
})

//draw permissions
@Permission(name = GachaSingle.PERMISSION, desc = "Draw of a single pack", defaultValue = PermissionDefault.TRUE)
@Permission(name = GachaMulti.PERMISSION, desc = "Draw of multiple packs", defaultValue = PermissionDefault.TRUE)

//pack management permissions
@Permission(name = "czgacha.pack.*", desc = "Pack management rights", defaultValue = PermissionDefault.OP, children = {
        @ChildPermission(name = "czgacha.pack.consult"),
        @ChildPermission(name = "czgacha.pack.create"),
        @ChildPermission(name = "czgacha.pack.modify"),
        @ChildPermission(name = "czgacha.pack.delete")
})
@Permission(name = "czgacha.pack.consult", desc = "Get the list of packs possible to draw", defaultValue = PermissionDefault.TRUE)
@Permission(name = "czgacha.pack.create", desc = "Creation of new packs", defaultValue = PermissionDefault.OP)
@Permission(name = "czgacha.pack.modify", desc = "Modification of existing pack", defaultValue = PermissionDefault.OP)
@Permission(name = "czgacha.pack.delete", desc = "Deletion of existing pack", defaultValue = PermissionDefault.OP)

//rarity management permissions
@Permission(name = "czgacha.rarity.*", desc = "Rarity management rights", defaultValue = PermissionDefault.OP, children = {
        @ChildPermission(name = "czgacha.rarity.consult"),
        @ChildPermission(name = "czgacha.rarity.create"),
        @ChildPermission(name = "czgacha.rarity.modify"),
        @ChildPermission(name = "czgacha.rarity.delete")
})
@Permission(name = "czgacha.rarity.consult", desc = "Get the list of existing rarities", defaultValue = PermissionDefault.OP)
@Permission(name = "czgacha.rarity.create", desc = "Create new rarity", defaultValue = PermissionDefault.OP)
@Permission(name = "czgacha.rarity.modify", desc = "Modify probability of rarities", defaultValue = PermissionDefault.OP)
@Permission(name = "czgacha.rarity.delete", desc = "Delete existing rarity", defaultValue = PermissionDefault.OP)
public class GachaCommand implements TabExecutor {
    private final List<SubCommand<Player>> subCommands;

    public static final String COMMAND = "czgacha";

    private CustomizableGacha plugin;

    @Inject
    public GachaCommand(CustomizableGacha plugin) {
        this.plugin = plugin;
        this.subCommands = List.of(new GachaSingle(COMMAND, plugin), new GachaMulti(COMMAND, plugin));
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if (commandSender instanceof Player player) {
            if(args.length == 0) return noArgsCommand(player);
            AtomicBoolean returnValue = new AtomicBoolean();
            subCommands.stream()
                    .filter(sub -> sub.getValue().equals(args[0]))
                    .findFirst()
                    .ifPresentOrElse(sub -> {
                        sub.perform(player, command, s, args);
                        returnValue.set(true);
                    }, () -> returnValue.set(false));
            return returnValue.get();
        }
        return false;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if(args.length == 1) return this.subCommands.stream().map(SubCommand::getValue).toList();
        if(args.length == 2 && commandSender instanceof Player player) {
            AtomicReference<List<String>> atomicReference = new AtomicReference<>();
            this.subCommands.stream()
                    .filter(subCommand -> subCommand.getValue().equalsIgnoreCase(args[0]))
                    .findFirst()
                    .ifPresent(subCommand -> atomicReference.set(subCommand.autoComplete(player, command, s, args)));
            return atomicReference.get();
        }
        return null;
    }

    private boolean noArgsCommand(Player player) {
        //TODO
        return true;
    }

    public List<SubCommand<Player>> getSubCommands() {
        return subCommands;
    }

    public static String getCommand() {
        return COMMAND;
    }
}
