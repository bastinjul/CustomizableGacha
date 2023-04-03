package be.julienbastin.customizablegacha.commands.subcommands.rarity;

import be.julienbastin.customizablegacha.CustomizableGacha;
import be.julienbastin.customizablegacha.commands.subcommands.SubCommand;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class RarityModifySC extends SubCommand {

    public static final String PERMISSION = "czgacha.rarity.modify";

    public RarityModifySC(String parentCommand, CustomizableGacha plugin) {
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

    public static String getUsage() {
        return "Usage : " + "\n" +
                ChatColor.YELLOW + "/czgacha rarity modify [<shortname>:<probability>]" + "\n" +
                ChatColor.GRAY + "Where [<shortname>:<probability>] is the list of all rarities with their own probability." + "\n" +
                "The sum of probability should be equal to 100.\n" +
                ChatColor.BLUE + "Example : /czgacha rarity modify c:50 r:35 ur:15";
    }

    @Override
    public boolean perform(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        //TODO
        return true;
    }

    @Override
    public @Nullable List<String> autoComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        //TODO
        return null;
    }
}
