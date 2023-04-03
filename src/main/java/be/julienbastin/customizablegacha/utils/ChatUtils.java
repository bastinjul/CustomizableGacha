package be.julienbastin.customizablegacha.utils;

import org.bukkit.ChatColor;
import org.bukkit.util.ChatPaginator;

public class ChatUtils {

    private ChatUtils() {
        throw new IllegalStateException("Utility class");
    }

    public static String getFormattedMessageWithMultiplePage(ChatPaginator.ChatPage page, String collectionName) {
        StringBuilder message = new StringBuilder();
        message.append(ChatColor.YELLOW).append("----------- ");
        message.append(ChatColor.WHITE).append("All ").append(collectionName).append(" index (")
                .append(page.getPageNumber()).append("/").append(page.getTotalPages())
                .append(")");
        message.append(ChatColor.YELLOW).append("----------- ").append("\n").append(ChatColor.WHITE);
        for(String line : page.getLines()) {
            message.append(line).append("\n");
        }
        return message.toString();
    }
}
