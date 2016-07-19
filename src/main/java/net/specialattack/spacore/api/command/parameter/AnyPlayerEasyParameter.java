package net.specialattack.spacore.api.command.parameter;

import java.util.List;
import java.util.Set;
import net.specialattack.spacore.util.Util;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

public class AnyPlayerEasyParameter extends AbstractEasyParameter<String> {

    public AnyPlayerEasyParameter() {
        this.setName("player");
    }

    @Override
    public boolean parse(CommandSender sender, String value) {
        Location location = sender instanceof Entity ? ((Entity) sender).getLocation() : null;
        Player result = null;
        try {
            Set<Entity> matched = Util.matchEntities(value, location, EntityType.PLAYER);
            if (matched.isEmpty()) {
                this.setValue(value);
                return true;
            }
            for (Entity entity : matched) {
                if (entity instanceof Player) {
                    result = (Player) entity;
                    break;
                }
            }
        } catch (IllegalArgumentException ignored) {
        }
        if (result != null) {
            this.setValue(result.getName());
            return true;
        }
        this.setValue(value);
        return true;
    }

    @Override
    public List<String> getTabComplete(CommandSender sender, String input) {
        return null;
    }
}
