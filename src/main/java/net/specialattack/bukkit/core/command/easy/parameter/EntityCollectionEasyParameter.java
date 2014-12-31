package net.specialattack.bukkit.core.command.easy.parameter;

import java.util.Collection;
import java.util.List;
import net.specialattack.bukkit.core.command.CommandException;
import net.specialattack.bukkit.core.command.easy.EasyCollection;
import net.specialattack.bukkit.core.util.Util;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;

public class EntityCollectionEasyParameter extends AbstractEasyParameter.Multi<EasyCollection<Entity>> {

    public EntityCollectionEasyParameter() {
        this.setName("entity");
    }

    @Override
    public boolean parse(CommandSender sender, String value) {
        Location location = sender instanceof Entity ? ((Entity) sender).getLocation() : null;
        try {
            List<Entity> matched = Util.matchEntities(value, location, null);
            this.setValue(new EasyCollection<Entity>(matched));
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
