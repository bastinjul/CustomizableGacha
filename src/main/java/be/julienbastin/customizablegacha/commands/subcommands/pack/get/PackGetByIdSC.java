package be.julienbastin.customizablegacha.commands.subcommands.pack.get;

import be.julienbastin.customizablegacha.CustomizableGacha;
import be.julienbastin.customizablegacha.commands.subcommands.SubCommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class PackGetByIdSC extends SubCommand {

    public static final String PERMISSION = "czgacha.pack.consult.id";

    public PackGetByIdSC(String parentCommand, CustomizableGacha plugin) {
        super(parentCommand, plugin, PERMISSION);
    }

    @Override
    public @NotNull String getValue() {
        return "id";
    }

    @Override
    public @NotNull String description() {
        return "Get details of a specific pack";
    }

    @Override
    public boolean perform(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(args.length == 1) {
            try {
                Integer packId = Integer.parseInt(args[0]);
                if(this.plugin.getPacks().containsKey(packId)) {
                    sender.sendMessage(this.plugin.getPacks().get(packId).toString());
                    return true;
                }
            } catch (NumberFormatException e) {
                //return false and send usage message
            }
        }
        sender.sendMessage("Usage : /czgacha pack get id <pack-id>");
        return false;
    }

    @Override
    public @Nullable List<String> autoComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(args.length == 1) {
            return this.plugin.getPacks().keySet().stream().map(Object::toString).toList();
        }
        return null;
    }
}
