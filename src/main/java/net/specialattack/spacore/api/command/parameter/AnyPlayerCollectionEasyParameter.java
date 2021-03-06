package net.specialattack.spacore.api.command.parameter;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;
import net.specialattack.spacore.util.Util;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

public class AnyPlayerCollectionEasyParameter extends AbstractEasyParameter.Multi<List<String>> {

    public AnyPlayerCollectionEasyParameter() {
        this.setName("player");
    }

    @Override
    public boolean parse(CommandSender sender, String value) {
        String[] split = value.split(" ");
        Location location = sender instanceof Entity ? ((Entity) sender).getLocation() : null;
        Set<String> result = new TreeSet<>();
        for (String part : split) {
            try {
                Set<Entity> matched = Util.matchEntities(part, location, EntityType.PLAYER);
                if (!matched.isEmpty()) {
                    result.addAll(matched.stream().filter(entity -> entity != null && entity instanceof Player).map(CommandSender::getName).collect(Collectors.toList()));
                    continue;
                }
            } catch (IllegalArgumentException ignored) {
            }
            result.add(part);
        }
        this.setValue(new ArrayList<>(result));
        return true;
    }

    @Override
    public List<String> getTabComplete(CommandSender sender, String input) {
        return null;
    }
}
