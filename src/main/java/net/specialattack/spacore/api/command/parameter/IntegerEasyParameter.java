package net.specialattack.spacore.api.command.parameter;

import java.util.List;
import net.specialattack.spacore.util.Util;
import org.bukkit.command.CommandSender;

public class IntegerEasyParameter extends AbstractEasyParameter<Integer> {

    private int min = Integer.MIN_VALUE;
    private int max = Integer.MAX_VALUE;

    public IntegerEasyParameter() {
        this.setName("number");
    }

    public void setMinMax(int min, int max) {
        this.min = min;
        this.max = max;
    }

    @Override
    public boolean parse(CommandSender sender, String value) {
        Integer result;
        try {
            result = Integer.parseInt(value);
        } catch (NumberFormatException e) {
            this.setValue(null);
            return false;
        }

        if (result < this.min || result > this.max) {
            this.setValue(null);
            return false;
        }

        this.setValue(result);
        return true;
    }

    @Override
    public List<String> getTabComplete(CommandSender sender, String input) {
        return Util.TAB_RESULT_EMPTY;
    }
}
