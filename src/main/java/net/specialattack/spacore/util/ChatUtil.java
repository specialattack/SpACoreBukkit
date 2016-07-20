package net.specialattack.spacore.util;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;
import net.md_5.bungee.api.chat.BaseComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public final class ChatUtil {

    public static final List<String> TAB_RESULT_EMPTY = Collections.emptyList();
    public static final List<String> TAB_RESULT_TRUE_FALSE = Collections.unmodifiableList(Arrays.asList("true", "false"));

    private ChatUtil() {
    }

    public static void sendToAll(Predicate<Player> filter, String message) {
        Bukkit.getOnlinePlayers().forEach(player -> {
            if (filter.test(player)) {
                player.sendMessage(message);
            }
        });
    }

    public static void sendToAll(Predicate<Player> filter, BaseComponent message) {
        Bukkit.getOnlinePlayers().forEach(player -> {
            if (filter.test(player)) {
                player.spigot().sendMessage(message);
            }
        });
    }
}
