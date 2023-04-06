package be.julienbastin.customizablegacha.commands.subcommands.draw;

import be.julienbastin.customizablegacha.CustomizableGacha;
import be.julienbastin.customizablegacha.commands.subcommands.SubCommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public abstract class DrawSC extends SubCommand {

    public DrawSC(String parentCommand, CustomizableGacha plugin, String permission) {
        super(parentCommand, plugin, permission);
    }

    @Override
    public boolean perform(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(args.length == 0) {
            this.draw();
            return true;
        } else if(
                (args.length == 1 || args.length == 2) && this.hasSubcommandPermission().test(sender)) {
            super.perform(sender, command, label, args);
            return true;
        }
        if(this.hasSubcommandPermission().test(sender)) {
            sender.sendMessage(subCommandUsage());
        } else {
            sender.sendMessage(drawUsage());
        }
        return false;
    }

    @Override
    public @Nullable List<String> autoComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(this.hasSubcommandPermission().test(sender)) {
            return super.autoComplete(sender, command, label, args);
        }
        return null;
    }

    protected abstract void draw();

    protected abstract String subCommandUsage();

    protected abstract String drawUsage();
}
