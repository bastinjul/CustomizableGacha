package be.julienbastin.customizablegacha.commands.subcommands.pack;

import be.julienbastin.customizablegacha.CustomizableGacha;
import be.julienbastin.customizablegacha.commands.subcommands.SubCommand;
import be.julienbastin.customizablegacha.config.Pack;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class PackDeleteSC extends SubCommand {

    public static final String PERMISSION = "czgacha.pack.delete";

    public PackDeleteSC(String parentCommand, CustomizableGacha plugin) {
        super(parentCommand, plugin, PERMISSION);
    }

    @Override
    public @NotNull String getValue() {
        return "delete";
    }

    @Override
    public @NotNull String description() {
        return "Delete an existing pack";
    }

    @Override
    public boolean perform(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(args.length != 1) {
            sender.sendMessage("Usage : /czgacha pack delete <packId>");
            return false;
        }
        Integer packId = null;
        try {
            packId = Integer.parseInt(args[0]);
            if(!this.plugin.getPacks().containsKey(packId)) {
                sender.sendMessage("Usage : /czgacha pack delete <packId>");
                return false;
            }
        } catch (NumberFormatException e) {
            sender.sendMessage("Usage : /czgacha pack delete <packId>");
            return false;
        }
        Pack removedPack = this.plugin.getPacks().remove(packId);
        this.plugin.getRarities().get(removedPack.getRarityShortName()).getPacks().remove(removedPack);
        this.plugin.getConfig().set("packs", this.plugin.getPacks().values().stream().toList());
        this.plugin.saveConfig();
        if(removedPack.getItemStackList().size() == this.plugin.getGachaConfiguration().getMaxItemStacksInOnePack()) {
            this.plugin.getGachaConfiguration().recomputeMaxItemStacksInOnePack();
        }
        sender.sendMessage("Pack deleted!");
        return true;
    }

    @Override
    public @Nullable List<String> autoComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(args.length == 1) {
            return this.plugin.getPacks().keySet().stream().map(Object::toString).toList();
        }
        return null;
    }
}
