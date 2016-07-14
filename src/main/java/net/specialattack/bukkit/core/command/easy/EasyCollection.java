package net.specialattack.bukkit.core.command.easy;

import java.util.Collection;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

public final class EasyCollection<T> {

    public final Collection<T> values;

    public EasyCollection(Collection<T> values) {
        this.values = values;
    }

    public <R> EasyCollection<R> map(Function<T, R> function) {
        return new EasyCollection<>(this.values.stream().map(function).collect(Collectors.toList()));
    }

    public void forEach(Consumer<T> function) {
        this.values.forEach(function);
    }
}
