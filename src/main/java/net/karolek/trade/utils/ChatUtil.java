package net.karolek.trade.utils;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class ChatUtil {

    public static String replace(String message, Object... objects) {
        String replace = message;
        for (int i = 0; i < objects.length; i++)
            replace = replace.replace("{" + i + "}", objects[i].toString());
        return ChatColor.translateAlternateColorCodes('&', replace);
    }

    public static boolean send(CommandSender sender, String msg, Object... objects) {
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', replace(msg, objects)));
        return true;
    }

}
