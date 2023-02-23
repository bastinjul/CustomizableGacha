package be.julienbastin.customizablegacha.commands.utils;

import be.julienbastin.customizablegacha.commands.subcommands.SubCommand;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class CommandUtils {

    private CommandUtils() {
        throw new IllegalStateException("Utility class, you cannot instantiate this class");
    }

    public static boolean noArgsCommand(@Nullable List<SubCommand> subCommandList, @NotNull CommandSender commandSender) {
        if(subCommandList != null) {
            StringBuilder builder = new StringBuilder();
            builder.append("Usage : \n");
            subCommandList.forEach(sc -> builder.append(sc.subCommandSyntaxDescription(commandSender)));
            commandSender.sendMessage(builder.toString());
        }
        return true;
    }

    public static List<String> tabCompleteSubCommands(@Nullable List<SubCommand> subCommands, @NotNull CommandSender commandSender) {
        if(subCommands != null) {
            return subCommands.stream()
                    .filter(sc -> sc.hasCompleteSubCommandPermissions().test(commandSender))
                    .map(SubCommand::getValue)
                    .toList();
        }
        return null;
    }
}
