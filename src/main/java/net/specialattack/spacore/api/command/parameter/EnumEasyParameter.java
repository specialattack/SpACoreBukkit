package net.specialattack.spacore.api.command.parameter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.bukkit.command.CommandSender;

public class EnumEasyParameter<T extends Enum<T>> extends AbstractEasyParameter<T> {

    private final T[] values;
    private final List<String> tab;

    public EnumEasyParameter(T[] values) {
        this.setName("enum");
        this.values = values;
        List<String> tab = new ArrayList<>();
        for (T value : values) {
            tab.add(value.name().toLowerCase());
        }
        this.tab = Collections.unmodifiableList(tab);
    }

    @Override
    public boolean parse(CommandSender sender, String value) {
        T result = null;
        for (T current : this.values) {
            if (current.name().equalsIgnoreCase(value)) {
                result = current;
                break;
            }
        }
        this.setValue(result);
        return result != null;
    }

    @Override
    public List<String> getTabComplete(CommandSender sender, String input) {
        return this.tab;
    }
}
