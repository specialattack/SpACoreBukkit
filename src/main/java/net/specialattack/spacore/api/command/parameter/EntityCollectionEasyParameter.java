package net.specialattack.spacore.api.command.parameter;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import net.specialattack.spacore.api.command.CommandException;
import net.specialattack.spacore.util.Util;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;

public class EntityCollectionEasyParameter extends AbstractEasyParameter.Multi<List<Entity>> {

    public EntityCollectionEasyParameter() {
        this.setName("entity");
    }

    @Override
    public boolean parse(CommandSender sender, String value) {
        Location location = sender instanceof Entity ? ((Entity) sender).getLocation() : null;
        try {
            Set<Entity> matched = Util.matchEntities(value, location, null);
            this.setValue(new ArrayList<>(matched));
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
