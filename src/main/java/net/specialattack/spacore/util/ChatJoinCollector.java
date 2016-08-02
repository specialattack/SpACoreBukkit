package net.specialattack.spacore.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;
import org.bukkit.ChatColor;

public class ChatJoinCollector implements Collector<String, List<Object>, List<Object>> {

    private final ChatColor displayColor;

    public ChatJoinCollector(ChatColor displayColor) {
        this.displayColor = displayColor;
    }

    public ChatJoinCollector() {
        this.displayColor = ChatColor.WHITE;
    }

    @Override
    public Supplier<List<Object>> supplier() {
        return ArrayList::new;
    }

    @Override
    public BiConsumer<List<Object>, String> accumulator() {
        return (joined, s) -> {
            if (joined.size() > 0) {
                joined.add(", ");
            }
            joined.add(this.displayColor);
            joined.add(s);
            joined.add(ChatColor.RESET);
        };
    }

    @Override
    public BinaryOperator<List<Object>> combiner() {
        return (joined, other) -> {
            joined.add(", ");
            joined.addAll(other);
            return joined;
        };
    }

    @Override
    public Function<List<Object>, List<Object>> finisher() {
        return result -> result;
    }

    @Override
    public Set<Characteristics> characteristics() {
        return Collections.emptySet();
    }
}
