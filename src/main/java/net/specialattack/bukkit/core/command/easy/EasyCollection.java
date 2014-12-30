package net.specialattack.bukkit.core.command.easy;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import net.specialattack.bukkit.core.util.Func;

public final class EasyCollection<T> {

    public final Collection<T> values;

    public EasyCollection(Collection<T> values) {
        this.values = values;
    }

    public <R> EasyCollection<R> forEach(Func<T, R> function) {
        List<R> result = new ArrayList<R>();
        for (T value : this.values) {
            result.add(function.go(value));
        }
        return new EasyCollection<R>(result);
    }

}
