package be.julienbastin.customizablegacha.commands.subcommands.rarity;

import be.julienbastin.customizablegacha.CustomizableGacha;
import be.julienbastin.customizablegacha.commands.subcommands.SubCommand;
import be.julienbastin.customizablegacha.utils.ChatUtils;
import org.apache.commons.lang3.StringUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.util.ChatPaginator;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class RarityGetSC extends SubCommand {

    public static final String PERMISSION = "czgacha.rarity.consult";

    public RarityGetSC(String parentCommand, CustomizableGacha plugin) {
        super(parentCommand, plugin, PERMISSION);
    }

    @Override
    public @NotNull String getValue() {
        return "get";
    }

    @Override
    public @NotNull String description() {
        return "Get information on an existing rarity";
    }

    @Override
    public boolean perform(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(args.length == 0) {
            sender.sendMessage("Usage : /czgacha rarity get <all [pageNumber] | pack-id>");
        } else if(args.length == 1 || args.length == 2) {
            if(args[0].equalsIgnoreCase("all")) {
                if(args.length == 1) return this.sendAllRaritiesMessage(sender, "1");
                return this.sendAllRaritiesMessage(sender, args[1]);
            } else if(this.plugin.getRarities().containsKey(args[0])) {
                sender.sendMessage(this.plugin.getRarities().get(args[0]).toString());
            } else {
                return false;
            }
            return true;
        }
        return false;
    }

    @Override
    public @Nullable List<String> autoComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(args.length == 1) {
            return Stream.concat(Stream.of("all"), this.plugin.getRarities().keySet().stream()).toList();
        } else if(args.length == 2) {
            return IntStream.range(1, getPage(1).getTotalPages()+1).boxed().map(Object::toString).toList();
        }
        return null;
    }

    private boolean sendAllRaritiesMessage(CommandSender sender, String pageNumberStr) {
        int pageNumber = 1;
        try {
            if(StringUtils.isNotBlank(pageNumberStr)) pageNumber = Integer.parseInt(pageNumberStr)+1;
        } catch (NumberFormatException e) {
            sender.sendMessage("Invalid page number");
            sender.sendMessage("Usage : /czgacha rarity get <all | pack-id> [pageNumber]");
            return false;
        }
        ChatPaginator.ChatPage page = this.getPage(pageNumber);
        sender.sendMessage(ChatUtils.getFormattedMessageWithMultiplePage(page, "rarities"));
        return true;
    }

    private ChatPaginator.ChatPage getPage(int pageNumber) {
        return ChatPaginator.paginate(this.plugin.getAllRaritiesString(), pageNumber, 75, 9);
    }
}
