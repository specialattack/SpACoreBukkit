package net.specialattack.bukkit.core.command.easy.parameter;

import java.util.List;
import net.specialattack.bukkit.core.util.Util;
import org.bukkit.command.CommandSender;

public class DoubleEasyParameter extends AbstractEasyParameter<Double> {

    private double min = Double.MIN_VALUE;
    private double max = Double.MAX_VALUE;

    public DoubleEasyParameter() {
        this.setName("number");
    }

    public void setMinMax(double min, double max) {
        this.min = min;
        this.max = max;
    }

    @Override
    public boolean parse(CommandSender sender, String value) {
        Double result;
        try {
            result = Double.parseDouble(value);
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
