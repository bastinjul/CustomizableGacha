package be.julienbastin.customizablegacha.commands.subcommands.pack.get;

import be.julienbastin.customizablegacha.CustomizableGacha;
import be.julienbastin.customizablegacha.commands.subcommands.SubCommand;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.util.ChatPaginator;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.stream.IntStream;

public class PackGetByRaritySC extends SubCommand {

    public static final String PERMISSION = "czgacha.pack.consult.rarity";

    public PackGetByRaritySC(String parentCommand, CustomizableGacha plugin) {
        super(parentCommand, plugin, PERMISSION);
    }

    @Override
    public @NotNull String getValue() {
        return "rarity";
    }

    @Override
    public @NotNull String description() {
        return "Get packs by rarity";
    }

    @Override
    public boolean perform(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(args.length > 2) {
            sender.sendMessage("Usage : /czg pack get rarity <rarity> <page-number>");
            return false;
        }
        if(!this.plugin.getRarities().containsKey(args[0])) {
            sender.sendMessage("Invalid rarity. Possible values are : " + this.plugin.getRarities().keySet());
            return false;
        }
        int pageNumber = 1;
        try {
            if(args.length == 2) pageNumber = Integer.parseInt(args[1])+1;
        } catch (NumberFormatException e) {
            sender.sendMessage("Invalid page number");
            sender.sendMessage("Usage : /czg pack get rarity <rarity> <page-number>");
            return false;
        }
        ChatPaginator.ChatPage page = getPage(pageNumber, args[0]);
        StringBuilder message = new StringBuilder();
        message.append(ChatColor.YELLOW).append("----------- ");
        message.append(ChatColor.WHITE).append("Rarity ").append(args[0]).append(" packs index (")
                .append(page.getPageNumber()).append("/").append(page.getTotalPages())
                .append(")");
        message.append(ChatColor.YELLOW).append("----------- ").append("\n").append(ChatColor.WHITE);
        for(String line : page.getLines()) {
            message.append(line).append("\n");
        }
        sender.sendMessage(message.toString());
        return true;
    }

    @Override
    public @Nullable List<String> autoComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(args.length == 1) {
            return this.plugin.getRarities().keySet().stream().toList();
        } else if(args.length == 2 && this.plugin.getRarities().containsKey(args[0])) {
            return IntStream.range(1, getPage(1, args[0]).getTotalPages()+1).boxed().map(Object::toString).toList();
        }
        return null;
    }

    private ChatPaginator.ChatPage getPage(int pageNumber, String rarityShortname) {
        return ChatPaginator.paginate(this.plugin.getPacksByRarityString(rarityShortname), pageNumber, 75, 9);
    }
}
