package be.julienbastin.customizablegacha.commands.subcommands.rarity;

import be.julienbastin.customizablegacha.CustomizableGacha;
import be.julienbastin.customizablegacha.commands.subcommands.SubCommand;
import be.julienbastin.customizablegacha.config.Rarity;
import org.apache.commons.lang3.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

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
        if(args.length == 0) {
            sender.sendMessage(getUsage());
            return true;
        }
        int totalProbability = 0;
        Map<String, Integer> rarityShortnames = new HashMap<>();
        for(String arg : args) {
            String[] parts = arg.split(":");
            if(parts.length != 2 || !this.plugin.getRarities().containsKey(parts[0])) {
                sender.sendMessage(getUsage());
                return false;
            }
            try {
                int probability = Integer.parseInt(parts[1]);
                rarityShortnames.put(parts[0], probability);
                totalProbability += probability;
            } catch (NumberFormatException e) {
                sender.sendMessage(getUsage());
                return false;
            }
        }
        if(totalProbability != 100) {
            sender.sendMessage(ChatColor.RED + "Total probability not equals to 100!");
            sender.sendMessage(ChatColor.DARK_RED + "The total probability you entered is equal to " + totalProbability);
            return false;
        }
        rarityShortnames.forEach((key, value) -> {
            Rarity rarity = this.plugin.getRarities().get(key);
            rarity.setProbability(value);
            this.plugin.getRarities().replace(key, rarity);
        });
        this.plugin.getConfig().set("rarities", this.plugin.getRarities().values().stream().toList());
        this.plugin.saveConfig();
        return true;
    }

    @Override
    public @Nullable List<String> autoComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(args.length == 0 || this.getTotalProbabilityFromList(args) >= 100) return null;
        if(StringUtils.isBlank(args[args.length-1])) {
            return getRarityShortnameList(args);
        } else if (args[args.length-1].endsWith(":")) {
            return List.of("<probability>");
        } else if (this.plugin.getRarities().containsKey(args[args.length-1])) {
            return List.of(":");
        }
        return null;
    }

    private List<String> getRarityShortnameList(String[] args) {
        List<String> previousRarities = Arrays.stream(args).map(s -> {
            String[] parts = s.split(":");
            if(parts.length == 2) {
                return parts[0];
            }
            return null;
        }).filter(Objects::nonNull).toList();
        Set<String> raritiesShortname = new HashSet<>(this.plugin.getRarities().keySet());
        previousRarities.forEach(raritiesShortname::remove);
        return raritiesShortname.stream().toList();
    }

    private Integer getTotalProbabilityFromList(String[] args) {
        return Arrays.stream(args)
                .map(arg -> {
                    String[] parts = arg.split(":");
                    if(parts.length == 2) {
                        try {
                            return Integer.parseInt(parts[1]);
                        } catch (NumberFormatException e) {
                            //do nothing
                        }
                    }
                    return null;
                })
                .filter(Objects::nonNull)
                .reduce(0, Integer::sum);
    }
}
