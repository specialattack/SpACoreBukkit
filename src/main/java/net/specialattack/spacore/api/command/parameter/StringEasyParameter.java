package net.specialattack.spacore.api.command.parameter;

import java.util.List;
import net.specialattack.spacore.util.Util;
import org.bukkit.command.CommandSender;

public class StringEasyParameter extends AbstractEasyParameter.Multi<String> {

    public StringEasyParameter() {
        this.setName("string");
    }

    @Override
    public boolean parse(CommandSender sender, String value) {
        this.setValue(value);
        return true;
    }

    @Override
    public List<String> getTabComplete(CommandSender sender, String input) {
        return Util.TAB_RESULT_EMPTY;
    }

    @Override
    public String getHelpDisplay() {
        return "[" + this.getName() + "]";
    }
}
