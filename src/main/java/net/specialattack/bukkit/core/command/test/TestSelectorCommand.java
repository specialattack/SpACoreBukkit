package net.specialattack.bukkit.core.command.test;

import net.specialattack.bukkit.core.command.AbstractSubCommand;
import net.specialattack.bukkit.core.command.ISubCommandHolder;
import net.specialattack.bukkit.core.command.easy.EasyCollection;
import net.specialattack.bukkit.core.command.easy.parameter.EntityCollectionEasyParameter;
import net.specialattack.bukkit.core.util.ChatFormat;
import net.specialattack.bukkit.core.util.Function;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

/**
 * Command that displays version info about SpACore etc...
 */
public class TestSelectorCommand extends AbstractSubCommand {

    private final EntityCollectionEasyParameter entities;
    private int counter;

    public TestSelectorCommand(ISubCommandHolder command, String name, String permissions, String... aliases) {
        super(command, name, permissions, aliases);
        this.addParameter(this.entities = new EntityCollectionEasyParameter().setTakeAll());
        this.finish();
    }

    @Override
    public void runCommand(final CommandSender sender) {
        final EasyCollection<Entity> entities = this.entities.getValue();

        sender.sendMessage(ChatFormat.format("Input selected %s entities", ChatColor.GREEN, entities.values.size()));

        this.counter = 0;

        entities.forEach(new Function<Entity>() {
            @Override
            public void run(Entity entity) {
                if (TestSelectorCommand.this.counter > 15) {
                    return;
                }
                if (entity.getCustomName() != null) {
                    sender.sendMessage(ChatFormat.format("%s: %s (%s)", ChatColor.GREEN, entity.getType(), entity.getCustomName(), entity.getUniqueId()));
                } else if (entity instanceof Player) {
                    sender.sendMessage(ChatFormat.format("%s: %s (%s)", ChatColor.GREEN, entity.getType(), ((Player) entity).getName(), entity.getUniqueId()));
                } else {
                    sender.sendMessage(ChatFormat.format("%s: %s", ChatColor.GREEN, entity.getType(), entity.getUniqueId()));
                }

                TestSelectorCommand.this.counter++;
                return;
            }
        });
    }

}
