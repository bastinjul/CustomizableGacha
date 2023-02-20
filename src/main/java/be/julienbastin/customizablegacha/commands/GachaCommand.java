package be.julienbastin.customizablegacha.commands;

import be.julienbastin.customizablegacha.CustomizableGacha;
import be.julienbastin.customizablegacha.commands.subcommands.GachaMulti;
import be.julienbastin.customizablegacha.commands.subcommands.GachaSingle;
import be.julienbastin.customizablegacha.commands.subcommands.SubCommand;
import com.google.inject.Inject;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.annotation.command.Commands;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

@Commands(
        @org.bukkit.plugin.java.annotation.command.Command(name = "czgacha", usage = "/czgacha [ single | multi ]",
                desc = "Base Command", aliases = {"czg", "czgacha"})
)
public class GachaCommand implements CommandExecutor {

    private final List<SubCommand<Player>> subCommands;

    private CustomizableGacha plugin;

    @Inject
    public GachaCommand(CustomizableGacha plugin) {
        this.plugin = plugin;
        this.subCommands = List.of(new GachaSingle(plugin), new GachaMulti(plugin));
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if (commandSender instanceof Player player) {
            if(args.length != 1) return false;
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

    public List<SubCommand<Player>> getSubCommands() {
        return subCommands;
    }
}
