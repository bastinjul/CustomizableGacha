package be.julienbastin.customizablegacha.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.annotation.command.Commands;
import org.jetbrains.annotations.NotNull;

@Commands(
        @org.bukkit.plugin.java.annotation.command.Command(name = "czgacha", usage = "/czgacha [single|multi]",
                desc = "Base Command", aliases = {"czg", "czgacha"})
)
public class GachaCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (commandSender instanceof Player player) {
            player.sendMessage("Command czgacha entered.");
            return true;
        }
        return false;
    }
}
