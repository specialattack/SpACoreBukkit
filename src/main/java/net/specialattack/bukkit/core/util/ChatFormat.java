package net.specialattack.bukkit.core.util;

import org.bukkit.ChatColor;

public final class ChatFormat {

    private ChatFormat() {
    }

    public static String format(String str, ChatColor color, Object... args) {
        for (int i = 0; i < args.length; i++) {
            args[i] = ChatColor.WHITE + args[i].toString() + color;
        }

        return color + String.format(str, args);
    }

    public static String format(String str, ChatColor mainColor, ChatColor paramColor, Object... args) {
        for (int i = 0; i < args.length; i++) {
            args[i] = paramColor + args[i].toString() + mainColor;
        }

        return mainColor + String.format(str, args);
    }

}
