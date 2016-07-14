package net.specialattack.bukkit.core.command.easy.parameter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.specialattack.bukkit.core.command.easy.EasyCollection;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;

public class OfflinePlayerCollectionEasyParameter extends AbstractEasyParameter.Multi<EasyCollection<OfflinePlayer>> {

    public OfflinePlayerCollectionEasyParameter() {
        this.setName("player");
    }

    @Override
    public boolean parse(CommandSender sender, String value) {
        String[] split = value.split(" ");
        Pattern[] patterns = new Pattern[split.length];
        for (int i = 0; i < split.length; i++) {
            patterns[i] = Pattern.compile(split[i]);
        }

        Set<OfflinePlayer> result = null;
        for (OfflinePlayer player : Bukkit.getOfflinePlayers()) {
            for (Pattern pattern : patterns) {
                String name = player.getName();
                if (name == null) {
                    continue;
                }
                Matcher matcher = pattern.matcher(name);
                if (matcher.find()) {
                    if (result == null) {
                        result = new HashSet<>();
                    }
                    result.add(player);
                }
            }
        }
        if (result == null) {
            this.setValue(null);
            return false;
        }
        this.setValue(new EasyCollection<>(new ArrayList<>(result)));
        return true;
    }

    @Override
    public List<String> getTabComplete(CommandSender sender, String input) {
        return null;
    }
}
