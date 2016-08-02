package net.specialattack.spacore.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
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

    public static TextComponent constructMessage(@Nonnull org.bukkit.ChatColor color, @Nullable Object @Nonnull ... args) {
        return constructMessage(color.asBungee(), args);
    }

    public static TextComponent constructMessage(@Nonnull ChatColor color, @Nullable Object @Nonnull ... args) {
        return constructMessage(new ChatColor[] { color }, args);
    }

    public static TextComponent constructMessage(@Nonnull org.bukkit.ChatColor[] colors, @Nullable Object @Nonnull ... args) {
        return constructMessage(Arrays.stream(colors).map(org.bukkit.ChatColor::asBungee).toArray(ChatColor[]::new), args);
    }

    public static TextComponent constructMessage(@Nonnull ChatColor[] colors, @Nullable Object @Nonnull ... args) {
        TextComponent result = constructMessage(args);
        for (ChatColor color : colors) {
            switch (color) {
                case MAGIC:
                    result.setObfuscated(true);
                    break;
                case BOLD:
                    result.setBold(true);
                    break;
                case STRIKETHROUGH:
                    result.setStrikethrough(true);
                    break;
                case UNDERLINE:
                    result.setUnderlined(true);
                    break;
                case ITALIC:
                    result.setItalic(true);
                    break;
                case RESET:
                    break;
                default:
                    result.setColor(color);
                    break;
            }
        }
        return result;
    }

    public static TextComponent constructMessage(@Nullable Object @Nonnull ... args) {
        List<TextComponent> components = new ArrayList<>();
        TextComponent component = new TextComponent();
        for (Object arg : args) {
            component = constructDo(components, component, arg);
        }

        TextComponent result = new TextComponent("");
        components.forEach(result::addExtra);
        return result;
    }

    private static TextComponent constructDo(List<TextComponent> components, TextComponent component, Object arg) {
        if (arg instanceof String) {
            TextComponent old = component;
            component = new TextComponent(component);
            old.setText((String) arg);
            components.add(old);
        } else if (arg instanceof org.bukkit.ChatColor) {
            component = setColor(component, ((org.bukkit.ChatColor) arg).asBungee());
        } else if (arg instanceof ChatColor) {
            component = setColor(component, (ChatColor) arg);
        } else if (arg instanceof Player) {
            Player player = (Player) arg;
            TextComponent old = component;
            component = new TextComponent(component);
            old.setText(player.getName());
            old.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/tell " + player.getName()));
            components.add(old);
        } else if (arg instanceof CommandSender) {
            TextComponent old = component;
            component = new TextComponent(component);
            old.setText(((CommandSender) arg).getName());
            components.add(old);
        } else if (arg instanceof Iterable) {
            Container<TextComponent> holder = new Container<>(component);
            //noinspection unchecked
            ((Iterable) arg).forEach(o -> holder.value = constructDo(components, holder.value, o));
            component = holder.value;
        } else {
            TextComponent old = component;
            component = new TextComponent(component);
            old.setText("" + arg);
            components.add(old);
        }
        return component;
    }

    public static TextComponent setColor(TextComponent component, ChatColor color) {
        switch (color) {
            case MAGIC:
                component.setObfuscated(true);
                break;
            case BOLD:
                component.setBold(true);
                break;
            case STRIKETHROUGH:
                component.setStrikethrough(true);
                break;
            case UNDERLINE:
                component.setUnderlined(true);
                break;
            case ITALIC:
                component.setItalic(true);
                break;
            case RESET:
                component = new TextComponent();
                break;
            default:
                component = new TextComponent();
                component.setColor(color);
                break;
        }
        return component;
    }
}
