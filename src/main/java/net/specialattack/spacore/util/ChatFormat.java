package net.specialattack.spacore.util;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.Syntax;
import org.bukkit.ChatColor;

public final class ChatFormat {

    private ChatFormat() {
    }

    @Nonnull
    public static String format(@Syntax("FormatString") @Nonnull String str, @Nonnull ChatColor color, @Nullable Object @Nonnull ... args) {
        for (int i = 0; i < args.length; i++) {
            args[i] = ChatColor.WHITE.toString() + args[i] + color;
        }

        return color + String.format(str, args);
    }

    @Nonnull
    public static String format(@Syntax("FormatString") @Nonnull String str, @Nonnull ChatColor mainColor, @Nonnull ChatColor paramColor, @Nullable Object @Nonnull ... args) {
        for (int i = 0; i < args.length; i++) {
            args[i] = paramColor.toString() + args[i] + mainColor;
        }

        return mainColor + String.format(str, args);
    }
}
