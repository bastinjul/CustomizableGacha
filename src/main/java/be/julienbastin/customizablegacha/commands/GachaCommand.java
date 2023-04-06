package be.julienbastin.customizablegacha.commands;

import be.julienbastin.customizablegacha.CustomizableGacha;
import be.julienbastin.customizablegacha.commands.subcommands.SubCommand;
import be.julienbastin.customizablegacha.commands.subcommands.draw.MultiSC;
import be.julienbastin.customizablegacha.commands.subcommands.draw.SingleSC;
import be.julienbastin.customizablegacha.commands.subcommands.draw.prize.MultiPriceSC;
import be.julienbastin.customizablegacha.commands.subcommands.draw.prize.SinglePriceSC;
import be.julienbastin.customizablegacha.commands.subcommands.draw.quantity.MultiQuantitySC;
import be.julienbastin.customizablegacha.commands.subcommands.pack.PackCreateSC;
import be.julienbastin.customizablegacha.commands.subcommands.pack.PackDeleteSC;
import be.julienbastin.customizablegacha.commands.subcommands.pack.PackGetSC;
import be.julienbastin.customizablegacha.commands.subcommands.pack.PackSC;
import be.julienbastin.customizablegacha.commands.subcommands.pack.get.PackGetAllSC;
import be.julienbastin.customizablegacha.commands.subcommands.pack.get.PackGetByIdSC;
import be.julienbastin.customizablegacha.commands.subcommands.pack.get.PackGetByRaritySC;
import be.julienbastin.customizablegacha.commands.subcommands.rarity.*;
import com.google.inject.Inject;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.permissions.PermissionDefault;
import org.bukkit.plugin.java.annotation.command.Commands;
import org.bukkit.plugin.java.annotation.permission.ChildPermission;
import org.bukkit.plugin.java.annotation.permission.Permission;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

@Commands(
        @org.bukkit.plugin.java.annotation.command.Command(name = GachaCommand.COMMAND, usage = "Invalid command",
                desc = "Base Command", aliases = {"czg", "czgacha"})
)
// Global permissions
@Permission(name = "czgacha.*", desc = "Access to all commands", defaultValue = PermissionDefault.OP, children = {
        @ChildPermission(name = SingleSC.PERMISSION),
        @ChildPermission(name = MultiSC.PERMISSION),
        @ChildPermission(name = SinglePriceSC.PERMISSION),
        @ChildPermission(name = MultiPriceSC.PERMISSION),
        @ChildPermission(name = PackSC.PERMISSION),
        @ChildPermission(name = RaritySC.PERMISSION)
})

//draw permissions
@Permission(name = SingleSC.PERMISSION, desc = "Draw of a single pack", defaultValue = PermissionDefault.TRUE)
@Permission(name = SinglePriceSC.PERMISSION, desc = "Configure single draw price", defaultValue = PermissionDefault.OP)
@Permission(name = MultiSC.PERMISSION, desc = "Draw of multiple packs", defaultValue = PermissionDefault.TRUE)
@Permission(name = MultiPriceSC.PERMISSION, desc = "Configure multi draw price", defaultValue = PermissionDefault.OP)
@Permission(name = MultiQuantitySC.PERMISSION, desc = "Quantity of pack for a multi draw configuration", defaultValue = PermissionDefault.OP)

//pack management permissions
@Permission(name = PackSC.PERMISSION, desc = "Pack management rights", defaultValue = PermissionDefault.OP, children = {
        @ChildPermission(name = PackGetSC.PERMISSION),
        @ChildPermission(name = PackCreateSC.PERMISSION),
        @ChildPermission(name = PackDeleteSC.PERMISSION)
})
@Permission(name = PackCreateSC.PERMISSION, desc = "Creation of new packs", defaultValue = PermissionDefault.OP)
@Permission(name = PackDeleteSC.PERMISSION, desc = "Deletion of existing pack", defaultValue = PermissionDefault.OP)
@Permission(name = PackGetSC.PERMISSION, desc = "Get the list of packs possible to draw", defaultValue = PermissionDefault.TRUE, children = {
        @ChildPermission(name = PackGetAllSC.PERMISSION),
        @ChildPermission(name = PackGetByRaritySC.PERMISSION),
        @ChildPermission(name = PackGetByIdSC.PERMISSION)
})
@Permission(name = PackGetAllSC.PERMISSION, desc = "Get list of all packs", defaultValue = PermissionDefault.TRUE)
@Permission(name = PackGetByRaritySC.PERMISSION, desc = "Get list of packs by rarity", defaultValue = PermissionDefault.TRUE)
@Permission(name = PackGetByIdSC.PERMISSION, desc = "Get the detail of a specific pack", defaultValue = PermissionDefault.TRUE)

//rarity management permissions
@Permission(name = RaritySC.PERMISSION, desc = "Rarity management rights", defaultValue = PermissionDefault.OP, children = {
        @ChildPermission(name = RarityGetSC.PERMISSION),
        @ChildPermission(name = RarityCreateSC.PERMISSION),
        @ChildPermission(name = RarityModifySC.PERMISSION),
        @ChildPermission(name = RarityDeleteSC.PERMISSION)
})
@Permission(name = RarityGetSC.PERMISSION, desc = "Get the list of existing rarities", defaultValue = PermissionDefault.TRUE)
@Permission(name = RarityCreateSC.PERMISSION, desc = "Create new rarity", defaultValue = PermissionDefault.OP)
@Permission(name = RarityModifySC.PERMISSION, desc = "Modify probability of rarities", defaultValue = PermissionDefault.OP)
@Permission(name = RarityDeleteSC.PERMISSION, desc = "Delete existing rarity", defaultValue = PermissionDefault.OP)
public class GachaCommand extends GachaTabExecutor implements TabExecutor {

    public static final String COMMAND = "czgacha";

    private CustomizableGacha plugin;

    @Inject
    public GachaCommand(CustomizableGacha plugin) {
        super(List.of(
                new SingleSC(COMMAND, plugin),
                new MultiSC(COMMAND, plugin),
                new PackSC(COMMAND, plugin),
                new RaritySC(COMMAND, plugin)
        ));
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        return super.onCommand(commandSender, command, s, args);
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        return super.onTabComplete(commandSender, command, s, args);
    }

    public List<SubCommand> getSubCommands() {
        return subCommands;
    }

    public static String getCommand() {
        return COMMAND;
    }
}
