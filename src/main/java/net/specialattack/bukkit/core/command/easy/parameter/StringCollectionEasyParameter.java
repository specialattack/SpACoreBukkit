package net.specialattack.bukkit.core.command.easy.parameter;

import java.util.Arrays;
import java.util.List;
import net.specialattack.bukkit.core.command.easy.EasyCollection;
import net.specialattack.bukkit.core.util.Util;
import org.bukkit.command.CommandSender;

public class StringCollectionEasyParameter extends AbstractEasyParameter.Multi<EasyCollection<String>> {

    public StringCollectionEasyParameter() {
        this.setName("string");
    }

    @Override
    public boolean parse(CommandSender sender, String value) {
        this.setValue(new EasyCollection<String>(Arrays.asList(value.split(" "))));
        return true;
    }

    @Override
    public List<String> getTabComplete(CommandSender sender, String input) {
        return Util.TAB_RESULT_EMPTY;
    }

}
