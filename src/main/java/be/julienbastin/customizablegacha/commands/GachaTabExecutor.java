package be.julienbastin.customizablegacha.commands;

import be.julienbastin.customizablegacha.commands.subcommands.SubCommand;
import be.julienbastin.customizablegacha.commands.utils.CommandUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

public abstract class GachaTabExecutor implements TabExecutor {

    protected List<SubCommand> subCommands;

    protected GachaTabExecutor(List<SubCommand> subCommands) {
        this.subCommands = subCommands;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if(args.length == 0) return CommandUtils.noArgsCommand(this.subCommands, commandSender);
        AtomicBoolean returnValue = new AtomicBoolean();
        subCommands.stream()
                .filter(sub -> sub.getValue().equals(args[0]))
                .findFirst()
                .ifPresentOrElse(
                        sub ->
                                returnValue.set(sub.perform(commandSender, command, s, Arrays.copyOfRange(args, 1, args.length))),
                        () -> returnValue.set(false));
        return returnValue.get();
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if(args.length == 1) {
            return CommandUtils.tabCompleteSubCommands(this.subCommands, commandSender);
        }
        AtomicReference<List<String>> atomicReference = new AtomicReference<>();
        this.subCommands.stream()
                .filter(subCommand -> subCommand.getValue().equalsIgnoreCase(args[0]))
                .findFirst()
                .ifPresent(subCommand -> atomicReference.set(subCommand.autoComplete(commandSender, command, s, Arrays.copyOfRange(args, 1, args.length))));
        return atomicReference.get();
    }
}
