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

public class PackGetAllSC extends SubCommand {
    public static final String PERMISSION = "czgacha.pack.consult.all";

    public PackGetAllSC(String parentCommand, CustomizableGacha plugin) {
        super(parentCommand, plugin, PERMISSION);
    }

    @Override
    public @NotNull String getValue() {
        return "all";
    }

    @Override
    public @NotNull String description() {
        return "Get all packs list";
    }

    @Override
    public boolean perform(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(args.length > 1) {
            sender.sendMessage("Usage : /czg pack get all <page-number>");
            return false;
        }
        int pageNumber = 1;
        try {
            if(args.length == 1) pageNumber = Integer.parseInt(args[0])+1;
        } catch (NumberFormatException e) {
            sender.sendMessage("Invalid page number");
            sender.sendMessage("Usage : /czg pack get all <page-number>");
            return false;
        }
        ChatPaginator.ChatPage page = getPage(pageNumber);
        StringBuilder message = new StringBuilder();
        message.append(ChatColor.YELLOW).append("----------- ");
        message.append(ChatColor.WHITE).append("All packs index (")
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
            return IntStream.range(1, getPage(1).getTotalPages()+1).boxed().map(Object::toString).toList();
        }
        return null;
    }

    private ChatPaginator.ChatPage getPage(int pageNumber) {
        return ChatPaginator.paginate(this.plugin.getAllPacksString(), pageNumber, 75, 9);
    }
}
