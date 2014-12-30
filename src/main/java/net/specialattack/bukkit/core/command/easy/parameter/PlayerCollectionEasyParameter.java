package net.specialattack.bukkit.core.command.easy.parameter;

import java.util.ArrayList;
import java.util.List;
import net.specialattack.bukkit.core.command.CommandException;
import net.specialattack.bukkit.core.command.easy.EasyCollection;
import net.specialattack.bukkit.core.util.Util;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

public class PlayerCollectionEasyParameter extends AbstractEasyParameter.Multi<EasyCollection<Player>> {

    public PlayerCollectionEasyParameter() {
        this.setName("player");
    }

    @Override
    public boolean parse(CommandSender sender, String value) {
        Location location = sender instanceof Entity ? ((Entity) sender).getLocation() : null;
        try {
            List<Entity> matched = Util.matchEntities(value, location, EntityType.PLAYER);
            if (matched.isEmpty()) {
                return false;
            }
            List<Player> players = new ArrayList<Player>(matched.size());
            for (Entity entity : matched) {
                if (entity instanceof Player) {
                    players.add((Player) entity);
                }
            }
            this.setValue(new EasyCollection<Player>(players));
            return true;
        } catch (IllegalArgumentException e) {
            this.setValue(null);
            throw new CommandException(e.getMessage(), e);
        }
    }

    @Override
    public List<String> getTabComplete(CommandSender sender, String input) {
        return null;
    }

}
