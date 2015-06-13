package net.specialattack.bukkit.core.command.easy.parameter;

import java.util.List;
import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;

public class OfflinePlayerEasyParameter extends AbstractEasyParameter<OfflinePlayer> {

    public OfflinePlayerEasyParameter() {
        this.setName("player");
    }

    @Override
    public boolean parse(CommandSender sender, String value) {
        OfflinePlayer result = null;
        try {
            UUID uuid = UUID.fromString(value);
            result = Bukkit.getOfflinePlayer(uuid);
        } catch (IllegalArgumentException e) {
            for (OfflinePlayer player : Bukkit.getOfflinePlayers()) {
                String name = player.getName();
                if (name == null) {
                    continue;
                }
                if (name.equals(value)) {
                    result = player;
                    break;
                }
                if (name.startsWith(value) || player.getUniqueId().toString().startsWith(value)) {
                    result = player; // We continue in case somebody's full name matches
                }
            }
            this.setValue(result);
        }
        this.setValue(result);
        return result != null; // No player found == failure!
    }

    @Override
    public List<String> getTabComplete(CommandSender sender, String input) {
        return null; // TODO: make tab complete look through all offline players possibly?
    }

}
