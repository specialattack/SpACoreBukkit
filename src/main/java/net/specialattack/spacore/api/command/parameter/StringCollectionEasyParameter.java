package net.specialattack.spacore.api.command.parameter;

import java.util.Arrays;
import java.util.List;
import net.specialattack.spacore.util.Util;
import org.bukkit.command.CommandSender;

public class StringCollectionEasyParameter extends AbstractEasyParameter.Multi<List<String>> {

    public StringCollectionEasyParameter() {
        this.setName("string");
    }

    @Override
    public boolean parse(CommandSender sender, String value) {
        this.setValue(Arrays.asList(value.split(" ")));
        return true;
    }

    @Override
    public List<String> getTabComplete(CommandSender sender, String input) {
        return Util.TAB_RESULT_EMPTY;
    }
}
